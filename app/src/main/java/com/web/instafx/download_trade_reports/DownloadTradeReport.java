package com.web.instafx.download_trade_reports;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.kyc.VerifyKycForPersonalInfoScreen;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DownloadTradeReport extends BaseActivity {
    private ImageView backIC;
    TextView from_dateTime_tv, to_dateTime_tv;
    private String reportType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_trade_report);

        initView();
        setOnClickListener();
    }

    private void initView() {
        from_dateTime_tv = findViewById(R.id.from_dateTime_tv);
        to_dateTime_tv = findViewById(R.id.to_dateTime_tv);

        RadioButton withdrawrepport = findViewById(R.id._exchange_trade_tv);
        RadioButton depositreport = findViewById(R.id._p2p_trade_tv);
        RadioButton orderHistory = findViewById(R.id._stf_trade_tv);

        withdrawrepport.setChecked(true);
        withdrawrepport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType="1";
                depositreport.setChecked(false);
                orderHistory.setChecked(false);
            }
        });
        depositreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType="2";
                withdrawrepport.setChecked(false);
                orderHistory.setChecked(false);
            }
        });

        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType="3";
                depositreport.setChecked(false);
                withdrawrepport.setChecked(false);
            }
        });


        backIC = findViewById(R.id.backIC);
        findViewById(R.id.rr_startdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                from_dateTime_tv.setTag("start");
                startDate(from_dateTime_tv);
            }
        });

        findViewById(R.id.rr_ldate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                to_dateTime_tv.setTag("end");
                startDate(to_dateTime_tv);
            }
        });

        findViewById(R.id.request_trading_reportBT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestForReport();
            }
        });

    }

    private void setOnClickListener() {
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void startDate(TextView datView) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(DownloadTradeReport.this,
                new DatePickerDialog.OnDateSetListener() {
                    String fmonth, fDate;
                    int month;

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
//bjb
                        try {
                            if (monthOfYear < 10 && dayOfMonth < 10) {

                                fmonth = "0" + monthOfYear;
                                month = Integer.parseInt(fmonth) + 1;
                                fDate = "0" + dayOfMonth;
                                String paddedMonth = String.format("%02d", month);
                                datView.setText(fDate + "-" + paddedMonth + "-" + year);

                            } else {

                                fmonth = "0" + monthOfYear;
                                month = Integer.parseInt(fmonth) + 1;
                                String paddedMonth = String.format("%02d", month);
                                datView.setText(dayOfMonth + "-" + paddedMonth + "-" + year);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }, mYear, mMonth, mDay);
        if (datView.getTag().toString().equalsIgnoreCase("start")) {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        } else {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }
        datePickerDialog.show();
    }



    private void requestForReport() {

        Map<String, String> m = new HashMap<>();
        m.put("token", savePreferences.reterivePreference(DownloadTradeReport.this, DefaultConstants.token) + "");
        m.put("DeviceToken", getDeviceToken() + "");
        m.put("Version", getAppVersion() + "");
        m.put("PlatForm", "Android");
        m.put("Timestamp", System.currentTimeMillis() + "");
        m.put("type", reportType);
        System.out.println("Data====" + m);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("X-API-KEY", UtilClass.xApiKey);

        new ServerHandler().sendToServer(DownloadTradeReport.this, getApiUrl() + "transactions-history", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {

                    JSONObject obj = new JSONObject(dta);
                    if (obj.getBoolean("status")) {


                    } else {
                        alertDialogs.alertDialog(DownloadTradeReport.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {
                                unauthorizedAccess(obj);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    }



    

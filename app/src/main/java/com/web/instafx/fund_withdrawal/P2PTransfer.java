package com.web.instafx.fund_withdrawal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.dialogsnpickers.SimpleDialog;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.LoginActivity;
import com.web.instafx.R;
import com.web.instafx.kyc.BalanceFulledetails;
import com.web.instafx.kyc.VerifyCompleteSubmitKycScreen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class P2PTransfer extends BaseActivity {

    private ImageView p2p_currencyimage;
    private TextView p2p_fee, p2p_totalvalue, p2p_currency_balance;
    private EditText p2p_amount, p2p_email, p2p_tofacode;
    private String fee, fee_type, balance, symbol = "";
    private double totalReceived = 0;
    boolean authenticator = false;
    private LinearLayout ll_is_2fa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2_p_transfer);
        getSupportActionBar().hide();
        initiateObj();
        init();
        setData();
        getP2pData();
    }

    private void init() {
        p2p_currencyimage = findViewById(R.id.p2p_currencyimage);
        p2p_amount = findViewById(R.id.p2p_amount);
        p2p_email = findViewById(R.id.p2p_email);
        p2p_fee = findViewById(R.id.p2p_fee);
        p2p_totalvalue = findViewById(R.id.p2p_totalvalue);
        p2p_currency_balance = findViewById(R.id.p2p_currency_balance);
        ll_is_2fa = findViewById(R.id.ll_is_2fa);
        p2p_tofacode = findViewById(R.id.p2p_tofacode);
        findViewById(R.id.backIC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        findViewById(R.id.p2p_verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validationRule.checkEmptyString(p2p_email) == 0) {
                    alertDialogs.alertDialog(P2PTransfer.this, getResources().getString(R.string.app_name), "Enter Email Address", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }

                if (validationRule.checkEmail(p2p_email) == 0) {
                    alertDialogs.alertDialog(P2PTransfer.this, getResources().getString(R.string.app_name), "Enter valid Email address", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }


                if (validationRule.checkEmptyString(p2p_amount) == 0) {
                    alertDialogs.alertDialog(P2PTransfer.this, getResources().getString(R.string.app_name), "Enter Amount", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }

                if (authenticator) {
                    if (validationRule.checkEmptyString(p2p_tofacode) == 0) {
                        alertDialogs.alertDialog(P2PTransfer.this, getResources().getString(R.string.app_name), "Enter 2FA code", "Ok", "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {

                            }
                        });
                        return;
                    }
                }

                verifyP2pTransfer();


            }
        });
        p2p_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() > 0) {

                    double amount = Double.parseDouble(s.toString());
                    double feeAmount = Double.parseDouble(fee);

                    if (fee_type.equalsIgnoreCase("fixed")) {
                        totalReceived = amount - feeAmount;
                        p2p_totalvalue.setText("Total value : " + totalReceived + " " + symbol);
                    } else {

                        double percent = amount * feeAmount / 100;
                        totalReceived = amount - percent;
                        p2p_totalvalue.setText("Total value : " + totalReceived + " " + symbol);
                    }

                } else {
                    totalReceived = 0;
                    p2p_totalvalue.setText("Total value : " + totalReceived + symbol);
                    p2p_amount.setHint("");
                }

            }
        });


    }

    private void setData() {
        try {
            JSONObject data = new JSONObject(getIntent().getStringExtra("data"));
            symbol = data.getString("symbol");
            showImage(data.getString("icon"), p2p_currencyimage);
            p2p_fee.setText("Fee : " + fee + " " + symbol);
            p2p_currency_balance.setText("Balance : " + balance + " " + symbol);

            if (authenticator) {
                ll_is_2fa.setVisibility(View.VISIBLE);
            } else {
                ll_is_2fa.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getP2pData() {
        try {
            final Map<String, String> m = new HashMap<>();
            m.put("currency", symbol);
            m.put("token", savePreferences.reterivePreference(P2PTransfer.this, DefaultConstants.token) + "");
            m.put("DeviceToken", getDeviceToken() + "");
            m.put("Version", getAppVersion() + "");
            m.put("PlatForm", "Android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            final Map<String, String> obj = new HashMap<>();
            obj.put("X-API-KEY", getXapiKey());
            obj.put("Rtoken", getNewRToken() + "");
            new ServerHandler().sendToServer(P2PTransfer.this, getApiUrl() + "p2p-transfer-entities", m, 0, obj, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {

                        JSONObject jsonObject = new JSONObject(dta);
                        System.out.println("P2p data===" + dta);
                        if (jsonObject.getBoolean("status"))
                        {
                            if (jsonObject.has("token")) {
                                savePreferences.savePreferencesData(P2PTransfer.this, jsonObject.getString("token"), DefaultConstants.token);
                                savePreferences.savePreferencesData(P2PTransfer.this, jsonObject.getString("r_token"), DefaultConstants.r_token);

                            }
                            fee = jsonObject.getString("fee");
                            balance = jsonObject.getString("balance");
                            fee_type = jsonObject.getString("fee_type");
                            authenticator = jsonObject.getBoolean("authenticator");

                            setData();

                        } else {
                            alertDialogs.alertDialog(P2PTransfer.this, getResources().getString(R.string.Response), jsonObject.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed) {
                                    unauthorizedAccess(jsonObject);
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void verifyP2pTransfer() {
        try {
            final Map<String, String> m = new HashMap<>();
            m.put("currency", symbol);
            m.put("amount", p2p_amount.getText().toString());
            m.put("email", p2p_email.getText().toString().trim());
            m.put("auth_code", p2p_tofacode.getText().toString());
            m.put("DeviceToken", getDeviceToken() + "");
            m.put("Version", getAppVersion() + "");
            m.put("PlatForm", "Android");
            m.put("Timestamp", System.currentTimeMillis() + "");

            final Map<String, String> obj = new HashMap<>();
            obj.put("X-API-KEY", getXapiKey());
            obj.put("Rtoken", getNewRToken() + "");

            System.out.println("Before==="+m);


            new ServerHandler().sendToServer(P2PTransfer.this, getApiUrl() + "proceed-to-transfer", m, 0, obj, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {

                        JSONObject jsonObject = new JSONObject(dta);
                        if (jsonObject.getBoolean("status"))
                        {
                            System.out.println("Proceed==="+jsonObject);

                            if (jsonObject.has("token")) {
                                savePreferences.savePreferencesData(P2PTransfer.this, jsonObject.getString("token"), DefaultConstants.token);
                                savePreferences.savePreferencesData(P2PTransfer.this, jsonObject.getString("r_token"), DefaultConstants.r_token);

                            }

                            m.put("token", savePreferences.reterivePreference(P2PTransfer.this, DefaultConstants.token) + "");
                            takeOtp(m,jsonObject.getString("msg"));

                        } else {
                            alertDialogs.alertDialog(P2PTransfer.this, getResources().getString(R.string.Response), jsonObject.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed) {
                                    unauthorizedAccess(jsonObject);
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Dialog takeOtpDialog;
    private void takeOtp(Map<String,String> map,String msg)
    {

        SimpleDialog simpleDialog = new SimpleDialog();
        takeOtpDialog = simpleDialog.simpleDailog(P2PTransfer.this, R.layout.take_otp_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
        EditText p2potp=takeOtpDialog.findViewById(R.id.p2p_otp);
        TextView otp_msg=takeOtpDialog.findViewById(R.id.otp_msg);
        RelativeLayout ll_animate=takeOtpDialog.findViewById(R.id.ll_animate);
        takeOtpDialog.setCancelable(true);
        takeOtpDialog.show();
        animateUp(ll_animate);
        otp_msg.setText(msg);

        ll_animate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                takeOtpDialog.dismiss();
            }
        });

        takeOtpDialog.findViewById(R.id.p2p_verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(p2potp.getText().toString().length()==0)
                {
                    alertDialogs.alertDialog(P2PTransfer.this, getResources().getString(R.string.Response), "Enter OTP", getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed)
                        {
                        }
                    });
                    return;
                }

                map.put("otp",p2potp.getText().toString());
                verifyTransactionwithotp(map);

            }
        });



    }


    private void verifyTransactionwithotp(Map<String,String> map)
    {
        System.out.println("final api==="+map);
        final Map<String, String> obj = new HashMap<>();
        obj.put("X-API-KEY", getXapiKey());
        obj.put("Rtoken", getNewRToken() + "");
        new ServerHandler().sendToServer(P2PTransfer.this, getApiUrl() + "verify-internal-transfer", map, 0, obj, 20000, R.layout.progressbar, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {

                    JSONObject jsonObject = new JSONObject(dta);
                    if (jsonObject.getBoolean("status"))
                       {
                        if(jsonObject.has("token"))
                        {
                            savePreferences.savePreferencesData(P2PTransfer.this, jsonObject.getString("token"), DefaultConstants.token);
                            savePreferences.savePreferencesData(P2PTransfer.this, jsonObject.getString("r_token"), DefaultConstants.r_token);
                        }

                        alertDialogs.alertDialog(P2PTransfer.this, getResources().getString(R.string.Response), jsonObject.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {
                                finish();

                            }
                        });
                    } else {
                        alertDialogs.alertDialog(P2PTransfer.this, getResources().getString(R.string.Response), jsonObject.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {
                                unauthorizedAccess(jsonObject);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }




    private void showImage(final String url, final ImageView header_img) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(P2PTransfer.this)
                        .load(url)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(3)))
                        .into(header_img);
            }
        });
    }


}
package com.web.instafx.fiatdepositwithdraw;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.kyc.GoogleAuthentication;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WithdrawInrActivity extends BaseActivity {
    private EditText  txt_amount;
    private double availableBal=0;
    private  TextView txt_note;

    private EditText txt_destinationtag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_inr);
        getSupportActionBar().hide();
        initiateObj();
        init();
    }

    private void init() {
      try {

          txt_amount = findViewById(R.id.txt_amount);
          ImageView txt_currency_image1 = findViewById(R.id.txt_currency_image1);
          TextView total_balance1 = findViewById(R.id.total_balance1);
           txt_note = findViewById(R.id.txt_note);

          TextView txt_label_destinationtag = findViewById(R.id.txt_label_destinationtag);
          txt_destinationtag = findViewById(R.id.txt_destinationtag);

          availableBal = Double.parseDouble(getIntent().getStringExtra("balance"));
          total_balance1.setText(availableBal + "");
          showImage(getIntent().getStringExtra("icon"), txt_currency_image1);

          String symbol = getIntent().getStringExtra("currency");
          if (symbol.equalsIgnoreCase("INR")) {
              txt_destinationtag.setVisibility(View.GONE);
              txt_label_destinationtag.setVisibility(View.GONE);

          } else {
              txt_destinationtag.setVisibility(View.VISIBLE);
              txt_label_destinationtag.setVisibility(View.VISIBLE);

          }

          JSONObject pairdata = new JSONObject(getIntent().getStringExtra(DefaultConstants.pair_data));
          System.out.println("Withdrawpair data==="+pairdata);

//          txt_note


          findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  finish();
              }
          });


          TextView submit = findViewById(R.id.submit);
          if (getIntent().getBooleanExtra("isGoogleAuth", false)) {
              submit.setText("Next");
          } else {
              submit.setText("Confirm Withdraw");
          }
          submit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (validationRule.checkEmptyString(txt_amount) == 0) {
                      alertDialogs.alertDialog(WithdrawInrActivity.this, getResources().getString(R.string.Response), "Enter withdraw amount.", getResources().getString(R.string.ok), "", new DialogCallBacks() {
                          @Override
                          public void getDialogEvent(String buttonPressed) {
                          }
                      });
                      return;
                  }

                  if (availableBal < Double.parseDouble(txt_amount.getText().toString())) {
                      alertDialogs.alertDialog(WithdrawInrActivity.this, getResources().getString(R.string.Response), "Withdraw amount should be less than available amount.", getResources().getString(R.string.ok), "", new DialogCallBacks() {
                          @Override
                          public void getDialogEvent(String buttonPressed) {
                          }
                      });
                      return;
                  }
                  if (txt_destinationtag.getVisibility() == View.VISIBLE) {
                      if (txt_destinationtag.getText().toString().length() <= 0) {
                          alertDialogs.alertDialog(WithdrawInrActivity.this, getResources().getString(R.string.Response), "Enter destination address", getResources().getString(R.string.ok), "", new DialogCallBacks() {
                              @Override
                              public void getDialogEvent(String buttonPressed) {
                              }
                          });
                          return;
                      }
                  }


                  if (getIntent().getBooleanExtra("isGoogleAuth", false)) {
                      Intent intent = new Intent(WithdrawInrActivity.this, GoogleAuthentication.class);
                      intent.putExtra("currency", symbol);
                      intent.putExtra("amount", txt_amount.getText().toString());
                      intent.putExtra("destination_tag", txt_destinationtag.getText().toString());
                      intent.putExtra("address", "");
                      startActivityForResult(intent, 1002);

                  } else {
                      withdrawWithoutAuth(symbol, txt_amount.getText().toString(), txt_destinationtag.getText().toString());
                  }
              }
          });
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
           if (data != null) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                intent.putExtra("data", "data");
                finish();
            }

        }


    private void showImage(final String url, final ImageView header_img) {
        Glide.with(WithdrawInrActivity.this)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(3)))
                .into(header_img);
    }


    private void withdrawWithoutAuth(String symbol, String amount,String address)
    {
        Map<String, String> map = new HashMap<>();
        map.put("token", savePreferences.reterivePreference(WithdrawInrActivity.this, DefaultConstants.token) + "");
        map.put("currency", symbol);
        map.put("amount", amount);
        map.put("address", address);
        map.put("DeviceToken", getDeviceToken());
        System.out.println("Withdraw==="+map);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("X-API-KEY", UtilClass.xApiKey);
        headerMap.put("Rtoken", getNewRToken() + "");


        new ServerHandler().sendToServer(WithdrawInrActivity.this, getApiUrl() + "proceed-withdraw", map, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {

                    JSONObject obj = new JSONObject(dta);
                    System.out.println("Return ==="+dta);
                    if (obj.getBoolean("status")) {
                        alertDialogs.alertDialog(WithdrawInrActivity.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {

                                finish();
                            }
                        });
                    } else {
                        alertDialogs.alertDialog(WithdrawInrActivity.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
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
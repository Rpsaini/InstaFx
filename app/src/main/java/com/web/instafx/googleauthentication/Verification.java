package com.web.instafx.googleauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;

import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.MainActivity;
import com.web.instafx.R;
import com.web.instafx.staking.StakingScreen;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Verification extends BaseActivity {

    private EditText ed_emailverificationcode,ed_googlecode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        getSupportActionBar().hide();
        initiateObj();
        init();
        System.out.println("Call times--->");
    }
    private void init()
    {

        ed_googlecode=findViewById(R.id.ed_googlecode);
        ed_emailverificationcode=findViewById(R.id.ed_emailverificationcode);
        TextView txt_pastekey=findViewById(R.id.txt_pastekey);
        TextView txt_email=findViewById(R.id.txt_email);

        findViewById(R.id.txt_send_email_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendGmailcode();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goback();
            }
        });

        txt_pastekey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_googlecode.setText(pasteClipboardData()+"");

            }
        });



        findViewById(R.id.txt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(validationRule.checkEmptyString(ed_emailverificationcode)==0)
                 {
                  alertDialogs.alertDialog(Verification.this, getResources().getString(R.string.app_name), "Enter Email Verification code.", "Ok", "", new DialogCallBacks() {
                    @Override
                    public void getDialogEvent(String buttonPressed)
                    {

                    }
                  });
                 return;
                }

                if(validationRule.checkEmptyString(ed_googlecode)==0)
                {
                    alertDialogs.alertDialog(Verification.this, getResources().getString(R.string.app_name), "Enter Google Verification Code.", "Ok", "", new DialogCallBacks() {
                    @Override
                    public void getDialogEvent(String buttonPressed) {

                    }
                });
               return;

                }
                completeGoogleVerification();
            }
        });
    }

    private void sendGmailcode()
    {
        try {
            final Map<String, String> m = new HashMap<>();
            m.put("token", savePreferences.reterivePreference(this, DefaultConstants.token) + "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("DeviceToken", getDeviceToken() + "");
            m.put("status", getIntent().getStringExtra(DefaultConstants.status));
            System.out.println("Before==otp=="+m);

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);

            serverHandler.sendToServer(Verification.this, getApiUrl() + "generate-auth-otp", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        JSONObject jsonObject = new JSONObject(dta);
                        System.out.println("Backup key==="+jsonObject);
                        if (jsonObject.getBoolean("status")) {


                            if(jsonObject.has("token"))
                            {
                                savePreferences.savePreferencesData(Verification.this, jsonObject.getString("token"), DefaultConstants.token);
                                savePreferences.savePreferencesData(Verification.this, jsonObject.getString("r_token"), DefaultConstants.r_token);
                            }

                            Toast.makeText(Verification.this,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
//                            alertDialogs.alertDialog(Verification.this, getResources().getString(R.string.app_name), jsonObject.getString("msg"), "Ok", "", new DialogCallBacks() {
//                                @Override
//                                public void getDialogEvent(String buttonPressed)
//                                {
//
//                                }
//                            });
                        }
                        else
                        {
                            alertDialogs.alertDialog(Verification.this, getResources().getString(R.string.app_name), jsonObject.getString("msg"), "Ok", "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed) {

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



    private void completeGoogleVerification()
    { try
        {
            final Map<String, String> m = new HashMap<>();
            m.put("token", savePreferences.reterivePreference(this, DefaultConstants.token) + "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            if(getIntent().getStringExtra(DefaultConstants.status).equalsIgnoreCase("1")) {
                m.put("secret", getIntent().getStringExtra(DefaultConstants.google_sceret_code));
            }
            else
            {
                m.put("secret", "");
            }
            m.put("code", ed_googlecode.getText()+"");
            m.put("status", getIntent().getStringExtra(DefaultConstants.status));
            m.put("otp", ed_emailverificationcode.getText()+"");
            m.put("DeviceToken", getDeviceToken() + "");



            System.out.println("Before to send==="+m);
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);

            serverHandler.sendToServer(Verification.this, getApiUrl() + "update-authenticator-settings", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons)
                {
                    try
                       {
                        JSONObject jsonObject = new JSONObject(dta);
                        System.out.println("Backup key==="+jsonObject);
                        if(jsonObject.getBoolean("status"))
                        {
                            if(jsonObject.has("token"))
                            {
                                savePreferences.savePreferencesData(Verification.this, jsonObject.getString("token"), DefaultConstants.token);
                                savePreferences.savePreferencesData(Verification.this, jsonObject.getString("r_token"), DefaultConstants.r_token);
                            }

                            System.out.println("Gaactive===="+getIntent().getStringExtra(DefaultConstants.status));
                            if(getIntent().getStringExtra(DefaultConstants.status).equalsIgnoreCase("1"))
                            {
                                savePreferences.savePreferencesData(Verification.this, "true", DefaultConstants.ga_active);
                            }
                            else
                            {
                                savePreferences.savePreferencesData(Verification.this, "false", DefaultConstants.ga_active);
                            }

                            alertDialogs.alertDialog(Verification.this, getResources().getString(R.string.app_name), jsonObject.getString("msg"), "Ok", "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed)
                                {
                                    goback();
                                }
                            });




//                            if(jsonObject.has("userdata"))
//                            {
//                                savePreferences.savePreferencesData(Verification.this, jsonObject.getString("userdata") + "", DefaultConstants.login_detail);
//                            }
//                            if(getIntent().getStringExtra(DefaultConstants.status).equalsIgnoreCase("1"))//from setup
//                            {
//                                alertDialogs.alertDialog(Verification.this, getResources().getString(R.string.app_name), jsonObject.getString("message"), "Ok", "", new DialogCallBacks() {
//                                    @Override
//                                    public void getDialogEvent(String buttonPressed) {
//
//                                        Intent intent=new Intent();
//                                        intent.putExtra("data","setup");
//                                        setResult(RESULT_OK,intent);
//                                        finish();
//                                    }
//                                });
//                            }
//                            else if(getIntent().getStringExtra(DefaultConstants.status).equalsIgnoreCase("2"))//from verification
//                            {
//
//                                Intent intent = new Intent(Verification.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                            else if(getIntent().getStringExtra(DefaultConstants.status).equalsIgnoreCase("3"))//from swap
//                            {
//
//                                Intent intent=new Intent();
//                                intent.putExtra("data","swap");
//                                setResult(RESULT_OK,intent);
//                                finish();
//                            }
//                            else if(getIntent().getStringExtra(DefaultConstants.status).equalsIgnoreCase("4"))//from send
//                            {
//
//                                Intent intent=new Intent();
//                                intent.putExtra("data","send");
//                                setResult(RESULT_OK,intent);
//                                finish();
//                            }

                        }
                        else
                        {
                            alertDialogs.alertDialog(Verification.this, getResources().getString(R.string.app_name), jsonObject.getString("msg"), "Ok", "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed) {

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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();

    }

private void goback()
{
    Intent intent = new Intent();
    if(getIntent().getStringExtra(DefaultConstants.status).equalsIgnoreCase("true")) {
        intent.putExtra("data", "setup");
    }
    else
    {
        intent.putExtra("data", "remove");
    }
    setResult(RESULT_OK, intent);
    finish();
}


}
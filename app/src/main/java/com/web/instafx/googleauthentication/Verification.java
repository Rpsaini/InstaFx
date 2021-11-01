package com.web.instafx.googleauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;

import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.MainActivity;
import com.web.instafx.R;

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

       //todo txt_email.setText("Code will send to an "+getRestParamsName(DefaultConstants.email)+" address");

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
                finish();
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
                { alertDialogs.alertDialog(Verification.this, getResources().getString(R.string.app_name), "Enter Email Verification code.", "Ok", "", new DialogCallBacks() {
                    @Override
                    public void getDialogEvent(String buttonPressed) {

                    }
                });
                return;

                }

                if(validationRule.checkEmptyString(ed_googlecode)==0)
                { alertDialogs.alertDialog(Verification.this, getResources().getString(R.string.app_name), "Enter Google Verification Code.", "Ok", "", new DialogCallBacks() {
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

            //todo m.put("user_id", getRestParamsName(DefaultConstants.user_id));

            final Map<String, String> obj = new HashMap<>();
            obj.put("token", savePreferences.reterivePreference(Verification.this, DefaultConstants.token) + "");

            serverHandler.sendToServer(Verification.this, getApiUrl() + "send-ga-code", m, 0, obj, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        JSONObject jsonObject = new JSONObject(dta);
                        System.out.println("Backup key==="+jsonObject);
                        if (jsonObject.getBoolean("status")) {

                            alertDialogs.alertDialog(Verification.this, getResources().getString(R.string.app_name), jsonObject.getString("msg"), "Ok", "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed)
                                {

                                }
                            });


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
//  Todo          m.put("user_id", getRestParamsName(DefaultConstants.user_id));
            m.put("google_code", ed_googlecode.getText().toString());
            m.put("email_code", ed_emailverificationcode.getText().toString());
            m.put("google_secret_key", getIntent().getStringExtra(DefaultConstants.google_sceret_code));
            m.put("status", getIntent().getStringExtra(DefaultConstants.status));



            System.out.println("Before to verify==="+m);
            final Map<String, String> obj = new HashMap<>();
            obj.put("token", savePreferences.reterivePreference(Verification.this, DefaultConstants.token) + "");

            serverHandler.sendToServer(Verification.this, getApiUrl() + "complete-google-authentication", m, 0, obj, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(dta);
                        System.out.println("Backup key==="+jsonObject);
                        if (jsonObject.getBoolean("status")) {

                           //updateGaStatus();

                            if(jsonObject.has("userdata")) {
                                savePreferences.savePreferencesData(Verification.this, jsonObject.getString("userdata") + "", DefaultConstants.login_detail);
                            }
                            if(getIntent().getStringExtra(DefaultConstants.status).equalsIgnoreCase("1"))//from setup
                            {
                                alertDialogs.alertDialog(Verification.this, getResources().getString(R.string.app_name), jsonObject.getString("message"), "Ok", "", new DialogCallBacks() {
                                    @Override
                                    public void getDialogEvent(String buttonPressed) {

                                        Intent intent=new Intent();
                                        intent.putExtra("data","setup");
                                        setResult(RESULT_OK,intent);
                                        finish();
                                    }
                                });
                            }
                            else if(getIntent().getStringExtra(DefaultConstants.status).equalsIgnoreCase("2"))//from verification
                            {

                                Intent intent = new Intent(Verification.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if(getIntent().getStringExtra(DefaultConstants.status).equalsIgnoreCase("3"))//from swap
                            {

                                Intent intent=new Intent();
                                intent.putExtra("data","swap");
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                            else if(getIntent().getStringExtra(DefaultConstants.status).equalsIgnoreCase("4"))//from send
                            {

                                Intent intent=new Intent();
                                intent.putExtra("data","send");
                                setResult(RESULT_OK,intent);
                                finish();
                            }

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


}
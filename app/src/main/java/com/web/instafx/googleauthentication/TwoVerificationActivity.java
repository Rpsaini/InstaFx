package com.web.instafx.googleauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.MainActivity;
import com.web.instafx.R;



import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;

import com.web.instafx.two_factor_auth.TwoFactorAuthScreen;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TwoVerificationActivity extends BaseActivity {
    private String  concateOtp;
    private EditText ed_one;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_verification);
        getSupportActionBar().hide();
        initiateObj();
        init();

    }


    private void init() {


        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ed_one = findViewById(R.id.ed_otp1);


        TextView btn_button = findViewById(R.id.btn_button);
        btn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                concateOtp = ed_one.getText().toString();
                if(concateOtp.length()<=5) {
                    alertDialogs.alertDialog(TwoVerificationActivity.this, getResources().getString(R.string.Response), "Entered 2FA code is invalid.", getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }

                activateAccountApi();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001)
        {
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    private void activateAccountApi()
    {
        try
        {
            final Map<String, String> m = new HashMap<>();
            m.put("code",concateOtp);
            m.put("token", savePreferences.reterivePreference(this, DefaultConstants.token)+ "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("DeviceToken", getDeviceToken() + "");

            final Map<String, String> obj = new HashMap<>();
            obj.put("X-API-KEY", getXapiKey());
            obj.put("Rtoken", getNewRToken()+"");



            serverHandler.sendToServer(this, getApiUrl() +"verify-google-auth", m, 0, obj, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {

                    try {
                        System.out.println("data back==="+dta);
                        JSONObject jsonObject = new JSONObject(dta);
                        if(jsonObject.getBoolean("status"))
                        {

                            if(jsonObject.has("token")) {
                                savePreferences.savePreferencesData(TwoVerificationActivity.this, jsonObject.getString("token"), UtilClass.token);
                                savePreferences.savePreferencesData(TwoVerificationActivity.this, jsonObject + "", DefaultConstants.login_detail);
                            }
                            Intent intent=new Intent(TwoVerificationActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            alertDialogs.alertDialog(TwoVerificationActivity.this, getResources().getString(R.string.Response), jsonObject.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
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
}
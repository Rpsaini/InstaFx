package com.web.instafx.forgot_pwd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;

import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        initiateObj();
        init();

    }

    private void init() {
        final EditText et_email = findViewById(R.id.et_email);
        TextView tv_sign_in = findViewById(R.id.tv_sign_in);
        LinearLayout root= findViewById(R.id.ll_sec);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(ForgotPassword.this);
            }
        });

        findViewById(R.id.backIC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationRule.checkEmptyString(et_email) == 0) {
                    alertDialogs.alertDialog(ForgotPassword.this, getResources().getString(R.string.Required), getResources().getString(R.string.enteryouremail), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (validationRule.checkEmail(et_email) == 0) {
                    alertDialogs.alertDialog(ForgotPassword.this, getResources().getString(R.string.Invalid), getResources().getString(R.string.entervalidemail), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                } else {

                    forgotPassword(et_email.getText().toString());
                }
            }
        });

    }


    private void forgotPassword(String email) {
        try {
            final Map<String, String> m = new HashMap<>();
            m.put("email", email);
            m.put("token", savePreferences.reterivePreference(ForgotPassword.this, DefaultConstants.token) + "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("DeviceToken", getDeviceToken() + "");
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);
            Log.e("OrderDetails","request param::"+m);


            serverHandler.sendToServer(this, getApiUrl() + "forgot-password-otp", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        JSONObject jsonObject = new JSONObject(dta);
                        if (jsonObject.getBoolean("status")) {

                            Intent intent=new Intent(ForgotPassword.this,EnterOTP.class);
                            intent.putExtra(UtilClass.otp,jsonObject.getString("otp"));
                            intent.putExtra(UtilClass.user_id,jsonObject.getString("user_id"));
                            intent.putExtra(UtilClass.email,email);
                            startActivityForResult(intent,1001);


                        } else {
                            alertDialogs.alertDialog(ForgotPassword.this, getResources().getString(R.string.Response), jsonObject.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001)
        {
            Intent intent=new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
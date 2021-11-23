package com.web.instafx.googleauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.vollycommunicationlib.CallBack;

import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BackUpKey extends BaseActivity {

    private TextView txt_backupkey,txt_copy;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_up_key);
        getSupportActionBar().hide();
        initiateObj();
        init();
        getBackupKey();
    }

    private void init()
    {
        txt_backupkey=findViewById(R.id.txt_backupkey);
        txt_copy=findViewById(R.id.txt_copy);
        findViewById(R.id.txt_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BackUpKey.this,PasteBackupKey.class);
                intent.putExtra(DefaultConstants.google_sceret_code,txt_backupkey.getText().toString());
                intent.putExtra(DefaultConstants.status,getIntent().getStringExtra(DefaultConstants.status));
                startActivityForResult(intent,1001);
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyCode(txt_backupkey.getText().toString());
            }
        });
    }


    private void getBackupKey()
    {
        try {
            final Map<String, String> m = new HashMap<>();

            m.put("token",savePreferences.reterivePreference(BackUpKey.this, DefaultConstants.token)+"");
            m.put("DeviceToken",getDeviceToken()+"");
            m.put("Version",getDeviceToken()+"");
            m.put("PlatForm","Android");
            m.put("Timestamp",System.currentTimeMillis()+"");

            Map<String,String> headerMap=new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);
            headerMap.put("Rtoken", getNewRToken()+"");

            System.out.println("data===="+getApiUrl() + "get-authenticator-info"+"===="+m+"==="+headerMap);


            serverHandler.sendToServer(BackUpKey.this, getApiUrl() + "get-authenticator-info", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        JSONObject jsonObject = new JSONObject(dta);
                        System.out.println("Backup key==="+jsonObject);
                        if(jsonObject.getBoolean("status"))
                        {

                            jsonObject.getString("qr_code_url");
                            jsonObject.getBoolean("is_active");//true/false

                            txt_backupkey.setText(jsonObject.getString("secret_key"));

                            if(jsonObject.has("token"))
                            {
                                savePreferences.savePreferencesData(BackUpKey.this, jsonObject.getString("token"), DefaultConstants.token);
                                savePreferences.savePreferencesData(BackUpKey.this, jsonObject.getString("r_token"), DefaultConstants.r_token);
                            }


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
        if(data!=null)
        {
            if(requestCode==1001)
            {
                Intent intent=new Intent();
                intent.putExtra("data","setup");
                setResult(RESULT_OK,intent);
                finish();
            }

        }

    }

}
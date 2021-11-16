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
                intent.putExtra(DefaultConstants.status,"1");
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
//          m.put("user_id", getRestParamsName(DefaultConstants.user_id));
            final Map<String, String> obj = new HashMap<>();
            obj.put("token", savePreferences.reterivePreference(BackUpKey.this, DefaultConstants.token) + "");

            serverHandler.sendToServer(BackUpKey.this, getApiUrl() + "get-google-secret-key", m, 0, obj, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        JSONObject jsonObject = new JSONObject(dta);
                        System.out.println("Backup key==="+jsonObject);
                        if (jsonObject.getBoolean("status")) {
                            txt_backupkey.setText(jsonObject.getString("google_secret_key"));
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
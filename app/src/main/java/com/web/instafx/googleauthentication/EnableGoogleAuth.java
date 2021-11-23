package com.web.instafx.googleauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;

public class EnableGoogleAuth extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_google_auth);
        getSupportActionBar().hide();
        initiateObj();
       }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init()
    {
        String ga_active=savePreferences.reterivePreference(EnableGoogleAuth.this,DefaultConstants.ga_active).toString();//getRestParamsName(DefaultConstants.google_sceret_code);
        SwitchMaterial aSwitch =findViewById(R.id.google_switch);

        System.out.println("is ga==="+ga_active);
        if(ga_active.length()==0||ga_active.equalsIgnoreCase("false"))
        {
            aSwitch.setChecked(false);
        }
        else
        {
            aSwitch.setChecked(true);
        }
        aSwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                System.out.println("is check==="+aSwitch.isChecked());
                if(aSwitch.isChecked())
                {
                    Intent intent=new Intent(EnableGoogleAuth.this,DownloadAndInstall.class);
                    intent.putExtra(DefaultConstants.status,"1");
                    startActivityForResult(intent,1001);
                }
                else
                {
                    Intent intent=new Intent(EnableGoogleAuth.this,Verification.class);
                    intent.putExtra(DefaultConstants.status,"0");
                    startActivityForResult(intent,1001);
                }

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("Data back----"+requestCode+"==="+resultCode);

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
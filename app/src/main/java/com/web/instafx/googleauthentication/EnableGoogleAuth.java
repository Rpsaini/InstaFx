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
        String googleSecretCode=getRestParamsName(DefaultConstants.google_sceret_code);
        String ga_active=getRestParamsName(DefaultConstants.ga_active);
        SwitchMaterial aSwitch =findViewById(R.id.google_switch);

        if(ga_active.equalsIgnoreCase("0"))
        {
            aSwitch.setChecked(false);
        }
        else
        {
            aSwitch.setChecked(true);
        }
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("is check==="+aSwitch.isChecked());
                if(aSwitch.isChecked())
                {
                    //if(!googleSecretCode.equalsIgnoreCase("false"))
                    if(googleSecretCode.length()>5)
                    {
                        Intent intent=new Intent(EnableGoogleAuth.this,Verification.class);
                        intent.putExtra(DefaultConstants.google_sceret_code,googleSecretCode);
                        intent.putExtra(DefaultConstants.status,"1");
                        startActivityForResult(intent,1001);

                    }
                    else
                    {
                        Intent intent=new Intent(EnableGoogleAuth.this,DownloadAndInstall.class);
                        startActivityForResult(intent,1001);
                    }
                }
                else
                {
                    if(googleSecretCode.length()>5)
                    {
                        Intent intent=new Intent(EnableGoogleAuth.this,Verification.class);
                        intent.putExtra(DefaultConstants.google_sceret_code,googleSecretCode);
                        intent.putExtra(DefaultConstants.status,"1");
                        startActivityForResult(intent,1001);
                    }
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
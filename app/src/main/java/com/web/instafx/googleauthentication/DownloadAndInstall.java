package com.web.instafx.googleauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.googleauthentication.googleauthslider.GoogleAuthSliderPageAdapter;

public class DownloadAndInstall extends BaseActivity {

    private GoogleAuthSliderPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_and_install);
        getSupportActionBar().hide();
        initiateObj();
        init();
    }

    private void init()
    {
        findViewById(R.id.txt_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExternalUrls("https://play.google.com/store/apps/details?id=com.google.android.apps.authenticator2");
                Intent intent=new Intent(DownloadAndInstall.this,BackUpKey.class);
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
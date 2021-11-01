package com.web.instafx.two_factor_auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.web.instafx.BaseActivity;
import com.web.instafx.R;
import com.web.instafx.googleauthentication.EnableGoogleAuth;

public class TwoFactorAuthScreen extends BaseActivity {
    private ImageView backIC;
    private ImageView authenticatAppIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_fact_auth_screen);

        initView();
        setOnClickListener();
    }

    private void initView(){
        backIC=findViewById(R.id.backIC);
        authenticatAppIV=findViewById(R.id.authenticatAppIV);
    }

    private void setOnClickListener(){
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        authenticatAppIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                authenticatAppIV.setImageResource(R.drawable.ic_switch_on);
                Intent intent=new Intent(TwoFactorAuthScreen.this, EnableGoogleAuth.class);
                startActivity(intent);
            }
        });


    }
}

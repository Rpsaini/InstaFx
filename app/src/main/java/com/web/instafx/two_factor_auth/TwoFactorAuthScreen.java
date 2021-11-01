package com.web.instafx.two_factor_auth;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.web.instafx.BaseActivity;
import com.web.instafx.R;

public class TwoFactorAuthScreen extends BaseActivity {
    private ImageView backIC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_fact_auth_screen);
        initView();
        setOnClickListener();
    }

    private void initView(){
        backIC=findViewById(R.id.backIC);
    }

    private void setOnClickListener(){
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

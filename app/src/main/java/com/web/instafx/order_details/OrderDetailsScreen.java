package com.web.instafx.order_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.dialogsnpickers.AlertDialogs;
import com.app.dialogsnpickers.DialogCallBacks;
import com.web.instafx.BaseActivity;
import com.web.instafx.R;
import com.web.instafx.activity_log.ActivityLogScreens;
import com.web.instafx.currency_preferences.CurrencyPreferencesScreen;
import com.web.instafx.kyc.VerifyKycAccountDetailsScreen;
import com.web.instafx.payment_option.PaymentOptionsScreen;
import com.web.instafx.setting_profile.SettingProfileScreen;
import com.web.instafx.two_factor_auth.TwoFactorAuthScreen;

import invite_earn.InviteEarnScreen;

public class OrderDetailsScreen extends BaseActivity {
    private ImageView backIC=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_screen);
        initiateObj();
        initView();
        setOnClickListener();
    }
    private void initView(){
        backIC =findViewById(R.id.backIC);
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

package com.web.instafx.payment_option;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.web.instafx.BaseActivity;
import com.web.instafx.R;
import com.web.instafx.payment_option.adapter.PaymentOptionPagerAdapter;

public class PaymentOptionsScreen extends BaseActivity {

    ImageView backIC;
    TabLayout tabLayout;
    ViewPager viewPager;
    PaymentOptionPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_option_screen);
        getSupportActionBar().hide();
        initView();
        setViewPagerAdapter();
        setOnClickListener();
    }

    private void initView(){
        backIC=findViewById(R.id.backIC);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.pager);
    }

    private void setViewPagerAdapter(){
        viewPagerAdapter = new PaymentOptionPagerAdapter(getSupportFragmentManager(),PaymentOptionsScreen.this);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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

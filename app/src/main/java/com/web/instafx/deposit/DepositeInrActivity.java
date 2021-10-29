package com.web.instafx.deposit;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.google.android.material.tabs.TabLayout;
import com.razorpay.PaymentResultListener;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.adapters.FundPagerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DepositeInrActivity extends BaseActivity  implements PaymentResultListener  {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposite_inr);
        getSupportActionBar().hide();
        initiateObj();
        tablayout();
        callPagerAdapter();
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    void tablayout() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Razorpay)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.bankdeposit)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);
       }

    private void callPagerAdapter() {
        DepositPagerAdapter adapter = new DepositPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#13B351"));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {


        System.out.println("Razor pay id==="+razorpayPaymentID);

        DepositByRazorPayFrg frag1 = (DepositByRazorPayFrg)viewPager
                .getAdapter()
                .instantiateItem(viewPager, viewPager.getCurrentItem());

        frag1.paymentSuccessFailure("1", razorpayPaymentID);
    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        System.out.println("Faild====" + code + "===" + response);
        DepositByRazorPayFrg frag1 = (DepositByRazorPayFrg)viewPager
                .getAdapter()
                .instantiateItem(viewPager, viewPager.getCurrentItem());

        frag1. paymentSuccessFailure("0","");

    }

}
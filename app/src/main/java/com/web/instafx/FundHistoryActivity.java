package com.web.instafx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.instafx.fragments.MainFundFragment;
import com.web.instafx.fragments.QuickBuyFragment;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FundHistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_history);
        getSupportActionBar().hide();
        initiateObj();
        init();

    }

    private void init() {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MainFundFragment fundFragment = new MainFundFragment();
        Bundle args = new Bundle();
        args.putString("data",getIntent().getStringExtra("data"));
        fundFragment.setArguments(args);
        replaceMainFragment(fundFragment, "fundhistory");


    }



    private void replaceMainFragment(Fragment fragment, String tag) {
        FragmentTransaction ft_main = getSupportFragmentManager().beginTransaction();
        ft_main.replace(R.id.ll_container, fragment);
        //ft_main.addToBackStack(tag);
        ft_main.commit();
    }





}
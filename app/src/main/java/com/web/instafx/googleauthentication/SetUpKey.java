package com.web.instafx.googleauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.googleauthentication.googleauthslider.GoogleAuthSliderPageAdapter;

public class SetUpKey extends BaseActivity {

    private GoogleAuthSliderPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_key);
        getSupportActionBar().hide();
        initiateObj();
        //init();
        initView();
        actions();

    }
    int nextCount=0;
    private void actions()
    {
        findViewById(R.id.next_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               nextCount++;



              if(nextCount>3)
               {
                Intent intent=new Intent(SetUpKey.this,Verification.class);
                intent.putExtra(DefaultConstants.google_sceret_code,getIntent().getStringExtra(DefaultConstants.google_sceret_code));
                intent.putExtra(DefaultConstants.status,getIntent().getStringExtra(DefaultConstants.status));
                startActivityForResult(intent,1001);
               }
               else
               {
                   viewPager.setCurrentItem(nextCount);
               }

            }
        });
    }



    TabLayout tabLayout;
    ViewPager viewPager;
    private void initView()
    {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(" "));
        tabLayout.addTab(tabLayout.newTab().setText(" "));
        tabLayout.addTab(tabLayout.newTab().setText(" "));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tablayout();

    }


    void tablayout() {

        adapter = new GoogleAuthSliderPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
                System.out.println("Tab postion==="+tab.getPosition());
                nextCount=tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
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

    @Override
    protected void onResume() {
        super.onResume();
        nextCount=0;
        viewPager.setCurrentItem(nextCount);
    }
}
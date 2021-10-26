package com.web.instafx;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.web.instafx.fragments.HomeFragment;
import com.web.instafx.fragments.MainFundFragment;
import com.web.instafx.fragments.QuickBuyFragment;
import com.web.instafx.orderpackage.MyOrderFragment;
import com.web.instafx.search_currency.SearchCurrencyScreen;
import com.web.instafx.setting_profile.SettingProfileScreen;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

public class MainActivity extends BaseActivity
{

    private String quickBuy="quick",fundFrg="fund",home="home",order="order";
    private QuickBuyFragment activefragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateObj();
        getSupportActionBar().hide();
        MarketFragment();
        bottomNavigation();
        init();
        savePreferences.savePreferencesData(MainActivity.this,"true", UtilClass.isLogin);
      }

    private void init()
    {
        findViewById(R.id.img_somoreoptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // showMoreOptions(v);
            }
        });





        findViewById(R.id.profileIC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(MainActivity.this, SettingProfileScreen.class);
                startActivity(intent);
            }
        });

    }

    private void MarketFragment()
    {

        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        replaceMainFragment(homeFragment,home);
    }

    private void orderFragment()
    {
        MyOrderFragment myOrderFrg = new MyOrderFragment();
        Bundle args = new Bundle();
        args.putString(DefaultConstants.CurrencyName,"");
        myOrderFrg.setArguments(args);
        replaceMainFragment(myOrderFrg,order);
    }

    private void fundFragment()
    {
        MainFundFragment fundFragment = new MainFundFragment();
        Bundle args = new Bundle();
        fundFragment.setArguments(args);
        replaceMainFragment(fundFragment,fundFrg);
    }

    private void loadQuickBuyFragment()
    {
        QuickBuyFragment fundFragment = new QuickBuyFragment();
        Bundle args = new Bundle();
        fundFragment.setArguments(args);
        replaceMainFragment(fundFragment,quickBuy);
    }


    private void replaceMainFragment(Fragment fragment, String tag)
    {

        FragmentTransaction ft_main = getSupportFragmentManager().beginTransaction();
        ft_main.replace(R.id.fragment_container, fragment);
        //ft_main.addToBackStack(tag);
        ft_main.commit();

        if(tag.equalsIgnoreCase(home))
        {
            findViewById(R.id.img_search).setVisibility(View.VISIBLE);
            findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent(MainActivity.this, SearchCurrencyScreen.class);
                    intent.putExtra(DefaultConstants.callfrom,home);
                    startActivity(intent);
                }
            });
        }
        else if(tag.equalsIgnoreCase(quickBuy))
        {
            findViewById(R.id.img_search).setVisibility(View.VISIBLE);
            activefragment=(QuickBuyFragment) fragment;
            findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent(MainActivity.this, SearchCurrencyScreen.class);
                    intent.putExtra(DefaultConstants.callfrom,DefaultConstants.quick);
                    intent.putExtra(DefaultConstants.pair_data,((QuickBuyFragment)fragment).pairs_info+"");
                    startActivityForResult(intent,1001);
                }
            });
        }
        else
        {
            findViewById(R.id.img_search).setVisibility(View.INVISIBLE);
        }
    }


    private void bottomNavigation()
    {
       final TextView txt_market =findViewById(R.id.txt_market);
       final TextView txt_order =findViewById(R.id.text_order);
       final TextView txt_fund =findViewById(R.id.txt_fund);
       final TextView txt_quicknuy =findViewById(R.id.txt_quicknuy);
       if(txt_market!=null)
        {
           txt_market.setTextColor(getResources().getColor(R.color.black));
           txt_order.setTextColor(getResources().getColor(R.color.grey_dark));
           txt_fund.setTextColor(getResources().getColor(R.color.grey_dark));
            txt_quicknuy.setTextColor(getResources().getColor(R.color.grey_dark));

           txt_market.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   txt_market.setTextColor(getResources().getColor(R.color.black));
                   txt_order.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_fund.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_quicknuy.setTextColor(getResources().getColor(R.color.grey_dark));
                   MarketFragment();
               }
           });

           txt_order.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   txt_market.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_order.setTextColor(getResources().getColor(R.color.black));
                   txt_fund.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_quicknuy.setTextColor(getResources().getColor(R.color.grey_dark));
                   orderFragment();
               }
           });

           txt_fund.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   txt_market.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_order.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_fund.setTextColor(getResources().getColor(R.color.black));
                   txt_quicknuy.setTextColor(getResources().getColor(R.color.grey_dark));
                   fundFragment();
               }
           });

            txt_quicknuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_market.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_order.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_fund.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_quicknuy.setTextColor(getResources().getColor(R.color.black));
                    loadQuickBuyFragment();
                }
            });

       }

    }



    int exitCount=1;
    @Override
    public void onBackPressed() {
        if(exitCount>=2)
        {
            finishAffinity();
            super.onBackPressed();
        }
        else
        {
            Toast.makeText(MainActivity.this,"Tap again to exit "+getResources().getString(R.string.app_name)+" app",Toast.LENGTH_LONG).show();
            exitCount++;



            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitCount=1;
                }
            }, 3000);


        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001)//Return from Search Fragment
        {
            if(data!=null)
            {
                try {

                    JSONObject data1=new JSONObject(data.getStringExtra("data"));
                    if(activefragment !=null)
                    {
                        activefragment.buysellDialog(data1);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }


    }
}
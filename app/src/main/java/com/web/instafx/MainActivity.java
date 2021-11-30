package com.web.instafx;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.web.instafx.fragments.FundFragment;
import com.web.instafx.fragments.HomeFragment;
import com.web.instafx.fragments.PromotionalFrg;
import com.web.instafx.fragments.QuickBuyFragment;
import com.web.instafx.orderpackage.MyOrderFragment;
import com.web.instafx.search_currency.SearchCurrencyScreen;
import com.web.instafx.settings.PasscodeSetting;
import com.web.instafx.settings.SecuritySettings;
import com.web.instafx.settings.SettingProfileScreen;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

public class MainActivity extends BaseActivity
{

    private String quickBuy="quick",fundFrg="fund",home="home",order="order";
    private QuickBuyFragment activefragment;

    private TextView txt_promotoion,txt_market,txt_order,txt_fund,txt_quicknuy;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateObj();
        getSupportActionBar().hide();
        bottomNavigation();
        promotionalPage();

        init();
        showPasscode();
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



    private void promotionalPage()
    {
        txt_promotoion.setAlpha(1f);
        txt_market.setAlpha(.5f);
        txt_order.setAlpha(.5f);
        txt_fund.setAlpha(.5f);
        txt_quicknuy.setAlpha(.5f);
        PromotionalFrg promotionalFrg = new PromotionalFrg();
        Bundle args = new Bundle();
        promotionalFrg.setArguments(args);
        replaceMainFragment(promotionalFrg,"promotion");
    }

    public void MarketFragment()
    {
        txt_promotoion.setAlpha(.5f);
        txt_market.setAlpha(1f);
        txt_order.setAlpha(.5f);
        txt_fund.setAlpha(.5f);
        txt_quicknuy.setAlpha(.5f);
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        replaceMainFragment(homeFragment,home);
    }

    private void orderFragment()
    {
        txt_promotoion.setAlpha(.5f);
        txt_market.setAlpha(.5f);
        txt_order.setAlpha(1f);
        txt_fund.setAlpha(.5f);
        txt_quicknuy.setAlpha(.5f);
        MyOrderFragment myOrderFrg = new MyOrderFragment();
        Bundle args = new Bundle();
        args.putString(DefaultConstants.CurrencyName,"");
        myOrderFrg.setArguments(args);
        replaceMainFragment(myOrderFrg,order);
    }

    private void fundFragment()
    {
        txt_promotoion.setAlpha(.5f);
        txt_market.setAlpha(.5f);
        txt_order.setAlpha(.5f);
        txt_fund.setAlpha(1f);
        txt_quicknuy.setAlpha(.5f);
        FundFragment fundFragment = new FundFragment();
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
        txt_promotoion =findViewById(R.id.txt_promotoion);
        txt_market =findViewById(R.id.txt_market);
        txt_order =findViewById(R.id.text_order);
        txt_fund =findViewById(R.id.txt_fund);
        txt_quicknuy =findViewById(R.id.txt_quicknuy);


            txt_promotoion.setTextColor(getResources().getColor(R.color.black));
            txt_market.setAlpha(.5f);
            txt_order.setAlpha(.5f);
            txt_fund.setAlpha(.5f);
            txt_quicknuy.setAlpha(.5f);


            txt_promotoion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    promotionalPage();
                }
            });


           txt_market.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   MarketFragment();
               }
           });

           txt_order.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   orderFragment();
               }
           });

           txt_fund.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   fundFragment();
               }
           });

            txt_quicknuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_promotoion.setAlpha(.5f);
                    txt_market.setAlpha(.5f);
                    txt_order.setAlpha(.5f);
                    txt_fund.setAlpha(.5f);
                    txt_quicknuy.setAlpha(1f);
                    loadQuickBuyFragment();
                }
            });

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


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void showPasscode()
    {
        String isPasscode=savePreferences.reterivePreference(MainActivity.this,DefaultConstants.isPasscodeActive).toString();
        System.out.println("IS passcode==="+isPasscode);
        if(isPasscode.equalsIgnoreCase("on"))
        {

            Intent intent = new Intent(MainActivity.this, PasscodeSetting.class);
            intent.putExtra(DefaultConstants.callfrom, DefaultConstants.pinVerify);
            startActivityForResult(intent, 1001);
        }
    }
}
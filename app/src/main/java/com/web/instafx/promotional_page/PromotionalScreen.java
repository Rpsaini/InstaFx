package com.web.instafx.promotional_page;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.activity_log.ActivityLogScreens;
import com.web.instafx.activity_log.adapter.ActivityLogsAdapter;
import com.web.instafx.utilpackage.SlidingImage_Adapter;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class PromotionalScreen extends BaseActivity {
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final String[] IMAGES= {"https://via.placeholder.com/300/09f/fff.png"};
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    private ImageView bottomBannerImage;
    private TextView totalWorthTV;
    private RecyclerView popularCurrencyRV;
    private TextView review_tv,done_tv;
    private ImageView submit_line,review_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotinal_screen);
        initiateObj();
        initV();
        getPromotionDetails();
        changeHeaderLayoutColorByKycStatus();
    }
    private void initV(){

        review_tv=findViewById(R.id.review_tv);
        done_tv=findViewById(R.id.done_tv);
        submit_line=findViewById(R.id.submit_line);
        review_line=findViewById(R.id.review_line);



        bottomBannerImage=findViewById(R.id.bottomBannerImage);
        totalWorthTV=findViewById(R.id.total_worthTV);
        popularCurrencyRV=findViewById(R.id.popular_currenciesRV);
        findViewById(R.id.backIC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void promotionalTopImage(JSONArray dataAr){

        mPager = findViewById(R.id.banner_pager);
        mPager.setAdapter(new BannerSlidingImageAdapter(PromotionalScreen.this,dataAr));
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        NUM_PAGES =IMAGES.length;
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }
    private void setAdapterData(JSONArray dataAr){
        PopularCurrencyAdapter mAdapter = new PopularCurrencyAdapter(PromotionalScreen.this,dataAr);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(PromotionalScreen.this, LinearLayoutManager.HORIZONTAL, false);
        popularCurrencyRV.setLayoutManager(horizontalLayoutManager);
        popularCurrencyRV.setItemAnimator(new DefaultItemAnimator());
        popularCurrencyRV.setAdapter(mAdapter);
    }
    private void getPromotionDetails()
    {
        try {
            Map<String, String> m = new LinkedHashMap<>();
            m.put("token", savePreferences.reterivePreference(PromotionalScreen.this, DefaultConstants.token) + "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("DeviceToken", getDeviceToken() + "");

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);
            Log.e("OrderDetails","request param::"+m);

            new ServerHandler().sendToServer(PromotionalScreen.this, getApiUrl() + "get-promotions", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        Log.e("OrderDetails","OrderDetails::"+dta);


                        /*   {"status":true,"balance":"1068.6659139100018","currency_data":[{"price":"4809078.0903","symbol":"BTC","icon":"https:\/\/instfx.com\/front\/resources\/img\/currency-icons\/BTC.png"},{"price":"339600.60654","symbol":"ETH","icon":"https:\/\/instfx.com\/front\/resources\/img\/currency-icons\/ETH.png"},{"price":"17453.550012","symbol":"LTC","icon":"https:\/\/instfx.com\/front\/resources\/img\/currency-icons\/LTC.png"},
                        {"price":"87.8263","symbol":"XRP","icon":"https:\/\/instfx.com\/front\/resources\/img\/currency-icons\/XRP.png"},{"price":"80.7604","symbol":"USDT","icon":"https:\/\/instfx.com\/front\/resources\/img\/currency-icons\/USDT.png"}],
                        "top_banner":[{"0":"https:\/\/instfx.com\/front\/resources\/img\/top-banner.png","1":"https:\/\/instfx.com\/front\/resources\/img\/top-banner.png","2":"https:\/\/instfx.com\/front\/resources\/img\/top-banner.png"}],
                        "bottom_banner":"https:\/\/instfx.com\/front\/resources\/img\/bottom-app-banner.png","code":200}
                         */

                        JSONObject obj = new JSONObject(dta);
                        if (obj.getBoolean("status")) {
                            try {
                                //  JSONObject detailObj = new JSONObject(obj.getString("order_detail"));
                                totalWorthTV.setText(getString(R.string.inr_symbol)+" "+obj.getString("balance"));
                                setAdapterData(obj.getJSONArray("currency_data"));
                                promotionalTopImage(obj.getJSONArray("top_banner"));
                                showImage(obj.getString("bottom_banner"),bottomBannerImage);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            alertDialogs.alertDialog(PromotionalScreen.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed)
                                {
                                    unauthorizedAccess(obj);

                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void showImage(final String url, final ImageView header_img) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(PromotionalScreen.this)
                        .load(url)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(3)))
                        .into(header_img);
            }
        });
    }
    private void changeHeaderLayoutColorByKycStatus(){
        String kycStatus=  savePreferences.reterivePreference(PromotionalScreen.this, DefaultConstants.kyc_status).toString();



        if(kycStatus.equals("1")){
            submit_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));
            review_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
            review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_review, 0, 0);
            done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_done, 0, 0);


        }
        else if(kycStatus.equals("2")){
            review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_review, 0, 0);
            done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_done, 0, 0);

            review_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));
            submit_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));


        }
        else if(kycStatus.equals("3")){
            review_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
            submit_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
            review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_review, 0, 0);
            done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_done, 0, 0);


        }
    }
}

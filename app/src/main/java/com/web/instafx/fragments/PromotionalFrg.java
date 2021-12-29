package com.web.instafx.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.web.instafx.DefaultConstants;
import com.web.instafx.MainActivity;
import com.web.instafx.R;

import com.web.instafx.adapters.PapularCurrenciesAdapter;
import com.web.instafx.fiatdepositwithdraw.ShowFiatCurrencyDepositWithdraw;
import com.web.instafx.imageslider.SliderAdapter;
import com.web.instafx.imageslider.SliderItem;
import com.web.instafx.pairdetailfragments.PairDetailView;
import com.web.instafx.utilpackage.SlidingImage_Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class PromotionalFrg extends Fragment {
    private View view;
    private MainActivity mainActivity;
    private TextView total_worthTV;
    private TextView review_tv, done_tv, buy_bitCoinTV,depositInrTV;
    private ImageView submit_line, review_line;
    public PromotionalFrg() {
        // Required empty public constructor
    }

    public static PromotionalFrg newInstance(String param1, String param2)
    {
        PromotionalFrg fragment = new PromotionalFrg();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_promotional_frg, container, false);
        mainActivity = (MainActivity) getActivity();

        getPromotions();
        init();
        sliderImageTop();
        sliderImageBottom();
        return view;
    }

    private void init() {
        total_worthTV = view.findViewById(R.id.total_worthTV);
        review_tv = view.findViewById(R.id.review_tv);
        done_tv = view.findViewById(R.id.done_tv);
        submit_line = view.findViewById(R.id.submit_line);
        review_line = view.findViewById(R.id.review_line);
        buy_bitCoinTV = view.findViewById(R.id.buy_bitCoinTV);
        depositInrTV = view.findViewById(R.id.depositInrTV);
        view.findViewById(R.id.exploreCurrenciesTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.MarketFragment();
            }
        });
      }


    private void getPromotions() {
        final Map<String, String> m = new HashMap<>();
        m.put("token", mainActivity.savePreferences.reterivePreference(mainActivity, DefaultConstants.token) + "");
        m.put("DeviceToken", mainActivity.getDeviceToken() + "");
        m.put("currency", "INR");
        final Map<String, String> obj = new HashMap<>();
        obj.put("X-API-KEY", mainActivity.getXapiKey());
        obj.put("Rtoken", mainActivity.getNewRToken() + "");

        new ServerHandler().sendToServer(mainActivity, mainActivity.getApiUrl() + "get-promotions", m, 0, obj, 20000, R.layout.progressbar, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {
                    JSONObject jsonObject = new JSONObject(dta);
                    System.out.println("Promotional data===" + jsonObject);
                    if (jsonObject.getBoolean("status")) {
                        try {

                            if(jsonObject.has("token"))
                            {
                                mainActivity.savePreferences.savePreferencesData(mainActivity, jsonObject.getString("token"), DefaultConstants.token);
                                mainActivity.savePreferences.savePreferencesData(mainActivity, jsonObject.getString("r_token"), DefaultConstants.r_token);
                            }

                            JSONArray popular_currency = jsonObject.getJSONArray("popular_currency");
                            String kyc_status = jsonObject.getString("kyc_status");
                            mainActivity.savePreferences.savePreferencesData(mainActivity, jsonObject.getString("kyc_status"), DefaultConstants.kyc_status);

//                          JSONObject balance_data = jsonObject.getJSONObject("balance_data");
                            JSONObject pair_data = jsonObject.getJSONObject("pair_data");
                            JSONObject balances = jsonObject.getJSONObject("balances");
                            JSONArray top_banner = jsonObject.getJSONArray("top_banner");
                            JSONArray bottom_banner = jsonObject.getJSONArray("bottom_banner");
                           //JSONObject banks = jsonObject.getJSONObject("banks");
                            total_worthTV.setText(jsonObject.getString("balance") + "INR");

                            setKycStatus(kyc_status);
                            renewItems(top_banner, "top");
                            renewItems(bottom_banner, "bottom");
                            showPapularCurriencies(popular_currency);
                            withdrawinr(balances);
                            buyBtc(pair_data);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        mainActivity.alertDialogs.alertDialog(mainActivity, getResources().getString(R.string.Response), jsonObject.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {
                                mainActivity.unauthorizedAccess(jsonObject);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void setKycStatus(String kycStatus) {
        if (kycStatus.equals("1")) {

            submit_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));
            review_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
            review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_review, 0, 0);
            done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_done, 0, 0);


        } else if (kycStatus.equals("2")) {

            review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_review, 0, 0);
            done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_done, 0, 0);

            review_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));
            submit_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));


        } else if (kycStatus.equals("3")) {
            review_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
            submit_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
            review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_review, 0, 0);
            done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_done, 0, 0);


        }
    }


    SliderView sliderView, image_slider_bootom;
    SliderAdapter adapter, adapteri_mage_slider_bootom;

    private void sliderImageTop() {
        sliderView = view.findViewById(R.id.image_slider);
        adapter = new SliderAdapter(getActivity());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

    }

    private void sliderImageBottom() {
        image_slider_bootom = view.findViewById(R.id.image_slider_bootom);
        adapteri_mage_slider_bootom = new SliderAdapter(getActivity());
        image_slider_bootom.setSliderAdapter(adapteri_mage_slider_bootom);
        image_slider_bootom.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        image_slider_bootom.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        image_slider_bootom.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        image_slider_bootom.setIndicatorSelectedColor(Color.WHITE);
        image_slider_bootom.setIndicatorUnselectedColor(Color.GRAY);
        image_slider_bootom.setScrollTimeInSec(3);
        image_slider_bootom.setAutoCycle(true);
        image_slider_bootom.startAutoCycle();

    }


    public void renewItems(JSONArray dataAr, String type) {
        try {
            List<SliderItem> sliderItemList = new ArrayList<>();
            for (int i = 0; i < dataAr.length(); i++) {
                SliderItem sliderItem = new SliderItem();
                sliderItem.setDescription("Slider Item " + i);
                sliderItem.setImageUrl(dataAr.getString(i) + "");
                sliderItemList.add(sliderItem);
            }
            if (type.equalsIgnoreCase("top")) {
                adapter.renewItems(sliderItemList);
            } else {
                adapteri_mage_slider_bootom.renewItems(sliderItemList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showPapularCurriencies(JSONArray dataAr) {
        RecyclerView popular_currenciesRV = view.findViewById(R.id.popular_currenciesRV);
        PapularCurrenciesAdapter linesViewAdapter = new PapularCurrenciesAdapter(dataAr, mainActivity);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        popular_currenciesRV.setLayoutManager(horizontalLayoutManagaer);
        popular_currenciesRV.setItemAnimator(new DefaultItemAnimator());
        popular_currenciesRV.setAdapter(linesViewAdapter);
    }


    private void buyBtc(JSONObject pairData) {
        Intent intent = new Intent(mainActivity, PairDetailView.class);
        intent.putExtra(DefaultConstants.pair_data, pairData + "");

        buy_bitCoinTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.startActivity(intent);
            }
        });


    }

    private void withdrawinr(JSONObject balances)
    {
        Intent intent = new Intent(getActivity(), ShowFiatCurrencyDepositWithdraw.class);
        intent.putExtra("data", balances + "");

        depositInrTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.startActivity(intent);
            }
        });

    }



}
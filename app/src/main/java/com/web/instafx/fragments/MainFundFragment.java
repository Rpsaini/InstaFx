package com.web.instafx.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.google.android.material.tabs.TabLayout;
import com.web.instafx.DefaultConstants;
import com.web.instafx.FundHistoryActivity;
import com.web.instafx.R;
import com.web.instafx.adapters.FundPagerAdapter;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainFundFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View view;
    private FundHistoryActivity fundHistory;
    public static MainFundFragment newInstance(String param1, String param2) {
        MainFundFragment fragment = new MainFundFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_main_fund, container, false);
        fundHistory=(FundHistoryActivity)getActivity();
        tablayout();
         getIntentData();
        return view;
    }

    void tablayout()
    {
        tabLayout = (TabLayout)view. findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.deposit_history)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.withdrawal_history)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) view.findViewById(R.id.pager);


    }

    private void callPagerAdapter(JSONArray depositAr,JSONArray withdrawAr)
    {
        FundPagerAdapter adapter = new FundPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(),depositAr,withdrawAr);
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


    private void getIntentData() {
        try {
            JSONObject data = new JSONObject(getArguments().getString("data"));
            getHistoryData(data.getString("symbol"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getHistoryData(String currency) {


        Map<String, String> m = new HashMap<>();
        m.put("token", fundHistory.savePreferences.reterivePreference(fundHistory, DefaultConstants.token) + "");
        m.put("DeviceToken", fundHistory.getDeviceToken() + "");
        m.put("Version", fundHistory.getAppVersion() + "");
        m.put("PlatForm", "Android");
        m.put("Timestamp", System.currentTimeMillis() + "");
        m.put("currency", currency);
        System.out.println("Data===="+m);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("X-API-KEY", UtilClass.xApiKey);
        headerMap.put("Rtoken", fundHistory.getNewRToken() + "");
        new ServerHandler().sendToServer(fundHistory, fundHistory.getApiUrl() + "transactions-history", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {

                    JSONObject obj = new JSONObject(dta);
                    if(obj.getBoolean("status")) {
                        try {
                            if (obj.has("token"))
                            {
                                fundHistory.savePreferences.savePreferencesData(fundHistory, obj.getString("token"), DefaultConstants.token);
                                fundHistory.savePreferences.savePreferencesData(fundHistory, obj.getString("r_token"), DefaultConstants.r_token);
                            }
                            callPagerAdapter(obj.getJSONArray("deposit"),obj.getJSONArray("withdraw"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        fundHistory.alertDialogs.alertDialog(fundHistory, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {
                                fundHistory.unauthorizedAccess(obj);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
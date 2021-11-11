package com.web.instafx.fragments;

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
import android.widget.RelativeLayout;

import com.web.instafx.MainActivity;
import com.web.instafx.R;
import com.web.instafx.adapters.MarketAdapter;
import com.web.instafx.utilpackage.SlidingImage_Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class CommonFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;
    public CommonFragment() {
        // Required empty public constructor
    }
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final String[] IMAGES= {"https://via.placeholder.com/300/09f/fff.png"};
    private ArrayList<String> ImagesArray = new ArrayList<String>();

    public static CommonFragment newInstance(String param1, String param2) {
        CommonFragment fragment = new CommonFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    private void pramotionalImage(){
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = view.findViewById(R.id.banner_pager);
        mPager.setAdapter(new SlidingImage_Adapter(mainActivity,ImagesArray));
        CircleIndicator indicator = view.findViewById(R.id.indicator);
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_common, container, false);
        mainActivity = (MainActivity) getActivity();
        pramotionalImage();
        loadData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {

        }
    }

    private void loadData()
    {
       if(getArguments()!=null)
       {
           try {
               if(HomeFragment.tabsHeaderKeys.size()>0) {
                   int pos = Integer.parseInt(getArguments().getString("pos"));
                   JSONObject headerData = HomeFragment.tabsHeaderKeys.get(pos);
                   String pairName = headerData.getString("pair_name");

                   JSONArray dataAr = HomeFragment.commonMap.get(pairName);
                   init(dataAr, pos);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }

    private void init(JSONArray dataObj,int pos)
    {
        if(dataObj!=null)
        {
            RecyclerView recycler_view_market = view.findViewById(R.id.recycler_view_market);
            RelativeLayout relativeLayout = view.findViewById(R.id.rr_nodata_view);
            if (dataObj.length() == 0) {
                relativeLayout.setVisibility(View.VISIBLE);
                recycler_view_market.setVisibility(View.GONE);
            } else {
                relativeLayout.setVisibility(View.GONE);
                recycler_view_market.setVisibility(View.VISIBLE);
            }
            MarketAdapter commonAdapter = new MarketAdapter(dataObj, (MainActivity) getActivity());
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recycler_view_market.setLayoutManager(horizontalLayoutManagaer);
            recycler_view_market.setItemAnimator(new DefaultItemAnimator());
            recycler_view_market.setAdapter(commonAdapter);
            HomeFragment.marketAdapterMap.put(pos, commonAdapter);
        }
    }
}
package com.web.instafx.search_currency;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.adapters.QuickBuySearchAdapter;
import com.web.instafx.fragments.HomeFragment;
import com.web.instafx.search_currency.adapter.SearchCurrencyAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class SearchCurrencyScreen extends BaseActivity {
    private ImageView backIC;
    private RecyclerView activityLogsRV;
    private EditText tvTitle;
    private ArrayList<JSONObject> filteredAr = new ArrayList<>();
    private ArrayList<JSONObject> allDataAr = new ArrayList<>();

    private SearchCurrencyAdapter msearchAdapter;
    private QuickBuySearchAdapter quickSearchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_currency_screen);
        getSupportActionBar().hide();
        initView();
        setAdapterData();
        setOnClickListener();
    }

    private void initView() {
        backIC = findViewById(R.id.backIC);
        activityLogsRV = findViewById(R.id.currency_search_rv);
        tvTitle = findViewById(R.id.tvTitle);

    }

    private void setAdapterData() {
        try {
            allDataAr = new ArrayList<>();
            if (getIntent().getStringExtra(DefaultConstants.callfrom).equalsIgnoreCase(DefaultConstants.home)) {
                for (Map.Entry<String, JSONArray> m : HomeFragment.commonMap.entrySet()) {
                    JSONArray currencyAr = m.getValue();
                    for (int x = 0; x < currencyAr.length(); x++) {
                        allDataAr.add(currencyAr.getJSONObject(x));
                        filteredAr.add(currencyAr.getJSONObject(x));
                    }
                }
                msearchAdapter = new SearchCurrencyAdapter(SearchCurrencyScreen.this, filteredAr);
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(SearchCurrencyScreen.this, LinearLayoutManager.VERTICAL, false);
                activityLogsRV.setLayoutManager(horizontalLayoutManager);
                activityLogsRV.setItemAnimator(new DefaultItemAnimator());
                activityLogsRV.setAdapter(msearchAdapter);
                searchCurrency();
            } else if (getIntent().getStringExtra(DefaultConstants.callfrom).equalsIgnoreCase(DefaultConstants.quick)) {
                try {
                    JSONArray quickBuyPairAr = new JSONArray(getIntent().getStringExtra(DefaultConstants.pair_data));
                    for (int x = 0; x < quickBuyPairAr.length(); x++) {
                        allDataAr.add(quickBuyPairAr.getJSONObject(x));
                        filteredAr.add(quickBuyPairAr.getJSONObject(x));
                    }
                    quickSearchAdapter = new QuickBuySearchAdapter(filteredAr, SearchCurrencyScreen.this);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(SearchCurrencyScreen.this, LinearLayoutManager.VERTICAL, false);
                    activityLogsRV.setLayoutManager(horizontalLayoutManager);
                    activityLogsRV.setItemAnimator(new DefaultItemAnimator());
                    activityLogsRV.setAdapter(quickSearchAdapter);
                    searchCurrency();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void searchCurrency() {
        tvTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s.length() > 0)
                    {
                        filteredAr.clear();
                        for (int x = 0; x < allDataAr.size(); x++) {
                            JSONObject dataObj = allDataAr.get(x);
                            if (getIntent().getStringExtra(DefaultConstants.callfrom).equalsIgnoreCase(DefaultConstants.home))
                            {
                                if(dataObj.getString("symbol").toLowerCase().contains(s.toString().toLowerCase()) || dataObj.getString("name").toLowerCase().contains(s.toString().toLowerCase())) {
                                    filteredAr.add(dataObj);
                                }
                            }
                            else
                            {
                                if(dataObj.getString("base_name").toLowerCase().contains(s.toString().toLowerCase()) || dataObj.getString("base").toLowerCase().contains(s.toString().toLowerCase())) {
                                    filteredAr.add(dataObj);
                                }
                            }

                        }

                        if (getIntent().getStringExtra(DefaultConstants.callfrom).equalsIgnoreCase(DefaultConstants.home))
                        {
                            msearchAdapter.notifyDataSetChanged();


                        } else {
                            quickSearchAdapter.notifyDataSetChanged();
                        }

                    } else {
                        filteredAr.clear();
                        for (int x = 0; x < allDataAr.size(); x++)
                        {
                            filteredAr.add(allDataAr.get(x));
                        }
                        if (getIntent().getStringExtra(DefaultConstants.callfrom).equalsIgnoreCase(DefaultConstants.home)) {
                            msearchAdapter.notifyDataSetChanged();
                        } else {
                            quickSearchAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
    private void setOnClickListener() {
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void sendBack(JSONObject data)
    {
        Intent intent=new Intent();
        intent.putExtra("data",data+"");
        setResult(RESULT_OK,intent);
        finish();

    }

}

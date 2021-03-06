package com.web.instafx.fiatdepositwithdraw;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.adapters.BankDetailsAdapters;
import com.web.instafx.deposit.DepositeInrActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowFiatCurrencyDepositWithdraw extends BaseActivity {
    private String isKycStatus = "";
    private boolean authenticator = false;
    private RecyclerView recycler_view_bankaddress;
    private String symbol="";
   TextView txt_withdrawinr,txt_deposit_inr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fiat_currency_deposit_withdraw);

        getSupportActionBar().hide();
        initiateObj();
        init();
        getDepsoitAddress();
    }

    private void init() {
        try {
            JSONObject dataObj = new JSONObject(getIntent().getStringExtra("data"));

            symbol = dataObj.getString("symbol");
            String availableBalance = dataObj.getString("available_balance");
            final String icon = dataObj.getString("icon");

            ImageView txt_currency_image = findViewById(R.id.txt_currency_image);
            txt_withdrawinr =findViewById(R.id.txt_withdrawinr);
            txt_deposit_inr = findViewById(R.id.txt_deposit_inr);
            TextView txt_title = findViewById(R.id.txt_title);
            TextView total_balance = findViewById(R.id.total_balance);
            recycler_view_bankaddress = findViewById(R.id.recycler_view_bankaddress);
            total_balance.setText(availableBalance);

            txt_title.setText("PLEASE PAY ATTENTION! THE COIN YOU SELECTED IS : " + symbol);
            txt_withdrawinr.setText("Withdraw "+symbol);
            txt_deposit_inr.setText("Deposit "+symbol);
            showImage(icon, txt_currency_image);

            findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


            txt_deposit_inr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShowFiatCurrencyDepositWithdraw.this, DepositeInrActivity.class);
                    intent.putExtra(DefaultConstants.symbol,symbol);
                    intent.putExtra(DefaultConstants.pair_data,dataObj+"");
                    startActivityForResult(intent, 1001);
                }
            });

            txt_withdrawinr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShowFiatCurrencyDepositWithdraw.this, WithdrawInrActivity.class);
                    intent.putExtra("isGoogleAuth", authenticator);
                    intent.putExtra("currency", symbol);
                    intent.putExtra("balance", availableBalance);
                    intent.putExtra("icon", icon);
                    intent.putExtra(DefaultConstants.pair_data, dataObj+"");
                    startActivityForResult(intent, 1001);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showImage(final String url, final ImageView header_img) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(ShowFiatCurrencyDepositWithdraw.this)
                        .load(url)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(3)))
                        .into(header_img);
            }
        });
    }


    private void getDepsoitAddress() {
        try {
            final Map<String, String> m = new HashMap<>();
            m.put("token", savePreferences.reterivePreference(ShowFiatCurrencyDepositWithdraw.this, DefaultConstants.token) + "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("DeviceToken", getDeviceToken() + "");
            m.put("currency", symbol);

            final Map<String, String> obj = new HashMap<>();
            obj.put("X-API-KEY", getXapiKey());
            obj.put("Rtoken", getNewRToken() + "");

            new ServerHandler().sendToServer(ShowFiatCurrencyDepositWithdraw.this, getApiUrl() + "get-company-bank", m, 0, obj, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        JSONObject jsonObject = new JSONObject(dta);
                        if(jsonObject.getBoolean("status")) {
                            try {
                                authenticator = jsonObject.getBoolean("authenticator");
                                showBanksAddress(jsonObject.getJSONArray("banks"));
                                }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            alertDialogs.alertDialog(ShowFiatCurrencyDepositWithdraw.this, getResources().getString(R.string.Response), jsonObject.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed)
                                {
                                    unauthorizedAccess(jsonObject);
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showBanksAddress(JSONArray dataAr)
    {
        BankDetailsAdapters mAdapter = new BankDetailsAdapters(this, dataAr);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_view_bankaddress.setLayoutManager(horizontalLayoutManagaer);
        recycler_view_bankaddress.setItemAnimator(new DefaultItemAnimator());
        recycler_view_bankaddress.setAdapter(mAdapter);
    }
}
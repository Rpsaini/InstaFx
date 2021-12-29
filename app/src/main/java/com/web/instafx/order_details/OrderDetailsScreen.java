package com.web.instafx.order_details;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderDetailsScreen extends BaseActivity {
    private ImageView backIC=null;
    private TextView averagePriceTV,currencySymbolTV,buyTV,placeOnDateTV,filledAmtTV,average_priceValueTV,totalTV,feeTV,tradeDateTime,tradeFilled,tradePrice,limitTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_screen);
        getSupportActionBar().hide();
        initiateObj();
        initView();
        setOnClickListener();
        if(getIntent().getStringExtra("order_id")!=null)
        getOrderDetails(getIntent().getStringExtra("order_id"));
    }
    private void initView(){
        backIC =findViewById(R.id.backIC);
        averagePriceTV =findViewById(R.id.averagePriceTV);
        currencySymbolTV =findViewById(R.id.currencySymbolTV);
        placeOnDateTV =findViewById(R.id.placedDateTimeTV);
        buyTV =findViewById(R.id.buyTV);
        filledAmtTV =findViewById(R.id.filledAmmountValueTV);
        average_priceValueTV =findViewById(R.id.average_priceValueTV);
        totalTV =findViewById(R.id.totalValueTV);
        feeTV =findViewById(R.id.feesValueTV);
        tradeDateTime =findViewById(R.id.dateTimeTitleValueTV);
        tradeFilled =findViewById(R.id.filledTitleValueTV);
        tradePrice =findViewById(R.id.priceTitleValueTV);
        limitTV =findViewById(R.id.limitTV);
    }

    private void setOnClickListener(){
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getOrderDetails(String order_id)
    {

        try {
            Map<String, String> m = new LinkedHashMap<>();
            m.put("token", savePreferences.reterivePreference(OrderDetailsScreen.this, DefaultConstants.token) + "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("DeviceToken", getDeviceToken() + "");
            m.put("order_id", order_id);
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);

            new ServerHandler().sendToServer(OrderDetailsScreen.this, getApiUrl() + "get-order-details", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        Log.e("OrderDetails","OrderDetails::"+dta);


 /*                       {"status":true,"order_detail":{"side":"buy","type":"limit","order_state":"active",
                                "txn_fee":"1.65561133","pair":"LTC \/ INR","volume":"0.055902405",
                                "total_value":"829.4612766","price":"14980.875576",
                                "placed_on":"2021-10-28 18:21:02","executed_at":0},"code":200} */

                        JSONObject obj = new JSONObject(dta);
                        if (obj.getBoolean("status")) {
                            try {
                                JSONObject detailObj = new JSONObject(obj.getString("order_detail"));
                                currencySymbolTV.setText(detailObj.getString("pair"));
                                buyTV.setText(detailObj.getString("side"));
                                filledAmtTV.setText(detailObj.getString("volume"));
                                limitTV.setText(detailObj.getString("type"));
                                placeOnDateTV.setText(detailObj.getString("placed_on"));
                                totalTV.setText(detailObj.getString("total_value"));
                                feeTV.setText(detailObj.getString("txn_fee"));
                                average_priceValueTV.setText(detailObj.getString("price"));
                                tradePrice.setText(detailObj.getString("price"));
                                tradeDateTime.setText(detailObj.getString("placed_on"));
                                tradeFilled.setText(detailObj.getString("volume"));

                                if(detailObj.getInt("executed_at")>0){
                                 averagePriceTV.setText(getString(R.string.average_price));
                                }
                                else {
                                    averagePriceTV.setText(getString(R.string.price));
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            alertDialogs.alertDialog(OrderDetailsScreen.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
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

}

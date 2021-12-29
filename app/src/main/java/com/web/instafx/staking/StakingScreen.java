package com.web.instafx.staking;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.dialogsnpickers.SimpleDialog;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;

import com.web.instafx.activity_log.ActivityLogScreens;
import com.web.instafx.activity_log.adapter.ActivityLogsAdapter;
import com.web.instafx.kyc.BankDetailScreen;
import com.web.instafx.kyc.adapter.SelectCategorySubCategoryAdapter;
import com.web.instafx.staking.adapter.StakeCoinAdapter;
import com.web.instafx.staking.adapter.StakingAdapter;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StakingScreen extends BaseActivity {
    private ImageView backIC;
    private RecyclerView auto_cgb_stacking_rv,cgb_stacking_rv,stake_coins_rv;
    private TextView stakingTV,cgb_stakeBT;
    TextView txt_choosePriceValueTV;
    private double rate;
    public String cgb_item_id;
    public String cgb_itemName;
    private JSONArray cbg_staked_array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stacking_screen);
        getSupportActionBar().hide();
        initiateObj();
        initView();
        setOnClickListener();
        getStakeInfoDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView(){
        backIC=findViewById(R.id.backIC);
        stakingTV=findViewById(R.id.staking_tv);

        auto_cgb_stacking_rv=findViewById(R.id.auto_cgb_stacking_rv);
        cgb_stacking_rv=findViewById(R.id.cgb_stacking_rv);
        stake_coins_rv=findViewById(R.id.coins_staked_rv);
        cgb_stakeBT=findViewById(R.id.cgb_stakeBT);
    }
    private void setOnClickListener(){
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cgb_stakeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCGBStackingDialog();
            }
        });
    }
    private void setCBGStackingRVAdapterData(JSONArray dataAr){
        StakingAdapter mAdapter = new StakingAdapter(StakingScreen.this,dataAr,"cgb_stacking_rv");
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(StakingScreen.this, LinearLayoutManager.VERTICAL, false);
        cgb_stacking_rv.setLayoutManager(horizontalLayoutManager);
        cgb_stacking_rv.setItemAnimator(new DefaultItemAnimator());
        cgb_stacking_rv.setAdapter(mAdapter);
    }
    private void setStakedCoinsAdapterData(JSONArray dataAr){
        StakeCoinAdapter mAdapter = new StakeCoinAdapter(dataAr,StakingScreen.this);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(StakingScreen.this, LinearLayoutManager.VERTICAL, false);
        stake_coins_rv.setLayoutManager(horizontalLayoutManager);
        stake_coins_rv.setItemAnimator(new DefaultItemAnimator());
        stake_coins_rv.setAdapter(mAdapter);
    }
    private void setAutoCBGStackingRVAdapterData(JSONArray dataAr){
        StakingAdapter mAdapter = new StakingAdapter(StakingScreen.this,dataAr,"");
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(StakingScreen.this, LinearLayoutManager.VERTICAL, false);
        auto_cgb_stacking_rv.setLayoutManager(horizontalLayoutManager);
        auto_cgb_stacking_rv.setItemAnimator(new DefaultItemAnimator());
        auto_cgb_stacking_rv.setAdapter(mAdapter);
    }
    Dialog showOrderPlacedConfirmDia;
    TextView orderConfirm;
    private void showCGBStackingDialog() {
        try
        {
            showOrderPlacedConfirmDia = new Dialog( StakingScreen.this);
            showOrderPlacedConfirmDia.setContentView(R.layout.staking_dailog);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = showOrderPlacedConfirmDia.getWindow();
            lp.copyFrom(window.getAttributes());
            showOrderPlacedConfirmDia.setCancelable(true);
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
            showOrderPlacedConfirmDia.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            showOrderPlacedConfirmDia.show();

            TextView currentPriceTV = showOrderPlacedConfirmDia.findViewById(R.id.currentPriceTV);
            txt_choosePriceValueTV = showOrderPlacedConfirmDia.findViewById(R.id.txt_choosePriceValueTV);
            EditText ed_amount = showOrderPlacedConfirmDia.findViewById(R.id.ed_amount);
            TextView feesTV = showOrderPlacedConfirmDia.findViewById(R.id.feesTV);

            String currentPriceValue="Current price 1CGB = "+rate+" USDT";

            currentPriceTV.setText(currentPriceValue);

            orderConfirm = showOrderPlacedConfirmDia.findViewById(R.id.confirmTV);
            TextView orderCancel = showOrderPlacedConfirmDia.findViewById(R.id.txt_cancelTV);
            txt_choosePriceValueTV.setText(cgb_itemName);
           ImageView closeBtn=showOrderPlacedConfirmDia.findViewById(R.id.closeBtn);
           ed_amount.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {

               }

               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                 if(count>0 && rate>0){
                     Double totalAmount=(Double.parseDouble(ed_amount.getText().toString()))/rate;
                     String currentPriceValue="You need to stake = "+totalAmount+" CGB";
                     feesTV.setText(currentPriceValue);
                 }
                 if(count==0){
                     String currentPriceValue="You need to stake = 0.00 CGB";
                     feesTV.setText(currentPriceValue);
                 }
               }

               @Override
               public void afterTextChanged(Editable s) {

               }
           });
           txt_choosePriceValueTV.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   selectTypeDialog();
               }
           });
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOrderPlacedConfirmDia.dismiss();
                }
            });

            orderConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  showOrderPlacedConfirmDia.dismiss();
                    if(ed_amount.getText().toString().isEmpty()){
                        alertDialogs.alertDialog(StakingScreen.this, getResources().getString(R.string.app_name), "Enter the Amount.", getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed)
                            {

                            }
                        });
                        return;
                    }
                    confirmStakeInfoDetails(cgb_item_id,ed_amount.getText().toString());
                }
            });

            orderCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOrderPlacedConfirmDia.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void getStakeInfoDetails()
    {
        try {
            Map<String, String> m = new LinkedHashMap<>();
            m.put("token", savePreferences.reterivePreference(StakingScreen.this, DefaultConstants.token) + "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("DeviceToken", getDeviceToken() + "");
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);

            new ServerHandler().sendToServer(StakingScreen.this, getApiUrl() + "stake-information", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        Log.e("OrderDetails","OrderDetails::"+dta);
                        JSONObject obj = new JSONObject(dta);
                        if (obj.getBoolean("status")) {
                            try {
                                cbg_staked_array=obj.getJSONArray("staking_settings");
                                setCBGStackingRVAdapterData(cbg_staked_array);
                                setAutoCBGStackingRVAdapterData(obj.getJSONArray("automatic_staking"));

                                setStakedCoinsAdapterData(obj.getJSONArray("user_stakings"));
                                rate=obj.getDouble("rate");
                                JSONObject object = (JSONObject) cbg_staked_array.get(0);
                                cgb_item_id=object.getString("id");
                                cgb_itemName=object.getString("name");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            alertDialogs.alertDialog(StakingScreen.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
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

    public void setKycType(String type) {
        cgb_itemName=type;
        Log.e("StakingScreen","id::"+cgb_item_id);
        Log.e("StakingScreen","name::"+cgb_itemName);

        txt_choosePriceValueTV.setText(type);
    }
    private void selectTypeDialog() {
        try {

            hideKeyboard(this);
            SimpleDialog simpleDialog = new SimpleDialog();
            final Dialog selectCategoryDialog = simpleDialog.simpleDailog(StakingScreen.this, R.layout.select_category_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
            RecyclerView select_category_recycle = selectCategoryDialog.findViewById(R.id.select_category_recycler);
            ImageView img_hideview = selectCategoryDialog.findViewById(R.id.img_hideview);
            final RelativeLayout ll_relativelayout = selectCategoryDialog.findViewById(R.id.ll_relativelayout);
            final TextView select_title = selectCategoryDialog.findViewById(R.id.select_title);
            final TextView select_sub_title = selectCategoryDialog.findViewById(R.id.select_sub_title);
            final TextView tv_done = selectCategoryDialog.findViewById(R.id.tv_done);
            animateUp(ll_relativelayout);
            select_title.setText(getResources().getString(R.string.type));
            select_sub_title.setText("");
            initHomeCategory(select_category_recycle,cbg_staked_array);


            img_hideview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downSourceDestinationView(ll_relativelayout, selectCategoryDialog);
                }
            });

            tv_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downSourceDestinationView(ll_relativelayout, selectCategoryDialog);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void initHomeCategory(RecyclerView select_category_recycle,JSONArray dataAr) {
        select_category_recycle.setNestedScrollingEnabled(false);
        select_category_recycle.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        select_category_recycle.setHasFixedSize(true);
        select_category_recycle.setItemAnimator(new DefaultItemAnimator());
        SelectCategorySubCategoryAdapter horizontalCategoriesAdapter = new SelectCategorySubCategoryAdapter(dataAr, this);
        select_category_recycle.setAdapter(horizontalCategoriesAdapter);
    }
    private void confirmStakeInfoDetails(String id,String amount)
    {
        try {
            Map<String, String> m = new LinkedHashMap<>();
            m.put("token", savePreferences.reterivePreference(StakingScreen.this, DefaultConstants.token) + "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("stake_type", id);
            m.put("amount",amount);
            m.put("DeviceToken", getDeviceToken() + "");
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);

            new ServerHandler().sendToServer(StakingScreen.this, getApiUrl() + "stake", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        Log.e("OrderDetails","OrderDetails::"+dta);
                        JSONObject obj = new JSONObject(dta);

                        if (obj.getBoolean("status")) {
                            try {
                                alertDialogs.alertDialog(StakingScreen.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                    @Override
                                    public void getDialogEvent(String buttonPressed)
                                    {
                                        //  unauthorizedAccess(obj);
                                        showOrderPlacedConfirmDia.dismiss();
                                        getStakeInfoDetails();
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            alertDialogs.alertDialog(StakingScreen.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed)
                                {
                                  //  unauthorizedAccess(obj);
                                    showOrderPlacedConfirmDia.dismiss();
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

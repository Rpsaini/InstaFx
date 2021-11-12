package com.web.instafx.staking;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.web.instafx.BaseActivity;
import com.web.instafx.R;

import com.web.instafx.staking.adapter.StakingAdapter;

import org.json.JSONObject;

import java.util.Map;

public class StakingScreen extends BaseActivity {
    private ImageView backIC;
    private RecyclerView auto_cgb_stacking_rv,cgb_stacking_rv;
    private TextView stakingTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stacking_screen);
        initiateObj();
        initView();
        setOnClickListener();
        setAutoCBGStackingRVAdapterData();
        setCBGStackingRVAdapterData();
    }
    private void initView(){
        backIC=findViewById(R.id.backIC);
        stakingTV=findViewById(R.id.staking_tv);
        auto_cgb_stacking_rv=findViewById(R.id.auto_cgb_stacking_rv);
        cgb_stacking_rv=findViewById(R.id.cgb_stacking_rv);
    }
    private void setOnClickListener(){
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        stakingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCGBStackingDialog();
            }
        });
    }
    private void setCBGStackingRVAdapterData(){
        StakingAdapter mAdapter = new StakingAdapter(StakingScreen.this,"cgb_stacking_rv");
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(StakingScreen.this, LinearLayoutManager.VERTICAL, false);
        cgb_stacking_rv.setLayoutManager(horizontalLayoutManager);
        cgb_stacking_rv.setItemAnimator(new DefaultItemAnimator());
        cgb_stacking_rv.setAdapter(mAdapter);
    }
    private void setAutoCBGStackingRVAdapterData(){
        StakingAdapter mAdapter = new StakingAdapter(StakingScreen.this,"");
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
            TextView txt_choosePriceValueTV = showOrderPlacedConfirmDia.findViewById(R.id.txt_choosePriceValueTV);
            TextView ed_amount = showOrderPlacedConfirmDia.findViewById(R.id.ed_amount);



            orderConfirm = showOrderPlacedConfirmDia.findViewById(R.id.confirmTV);
            TextView orderCancel = showOrderPlacedConfirmDia.findViewById(R.id.txt_cancelTV);

           ImageView closeBtn=showOrderPlacedConfirmDia.findViewById(R.id.closeBtn);

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOrderPlacedConfirmDia.dismiss();
                }
            });

            orderConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOrderPlacedConfirmDia.dismiss();
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

}

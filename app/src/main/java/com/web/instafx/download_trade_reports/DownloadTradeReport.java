package com.web.instafx.download_trade_reports;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.web.instafx.BaseActivity;
import com.web.instafx.R;

public class DownloadTradeReport  extends BaseActivity {
    private ImageView backIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_trade_report);

        initView();
        setOnClickListener();
    }

    private void initView(){
        backIC=findViewById(R.id.backIC);

    }

    private void setOnClickListener() {
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

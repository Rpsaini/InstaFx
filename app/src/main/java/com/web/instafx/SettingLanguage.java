package com.web.instafx;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SettingLanguage extends BaseActivity{
    private ImageView backIC=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_setting_screen);
        initiateObj();
        initView();
        setOnClickListener();
    }
    private void initView(){
        backIC =findViewById(R.id.backIC);
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

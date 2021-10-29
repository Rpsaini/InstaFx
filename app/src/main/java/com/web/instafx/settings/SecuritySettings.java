package com.web.instafx.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.web.instafx.BaseActivity;
import com.web.instafx.R;

public class SecuritySettings extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);
        initiateObj();
        init();
    }
    private void init()
    {
        Switch switch_apppasscode =findViewById(R.id.switch_apppasscode);
        switch_apppasscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch_apppasscode.isChecked())
                {

                }
                else
                {

                }
            }
        });



    }
}
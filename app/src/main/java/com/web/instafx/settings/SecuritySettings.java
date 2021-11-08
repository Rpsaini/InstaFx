package com.web.instafx.settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;

public class SecuritySettings extends BaseActivity {
    String isPasscodeActive;
    Switch switch_apppasscode;
    private ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);
        getSupportActionBar().hide();
        initiateObj();
        init();
    }
    private void init()
    {
         switch_apppasscode =findViewById(R.id.switch_apppasscode);
         img_back =findViewById(R.id.img_back);
         isPasscodeActive=savePreferences.reterivePreference(SecuritySettings.this, DefaultConstants.isPasscodeActive).toString();

         System.out.println("ispasscode==="+isPasscodeActive);
         if(isPasscodeActive.length()==0)
         {
             isPasscodeActive="off";
         }

        if(isPasscodeActive.equals("on"))
        {
            switch_apppasscode.setChecked(true);
        }
        else
        {
            switch_apppasscode.setChecked(false);
        }

        switch_apppasscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_apppasscode.isChecked())
                {
                    switch_apppasscode.setChecked(false);
                    Intent intent = new Intent(SecuritySettings.this, PasscodeSetting.class);
                    intent.putExtra(DefaultConstants.callfrom, DefaultConstants.pinSetup);
                    startActivityForResult(intent, 1001);
                }
                else
                {
                    switch_apppasscode.setChecked(true);
                    Intent intent = new Intent(SecuritySettings.this, PasscodeSetting.class);
                    intent.putExtra(DefaultConstants.callfrom, DefaultConstants.pinreset);
                    startActivityForResult(intent, 1001);
                }

            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001)
        {
            if(data!=null)
            {
                if(isPasscodeActive.equalsIgnoreCase("off"))
                {
                    switch_apppasscode.setChecked(true);
                    savePreferences.savePreferencesData(SecuritySettings.this,"on",DefaultConstants.isPasscodeActive);
                    savePreferences.savePreferencesData(SecuritySettings.this,data.getStringExtra("data"),DefaultConstants.pinKey);
                }
                else if(isPasscodeActive.equalsIgnoreCase("on"))
                {
                    switch_apppasscode.setChecked(false);
                    savePreferences.savePreferencesData(SecuritySettings.this,"off",DefaultConstants.isPasscodeActive);
                    savePreferences.savePreferencesData(SecuritySettings.this,"",DefaultConstants.pinKey);

                }
            }
        }
    }
}
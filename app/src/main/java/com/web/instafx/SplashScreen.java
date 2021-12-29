package com.web.instafx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatDelegate;

import com.web.instafx.communication.SocketHandlers;
import com.web.instafx.utilpackage.UtilClass;

public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
      {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
     //   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
=======
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
>>>>>>> e305b0496bc7b0a6182abfd063da8589791fe9c0
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        initiateObj();





        new SocketHandlers().createConnection();
        String loginData=savePreferences.reterivePreference(this, DefaultConstants.login_detail).toString();

        //            new Handler().postDelayed(new Runnable() {
        //                @Override
        //                public void run()
        //                {
        //
        //                    if(savePreferences.reterivePreference(SplashScreen.this, UtilClass.isLogin).toString().equalsIgnoreCase("true"))
        //                    {
        //
        //                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        //                        startActivity(intent);
        //                        finish();
        //
        //                    }
        //                    else
        //                    {
        //                        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
        //                        startActivity(intent);
        //                        finish();
        //                    }
        //                }
        //            },2000);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(savePreferences.reterivePreference(SplashScreen.this, UtilClass.isLogin).toString().equalsIgnoreCase("true"))
                {

                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);

    }




}
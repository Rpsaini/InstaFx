package com.web.instafx.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.preferences.SavePreferences;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.GetKeysActivity;
import com.web.instafx.MainActivity;
import com.web.instafx.R;
import com.web.instafx.adapters.KeyBoardAdapter;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;

public class PasscodeSetting extends BaseActivity {
    private String oldPin = "", newPin = "";
    private TextView createpin;
    private int attemptCount = 0;

    private RelativeLayout rr_setPin;
    private String MemberId = "";
    private String pinType = "";
    TextView forgotpasscode;
    private FingerprintManager.AuthenticationCallback authenticationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode_setting);

        getSupportActionBar().hide();
        createpin = findViewById(R.id.createpin);
        rr_setPin = findViewById(R.id.rr_setPin);
        forgotpasscode = findViewById(R.id.forgotpasscode);


        pinType = getIntent().getStringExtra(DefaultConstants.callfrom).toLowerCase();
        initiateObj();
        init();



    }


    int itemCount = 0;

    private void init() {
        itemCount = 0;
        final ArrayList<TextView> keysTextAr = new ArrayList<>();
        keysTextAr.add((TextView) findViewById(R.id.txt_pinone));
        keysTextAr.add((TextView) findViewById(R.id.txt_pintwo));
        keysTextAr.add((TextView) findViewById(R.id.txt_pinthree));
        keysTextAr.add((TextView) findViewById(R.id.txt_pinfour));
        ImageView back =findViewById(R.id.img_back);

        for (int x = 0; x < keysTextAr.size(); x++) {
            keysTextAr.get(x).setText("");
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(pinType.equalsIgnoreCase(DefaultConstants.pinVerify))
        {
            back.setVisibility(View.GONE);
        }


        GridView gridView = findViewById(R.id.grid_restaurantimages);
        final ArrayList<Integer> keysAr = new ArrayList<>();
        for (int x = 1; x < 10; x++) {
            keysAr.add(x);
        }
        keysAr.add(0);
        keysAr.add(R.drawable.delete);

        gridView.setAdapter(new KeyBoardAdapter(this, keysAr));
        if (pinType.equalsIgnoreCase(DefaultConstants.pinSetup)) {
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    try {
                        if (position == 10) {
                            if (itemCount > 0) {
                                itemCount--;
                                TextView textView = keysTextAr.get(itemCount);
                                textView.setText("");
                            }

                        } else {
                            TextView textView = keysTextAr.get(itemCount);
                            textView.setText(keysAr.get(position) + "");
                            itemCount++;
                        }

                        if (attemptCount == 0) {
                            if (itemCount == 4) {

                                attemptCount = 1;
                                createpin.setText("Re-Enter Passcode");
                                oldPin = keysTextAr.get(0).getText().toString() + keysTextAr.get(1).getText().toString() + keysTextAr.get(2).getText().toString() + keysTextAr.get(3).getText().toString();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        init();
                                    }
                                }, 500);

                            }
                        } else if (attemptCount == 1) {
                            if (itemCount == 4) {
                                attemptCount = 2;

                                newPin = keysTextAr.get(0).getText().toString() + keysTextAr.get(1).getText().toString() + keysTextAr.get(2).getText().toString() + keysTextAr.get(3).getText().toString();
                                if (oldPin.equalsIgnoreCase(newPin)) {
                                    Intent intent = new Intent();
                                    intent.putExtra("data", newPin);
                                    setResult(RESULT_OK, intent);
                                    finish();

                                } else {
                                    alertDialogs.alertDialog(PasscodeSetting.this, getResources().getString(R.string.app_name), "Confirm key does not matched", "Ok", "", new DialogCallBacks() {
                                        @Override
                                        public void getDialogEvent(String buttonPressed) {

                                        }
                                    });

                                    attemptCount = 0;
                                    createpin.setText("Create your Passcode");
                                    oldPin = "";
                                    newPin = "";
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            init();
                                        }
                                    }, 500);

                                }

                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } else if (pinType.equalsIgnoreCase(DefaultConstants.pinreset)) {
            createpin.setText("Enter Passcode");
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    try {

                        if (position == 10) {
                            if (itemCount > 0) {
                                itemCount--;
                                TextView textView = keysTextAr.get(itemCount);
                                textView.setText("");
                            }

                        } else {
                            TextView textView = keysTextAr.get(itemCount);
                            textView.setText(keysAr.get(position) + "");
                            itemCount++;
                        }

                        if (attemptCount == 0) {
                            if (itemCount == 4) {

                                attemptCount = 1;
                                createpin.setText("Enter Passcode");
                                oldPin = keysTextAr.get(0).getText().toString() + keysTextAr.get(1).getText().toString() + keysTextAr.get(2).getText().toString() + keysTextAr.get(3).getText().toString();

                                String savedPassCode = savePreferences.reterivePreference(PasscodeSetting.this, DefaultConstants.pinKey).toString();

                                if (savedPassCode.equalsIgnoreCase(oldPin)) {
                                    Intent intent = new Intent();
                                    intent.putExtra("data", oldPin);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else {
                                    alertDialogs.alertDialog(PasscodeSetting.this, getResources().getString(R.string.app_name), "Entered Passcode is invalid", "Ok", "", new DialogCallBacks() {
                                        @Override
                                        public void getDialogEvent(String buttonPressed) {

                                        }
                                    });
                                    itemCount = 0;
                                    attemptCount = 0;
                                    for (int x = 0; x < keysTextAr.size(); x++) {
                                        keysTextAr.get(x).setText("");
                                    }
                                }

                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        } else if (pinType.equalsIgnoreCase(DefaultConstants.pinVerify)) {
            createpin.setText("Enter " + getResources().getString(R.string.app_name) + " Passcode");
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    try {

                        if (position == 10) {
                            if (itemCount > 0) {
                                itemCount--;
                                TextView textView = keysTextAr.get(itemCount);
                                textView.setText("");
                            }

                        } else {
                            TextView textView = keysTextAr.get(itemCount);
                            textView.setText(keysAr.get(position) + "");
                            itemCount++;
                        }


                        if(itemCount == 4)
                        {
                            oldPin = keysTextAr.get(0).getText().toString() + keysTextAr.get(1).getText().toString() + keysTextAr.get(2).getText().toString() + keysTextAr.get(3).getText().toString();
                            String savedPin=savePreferences.reterivePreference(PasscodeSetting.this,DefaultConstants.pinKey).toString();
                            new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        itemCount = 0;
                                        for (int x = 0; x < keysTextAr.size(); x++) {
                                            keysTextAr.get(x).setText("");
                                        }
                                        if(oldPin.equalsIgnoreCase(savedPin))
                                        {
                                            finish();
                                        }
                                        else
                                        {
                                            forgotpasscode.setVisibility(View.VISIBLE);
                                            forgotpasscode.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    logout();
                                                }
                                            });
                                        }
                                       }
                                }, 300);
                               }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Override
    public void onBackPressed()
    {
        if(!pinType.equalsIgnoreCase(DefaultConstants.pinVerify)) {
            super.onBackPressed();
        }
    }





    //    private void sendToServer()
//    {
//        Map<String, String> m = new LinkedHashMap<>();
//        m.put("MemberId", MemberId);
//        m.put("Pin", oldPin);
//        m.put("OTP", "");
//        m.put("IsForget", "NO");
//        new ServerHandler().sendToServer(this, "SetPin", m, 0,1, new CallBack() {
//            @Override
//            public void getRespone(String dta, ArrayList<Object> respons) {
//
//                try {
//                    JSONObject obj = new JSONObject(dta);
//                    System.out.println("setPin====" + obj);
//                    if (obj.getBoolean("status")) {
//
//                        new SavePreferences().savePreferencesData(PasscodeSetting.this, oldPin + "", DefaultConstants.Pin);
//                        Intent i = new Intent(PasscodeSetting.this, MainActivity.class);
//                        i.putExtra(DefaultConstants.IsShowPin, "no");
//                        startActivity(i);
//                        finish();
//                    } else {
//
//                        alertDialogs.alertDialog(PasscodeSetting.this, getResources().getString(R.string.app_name), obj.getString("msg"), "Ok", "", new DialogCallBacks() {
//                            @Override
//                            public void getDialogEvent(String buttonPressed) {
//
//                            }
//                        });
//
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//
//        });
//
//    }

//    private void veriFyPin()
//    {
//        Map<String, String> m = new LinkedHashMap<>();
//        m.put("MemberId", MemberId);
//        m.put("Pin", oldPin);
//        new ServerHandler().sendToServer(this, "LoginWithPin", m, 0,1, new CallBack() {
//            @Override
//            public void getRespone(String dta, ArrayList<Object> respons) {
//                try
//                {
//                    JSONObject obj=new JSONObject(dta);
//                    System.out.println("LOgin data==="+obj);
//                    if(obj.getBoolean("status"))
//                    {
////                        new SaveImpPrefrences().savePrefrencesData(SetPinActivity.this,obj.getString("IsKycApproved")+"",DefaultConstatnts.IsKycApproved);
////                        new SaveImpPrefrences().savePrefrencesData(SetPinActivity.this,obj.getString("UserName")+"",DefaultConstatnts.UserName);
////                        new SaveImpPrefrences().savePrefrencesData(SetPinActivity.this,oldPin+"",DefaultConstatnts.Pin);
////                        Intent i=new Intent(SetPinActivity.this,MainActivity.class);
////                        i.putExtra(DefaultConstatnts.IsShowPin,"no");
////                        startActivity(i);
////                        finish();
//                    }
//                    else
//                    {
////                        showtoast.showToast(SetPinActivity.this,"Response",obj.getString("Message"),rr_setPin);
//                    }
//
//                }
//                catch(Exception e)
//                {
//                    e.printStackTrace();
//                }
//
//            }
//        });


}
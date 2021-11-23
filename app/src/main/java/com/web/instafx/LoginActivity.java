package com.web.instafx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.instafx.googleauthentication.TwoVerificationActivity;
import com.web.instafx.googleauthentication.Verification;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends BaseActivity {

    private EditText publicKey, sceretKey;
    private TextView txt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        initiateObj();
        getSupportActionBar().hide();
        init();
    }

    private void init() {
        findViewById(R.id.login_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView loginBtn = findViewById(R.id.login_btn);
        publicKey = findViewById(R.id.login_username);
        sceretKey = findViewById(R.id.login_password);
        txt_register = findViewById(R.id.txt_register);
        final ImageView scanqrcode = findViewById(R.id.scanqrcode);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationRule.checkEmptyString(publicKey) == 0) {
                    alertDialogs.alertDialog(LoginActivity.this, getResources().getString(R.string.app_name), "Enter User name.", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (validationRule.checkEmptyString(sceretKey) == 0) {
                    alertDialogs.alertDialog(LoginActivity.this, getResources().getString(R.string.app_name), "Enter Password.", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                try {
                    Map<String, String> m = new LinkedHashMap<>();
//                  m.put("secret", sceretKey.getText().toString());
//                  m.put("publickey", publicKey.getText().toString());

                    m.put("username", publicKey.getText().toString().trim());
                    m.put("password", sceretKey.getText().toString().trim());
                    m.put("Version", getAppVersion());
                    m.put("PlatForm", "android");
                    m.put("Timestamp", System.currentTimeMillis() + "");
                    m.put("DeviceToken", getDeviceToken() + "");

                    Map<String, String> headerMap = new HashMap<>();
                    headerMap.put("X-API-KEY", UtilClass.xApiKey);

                    System.out.println("Login==="+m+"==="+headerMap);

                    System.out.println("user_request::"+m);
                    System.out.println("header_request::"+headerMap);
                    new ServerHandler().sendToServer(LoginActivity.this, getApiUrl() + "login-authenticate-api", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                        @Override
                        public void getRespone(String dta, ArrayList<Object> respons) {
                            try {
                                System.out.println("Login===="+dta);
                                JSONObject obj = new JSONObject(dta);
                                if (obj.getBoolean("status")) {
                                    try
                                       {
                                        System.out.println("Login===="+obj);

                                        savePreferences.savePreferencesData(LoginActivity.this,obj.getString("is_auth_enable"),DefaultConstants.ga_active);
                                        savePreferences.savePreferencesData(LoginActivity.this, obj.getString("token"), DefaultConstants.token);
                                        savePreferences.savePreferencesData(LoginActivity.this, obj.getString("r_token"), DefaultConstants.r_token);

                                           if(obj.getString("is_auth_enable").equalsIgnoreCase("true"))
                                           {
                                               Intent intent = new Intent(LoginActivity.this, TwoVerificationActivity.class);
                                               startActivity(intent);

//                                               Intent intent = new Intent(LoginActivity.this, VerifyOtp.class);
//                                               intent.putExtra("url","verify-login-otp");
//                                               startActivity(intent);
                                           }
                                           else
                                           {
                                               Intent intent = new Intent(LoginActivity.this, VerifyOtp.class);
                                               intent.putExtra("url","verify-login-otp");
                                               startActivity(intent);
                                           }


                                       } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    alertDialogs.alertDialog(LoginActivity.this, getResources().getString(R.string.app_name), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                        @Override
                                        public void getDialogEvent(String buttonPressed) {
                                            unauthorizedAccess(obj);
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


        });


        scanqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectAndGotonext();
            }
        });

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.get_keys).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, GetKeysActivity.class);
                startActivity(intent);
            }
        });


    }

    public void redirectAndGotonext() {
        if (checkAndRequestPermissions() == 0) {
            Intent intent = new Intent(LoginActivity.this, ScanQrCode.class);
            intent.putExtra("title", "Scan Qr code to login");
            startActivityForResult(intent, 1001);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (data != null) {
                String qrcode = data.getStringExtra("code");
                if (qrcode.contains("-")) {
                    String ar[] = qrcode.split("-");
                    String publickey = ar[0];
                    String scretkey = ar[1];

                    publicKey.setText(publickey);
                    sceretKey.setText(scretkey);
                }

            }
        }
    }

    private int checkAndRequestPermissions() {

        int permissionCAMERA = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return 1;
        }

        return 0;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        redirectAndGotonext();
    }
}
//package com.web.whatashot.kyc;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.RadioButton;
//
//import com.app.dialogsnpickers.DialogCallBacks;
//import com.app.vollycommunicationlib.CallBack;
//import com.app.vollycommunicationlib.ServerHandler;
//import com.web.whatashot.BaseActivity;
//import com.web.whatashot.DefaultConstants;
//import com.web.whatashot.LoginActivity;
//import com.web.whatashot.MainActivity;
//import com.web.whatashot.R;
//import com.web.whatashot.pairdetailfragments.PairDetailView;
//import com.web.whatashot.utilpackage.UtilClass;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class PersonalDetails extends BaseActivity {
//
//    private EditText txt_name, txt_lastname, txt_nationality, txt_residence_address, txt_city, txt_state, txt_postalcode, txt_account_holder_name, txt_account_number, txt_branch_number, txt_iban_number;
//    private RadioButton rd_married, rd_unmarried;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_personal_details);
//        getSupportActionBar().hide();
//        initiateObj();
//        init();
//    }
//
//    private void init() {
//
//        txt_name = findViewById(R.id.txt_name);
//        txt_lastname = findViewById(R.id.txt_lastname);
//        txt_nationality = findViewById(R.id.txt_nationality);
//        txt_residence_address = findViewById(R.id.txt_residence_address);
//        txt_city = findViewById(R.id.txt_city);
//        txt_state = findViewById(R.id.txt_state);
//        txt_postalcode = findViewById(R.id.txt_postalcode);
//        txt_account_holder_name = findViewById(R.id.txt_account_holder_name);
//        txt_account_number = findViewById(R.id.txt_account_number);
//        txt_branch_number = findViewById(R.id.txt_branch_number);
//        txt_iban_number = findViewById(R.id.txt_iban_number);
//        rd_married = findViewById(R.id.rd_married);
//        rd_unmarried = findViewById(R.id.rd_unmarried);
//        rd_married.setChecked(true);
//        rd_married.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    rd_unmarried.setChecked(false);
//
//                }
//            }
//        });
//        rd_unmarried.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                rd_married.setChecked(false);
//            }
//        });
//
//        rd_married.setChecked(true);
//        rd_married.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    rd_unmarried.setChecked(false);
//
//                }
//            }
//        });
//        rd_unmarried.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                rd_married.setChecked(false);
//            }
//        });
//
//
//        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (validationRule.checkEmptyString(txt_name) == 0) {
//                    alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.app_name), "Enter first name", "ok", "", new DialogCallBacks() {
//                        @Override
//                        public void getDialogEvent(String buttonPressed) {
//                        }
//                    });
//                    return;
//                }
//
//                if (validationRule.checkEmptyString(txt_lastname) == 0) {
//                    alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.app_name), "Enter Last name", "ok", "", new DialogCallBacks() {
//                        @Override
//                        public void getDialogEvent(String buttonPressed) {
//                        }
//                    });
//                    return;
//                }
//
//                if (validationRule.checkEmptyString(txt_nationality) == 0) {
//                    alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.app_name), "Enter nationality ", "ok", "", new DialogCallBacks() {
//                        @Override
//                        public void getDialogEvent(String buttonPressed) {
//                        }
//                    });
//                    return;
//                }
//
//                if (validationRule.checkEmptyString(txt_residence_address) == 0) {
//                    alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.app_name), "Enter Residence address ", "ok", "", new DialogCallBacks() {
//                        @Override
//                        public void getDialogEvent(String buttonPressed) {
//                        }
//                    });
//                    return;
//                }
//
//                if (validationRule.checkEmptyString(txt_city) == 0) {
//                    alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.app_name), "Enter City name ", "ok", "", new DialogCallBacks() {
//                        @Override
//                        public void getDialogEvent(String buttonPressed) {
//                        }
//                    });
//                    return;
//                }
//
//                if (validationRule.checkEmptyString(txt_state) == 0) {
//                    alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.app_name), "Enter state name ", "ok", "", new DialogCallBacks() {
//                        @Override
//                        public void getDialogEvent(String buttonPressed) {
//                        }
//                    });
//                    return;
//                }
//
//                if (validationRule.checkEmptyString(txt_postalcode) == 0) {
//                    alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.app_name), "Enter postal address", "ok", "", new DialogCallBacks() {
//                        @Override
//                        public void getDialogEvent(String buttonPressed) {
//                        }
//                    });
//                    return;
//                }
//
//                if (validationRule.checkEmptyString(txt_account_holder_name) == 0) {
//                    alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.app_name), "Enter Account holder name  ", "ok", "", new DialogCallBacks() {
//                        @Override
//                        public void getDialogEvent(String buttonPressed) {
//                        }
//                    });
//                    return;
//                }
//
//                if (validationRule.checkEmptyString(txt_account_number) == 0) {
//                    alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.app_name), "Enter Account number", "ok", "", new DialogCallBacks() {
//                        @Override
//                        public void getDialogEvent(String buttonPressed) {
//                        }
//                    });
//                    return;
//                }
//
//                if (validationRule.checkEmptyString(txt_branch_number) == 0) {
//                    alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.app_name), "Enter Branch number", "ok", "", new DialogCallBacks() {
//                        @Override
//                        public void getDialogEvent(String buttonPressed) {
//                        }
//                    });
//                    return;
//                }
//
//                if (validationRule.checkEmptyString(txt_iban_number) == 0) {
//                    alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.app_name), "Enter IBAN number", "ok", "", new DialogCallBacks() {
//                        @Override
//                        public void getDialogEvent(String buttonPressed) {
//                        }
//                    });
//                    return;
//                }
//
//
//                Map<String, String> map = new HashMap<>();
//                map.put("", txt_name.getText().toString());
//                map.put("", txt_lastname.getText().toString());
//                map.put("", txt_nationality.getText().toString());
//                map.put("", txt_residence_address.getText().toString());
//                map.put("", txt_city.getText().toString());
//                map.put("", txt_state.getText().toString());
//                map.put("", txt_postalcode.getText().toString());
//                map.put("", txt_account_holder_name.getText().toString());
//                map.put("", txt_account_number.getText().toString());
//                map.put("", txt_branch_number.getText().toString());
//                map.put("", txt_iban_number.getText().toString());
//                map.put("", rd_married.getText().toString());
//                if (rd_married.isChecked()) {
//                    map.put("", "1");
//                } else {
//                    map.put("", "0");
//                }
//
//
//                Map<String, String> headerMap = new HashMap<>();
//                headerMap.put("X-API-KEY", UtilClass.xApiKey);
//                headerMap.put("Rtoken", getNewRToken() + "");
//
//
//                new ServerHandler().sendToServer(PersonalDetails.this, getApiUrl() + "", map, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
//                    @Override
//                    public void getRespone(String dta, ArrayList<Object> respons) {
//                        try {
//
//                            JSONObject obj = new JSONObject(dta);
//                            if (obj.getBoolean("status")) {
//
//
//                                if (obj.has("token")) {
//                                    savePreferences.savePreferencesData(PersonalDetails.this, obj.getString("token"), DefaultConstants.token);
//                                    savePreferences.savePreferencesData(PersonalDetails.this, obj.getString("r_token"), DefaultConstants.r_token);
//
//                                }
//
//                                alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
//                                    @Override
//                                    public void getDialogEvent(String buttonPressed) {
//
//                                    }
//                                });
//
//                            } else {
//                                alertDialogs.alertDialog(PersonalDetails.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
//                                    @Override
//                                    public void getDialogEvent(String buttonPressed) {
//                                    }
//                                });
//
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//
//            }
//        });
//
//
//        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//
//    }
//}
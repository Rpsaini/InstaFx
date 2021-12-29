package com.web.instafx.kyc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.dialogsnpickers.SimpleDialog;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.kyc.adapter.SelectCategorySubCategoryAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Calendar;

public class BasicDetailScreen extends BaseActivity {
    public static BasicDetailScreen mBasicDetailScreen;
    private ImageView backIc = null;
    private ImageView img_one;
    public static TextView dobET,selectCountryTV,select_typeTV,select_stateTV,select_DocTypeTV;
    RecyclerView select_category_recycle;


    public static String countryID;
    public static EditText firstNameET,middleNameET,lastNameET,addressET,cityET,pinCodeET;

    private int mYear,mMonth,mDay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_details_screen);
        getSupportActionBar().hide();
        mBasicDetailScreen=this;
        initiateObj();
        initView();
        setOnClickListener();
        setData();
    }
    private void setData()
    {
        try {

            firstNameET=findViewById(R.id.firstNameET);
            middleNameET=findViewById(R.id.middleNameET);
            lastNameET=findViewById(R.id.lastNameET);

            JSONObject data=new JSONObject(savePreferences.reterivePreference(this, DefaultConstants.login_detail).toString());


            String dataSTR=data.getString("name");
            if(dataSTR.contains(" "))
            {
                String ar[]=dataSTR.split(" ");
                firstNameET.setText(ar[0].trim());
                lastNameET.setText(ar[1].trim());

            }
            else
            {
                firstNameET.setText(dataSTR);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    private void initView() {
        backIc = findViewById(R.id.backIC);
        selectCountryTV=findViewById(R.id.select_countryTV);
        select_typeTV=findViewById(R.id.select_typeTV);

        select_stateTV=findViewById(R.id.select_stateTV);
        select_DocTypeTV=findViewById(R.id.select_DocTypeTV);

        firstNameET=findViewById(R.id.firstNameET);
        middleNameET=findViewById(R.id.middleNameET);
        lastNameET=findViewById(R.id.lastNameET);

        dobET=findViewById(R.id.dobET);
        addressET=findViewById(R.id.addressET);
        cityET=findViewById(R.id.cityET);

        pinCodeET=findViewById(R.id.pinCodeET);



        Intent intent =getIntent();
        if(intent.hasExtra("country_name")){
            selectCountryTV.setText(intent.getExtras().getString("country_name"));
        }
        if(intent.hasExtra("kyc_type")){
            select_typeTV.setText(intent.getExtras().getString("kyc_type"));
        }
        if(intent.hasExtra("_id")){
            countryID=intent.getExtras().getString("_id");
        }
    }

    private void setOnClickListener() {
        backIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        selectCountryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(BasicDetailScreen.this,SelectCountryScreen.class);
                startActivityForResult(intent,101);
            }
        });
        select_stateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(BasicDetailScreen.this,SelectStateScreen.class);
                intent.putExtra("_id",countryID);
                startActivityForResult(intent,102);
            }
        });

        select_typeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTypeDialog();
            }
        });

        findViewById(R.id.submitVerifyBT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNameET.getText().toString().length() == 0) {
                    alertDialogs.alertDialog(BasicDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.firstname_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (dobET.getText().toString().length() == 0) {
                    alertDialogs.alertDialog(BasicDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.dob_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (addressET.getText().toString().length() == 0) {
                    alertDialogs.alertDialog(BasicDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.address_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (select_stateTV.getText().toString().length() == 0) {
                    alertDialogs.alertDialog(BasicDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.state_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (cityET.getText().toString().length() == 0) {
                    alertDialogs.alertDialog(BasicDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.city_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (pinCodeET.getText().toString().length() == 0) {
                    alertDialogs.alertDialog(BasicDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.pincode_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }

               Intent intent=new Intent(BasicDetailScreen.this,IdentityDetailScreen.class);
               startActivity(intent);

               // saveKyc();
            }
        });
        dobET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(BasicDetailScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            String fmonth, fDate;
                            int month;

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                try {
                                    if (monthOfYear < 10 && dayOfMonth < 10) {

                                        fmonth = "0" + monthOfYear;
                                        month = Integer.parseInt(fmonth) + 1;
                                        fDate = "0" + dayOfMonth;
                                        String paddedMonth = String.format("%02d", month);
                                        dobET.setText(fDate + "-" + paddedMonth + "-" + year);

                                    } else {

                                        fmonth = "0" + monthOfYear;
                                        month = Integer.parseInt(fmonth) + 1;
                                        String paddedMonth = String.format("%02d", month);
                                        dobET.setText(dayOfMonth + "-" + paddedMonth + "-" + year);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

    }

    private void selectTypeDialog() {
        try {

            hideKeyboard(this);
            SimpleDialog simpleDialog = new SimpleDialog();
            final Dialog selectCategoryDialog = simpleDialog.simpleDailog(BasicDetailScreen.this, R.layout.select_category_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
            select_category_recycle = selectCategoryDialog.findViewById(R.id.select_category_recycler);
            ImageView img_hideview = selectCategoryDialog.findViewById(R.id.img_hideview);
            final RelativeLayout ll_relativelayout = selectCategoryDialog.findViewById(R.id.ll_relativelayout);
            final TextView select_title = selectCategoryDialog.findViewById(R.id.select_title);
            final TextView select_sub_title = selectCategoryDialog.findViewById(R.id.select_sub_title);
            final TextView tv_done = selectCategoryDialog.findViewById(R.id.tv_done);
            animateUp(ll_relativelayout);
            select_title.setText(getResources().getString(R.string.type));
            select_sub_title.setText("");
            JSONArray buisnessTypeAr = new JSONArray();
            JSONObject type1 = new JSONObject();
            type1.put("name", "personal");

            JSONObject type2 = new JSONObject();
            type2.put("name", "company");

            buisnessTypeAr.put(type1);
            buisnessTypeAr.put(type2);


            initHomeCategory(buisnessTypeAr);



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
    public void setKycType(String s){
        if(!s.isEmpty()) {
            select_typeTV.setText(s);
        }
        else {
            select_typeTV.setText(getString(R.string.personal));

        }

    }
    private void initHomeCategory(JSONArray dataAr) {
        select_category_recycle.setNestedScrollingEnabled(false);
        select_category_recycle.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        select_category_recycle.setHasFixedSize(true);
        select_category_recycle.setItemAnimator(new DefaultItemAnimator());
        SelectCategorySubCategoryAdapter horizontalCategoriesAdapter = new SelectCategorySubCategoryAdapter(dataAr, this);
        select_category_recycle.setAdapter(horizontalCategoriesAdapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case 0://camera

                    break;
                case 1://gallery
                    if (resultCode == RESULT_OK) {

                    }
                    break;
                case 101://selectCountry
                    if (imageReturnedIntent != null) {
                        String countryName=  imageReturnedIntent.getExtras().getString("_name");
                        countryID=imageReturnedIntent.getExtras().getString("_id");
                        selectCountryTV.setText(countryName);
                    }

                    break;
                case 102://selectState
                    if (imageReturnedIntent != null) {
                        String stateName=  imageReturnedIntent.getExtras().getString("_name");
                        select_stateTV.setText(stateName);
                    }

                    break;
            }

            // new ConvertImage().execute();
        }
    }

}

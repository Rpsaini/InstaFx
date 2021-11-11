package com.web.instafx.kyc;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.dialogsnpickers.SimpleDialog;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.google.gson.Gson;
import com.web.instafx.BaseActivity;
import com.web.instafx.BuildConfig;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.fileupload.AddEventInterface;
import com.web.instafx.fileupload.ApiProduction;
import com.web.instafx.fileupload.RxAPICallHelper;
import com.web.instafx.fileupload.RxAPICallback;
import com.web.instafx.fileupload.ServerResponse;
import com.web.instafx.kyc.adapter.SelectCategorySubCategoryAdapter;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class BankDetailScreen extends BaseActivity {
    public static BankDetailScreen mBankDetailScreen;
    private ImageView backIc = null;
    RecyclerView select_category_recycle;


    String imageType="pan";
    private ImageView commonImage;

    private String passbook="";
    private String docType="adhaar";



    private ImageView new_img_passbook_front;
    private RelativeLayout rr_browse_passbokimg;

    private EditText ed_bank_account_holder_name,ed_bank_name,ed_bank_account_number,ed_bank_confirm_account,ed_bank_ifsc_code,ed_bank_branch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_details_screen);
        mBankDetailScreen=this;
        initiateObj();
        initView();
        setOnClickListener();
    }

    private void initView() {
        backIc = findViewById(R.id.backIC);













        ed_bank_account_holder_name=findViewById(R.id.ed_bank_account_holder_name);
        ed_bank_name=findViewById(R.id.ed_bank_name);
        ed_bank_account_number=findViewById(R.id.ed_bank_account_number);
        ed_bank_ifsc_code=findViewById(R.id.ed_bank_ifsc_code);
        ed_bank_confirm_account=findViewById(R.id.ed_bank_confirm_account);
        ed_bank_branch=findViewById(R.id.ed_branch_name);

        new_img_passbook_front=findViewById(R.id.new_img_passbook_front);
        rr_browse_passbokimg=findViewById(R.id.rr_browse_passbokimg);


    }

    private void setOnClickListener() {
        backIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.submitVerifyBT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed_bank_account_holder_name.getText().toString().length() == 0) {
                    alertDialogs.alertDialog(BankDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.account_hoder_name_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (ed_bank_name.getText().toString().length() == 0) {
                    alertDialogs.alertDialog(BankDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.bank_name_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (ed_bank_branch.getText().toString().length() == 0) {
                    alertDialogs.alertDialog(BankDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.branch_name_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (ed_bank_account_number.getText().toString().length() == 0) {
                    alertDialogs.alertDialog(BankDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.account_number_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (!ed_bank_account_number.getText().toString().equalsIgnoreCase(ed_bank_confirm_account.getText().toString())) {
                    alertDialogs.alertDialog(BankDetailScreen.this, getResources().getString(R.string.app_name),  getResources().getString(R.string.account_number_match_warning), "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {
                        }
                    });
                    return;
                }
                if (ed_bank_ifsc_code.getText().toString().length() == 0) {
                    alertDialogs.alertDialog(BankDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.ifsc_code_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (passbook.length() == 0) {
                    alertDialogs.alertDialog(BankDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.passbook_image_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }

                saveKyc();
            }
        });




        rr_browse_passbokimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType="passbook";
                commonImage=new_img_passbook_front;
                browseImage();
            }
        });


    }



    //browse image
    int browseMode = 0;

    private void browseImage() {
        alertDialogs.alertDialog(this, getResources().getString(R.string.app_name), "Choose Image from", "Camera", "Gallery", new DialogCallBacks() {
            @Override
            public void getDialogEvent(String buttonPressed) {
                if (buttonPressed.equalsIgnoreCase("Camera")) {

                    browseMode = 0;
                    selectImage(browseMode);
                } else {
                    browseMode = 1;
                    selectImage(browseMode);
                }

            }
        });
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void selectImage(int actionCode) {
        if (checkAndRequestPermissions() == 0) {
            if (actionCode == 0) {
                dispatchTakePictureIntent();
            } else if (actionCode == 1) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, actionCode);
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case 0://camera
                    try {
                        Bitmap bmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                        Uri uri = getImageUri(this, bmap);
                        selectedPath = getRealPathFromURI(uri);
                        commonImage.setImageBitmap(bmap);

                        senAndUploadFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 1://gallery
                    if (resultCode == RESULT_OK) {
                        if (imageReturnedIntent != null) {
                            try {
                                Uri selectedImage = imageReturnedIntent.getData();
                                selectedPath = getRealPathFromURI(selectedImage);
                                InputStream image_stream = getContentResolver().openInputStream(selectedImage);
                                Bitmap bmap = BitmapFactory.decodeStream(image_stream);
                                commonImage.setImageBitmap(bmap);

                                senAndUploadFile();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 101://selectCountry
                case 102://selectState
                    if (imageReturnedIntent != null) {
                        String countryName=  imageReturnedIntent.getExtras().getString("_name");

                    }

                    break;

            }

            // new ConvertImage().execute();
        }
    }

    static final int REQUEST_TAKE_PHOTO = 0;
    Uri photoURI;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                System.out.println("inside exception===" + ex.getMessage());

            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                        BuildConfig.APPLICATION_ID + ".fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                    takePictureIntent.setClipData(ClipData.newRawUri("", photoURI));
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    String mCurrentPhotoPath;


    //    String mCurrentPhotoPath;
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private int checkAndRequestPermissions() {

        int permissionCAMERA = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int readExternal = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternal = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (readExternal != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writeExternal != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return 1;
        }

        return 0;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //end of browse image


    private String selectedPath = "";

    private void senAndUploadFile() {

        Observable<Response<ServerResponse>> responseObservable = null;
        AddEventInterface contestService = ApiProduction.getInstance(this).provideService(AddEventInterface.class);

        File file = new File(selectedPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(imageType, file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        RequestBody DeviceToken = RequestBody.create(MediaType.parse("text/plain"), getDeviceToken());
        RequestBody Version = RequestBody.create(MediaType.parse("text/plain"), getAppVersion());
        RequestBody PlatForm = RequestBody.create(MediaType.parse("text/plain"), "Android");
        RequestBody Timestamp = RequestBody.create(MediaType.parse("text/plain"), System.currentTimeMillis()+"");

        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), savePreferences.reterivePreference(BankDetailScreen.this, "token") + "");
        responseObservable = contestService.uploadKyc(token, DeviceToken, Version, PlatForm,Timestamp, getXapiKey(), body, filename);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RxAPICallHelper.call(responseObservable, new RxAPICallback<Response<ServerResponse>>() {
            @Override
            public void onSuccess(Response<ServerResponse> t) {
                try {
                    progressDialog.dismiss();
                    selectedPath = "";
                    if (t.body().isStatus()) {
                        ServerResponse.Kyc_info kyc_info = t.body().getKyc_info();
                        Gson gson = new Gson();
                        String json = gson.toJson(kyc_info);
                        if (imageType.equals("passbook")){
                            passbook="selfie";
                        }



                    } else {

                        alertDialogs.alertDialog(BankDetailScreen.this, getResources().getString(R.string.app_name), t.body().getMsg(), "ok", "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {
                            }
                        });
                    }

//                      {"status":true,"msg":"Your KYC details has been updated successfully","kyc_info":{"attempt":true,"status":"0","address_f_status":"1","address_b_status":"0","pan_status":"0"},"code":200}

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailed(Throwable throwable) {
                progressDialog.dismiss();
                System.out.println("error===" + throwable.getMessage());
                alertDialogs.alertDialog(BankDetailScreen.this, "ok", "Message document not updated.", "ok", "", new DialogCallBacks() {
                    @Override
                    public void getDialogEvent(String buttonPressed) {
                        if (buttonPressed.equalsIgnoreCase("ok")) {

                        }
                    }
                });
            }
        });

    }

    private void selectTypeDialog() {
        try {

            hideKeyboard(this);
            SimpleDialog simpleDialog = new SimpleDialog();
            final Dialog selectCategoryDialog = simpleDialog.simpleDailog(BankDetailScreen.this, R.layout.select_category_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
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

    private void initHomeCategory(JSONArray dataAr) {
        select_category_recycle.setNestedScrollingEnabled(false);
        select_category_recycle.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        select_category_recycle.setHasFixedSize(true);
        select_category_recycle.setItemAnimator(new DefaultItemAnimator());
        SelectCategorySubCategoryAdapter horizontalCategoriesAdapter = new SelectCategorySubCategoryAdapter(dataAr, this);
        select_category_recycle.setAdapter(horizontalCategoriesAdapter);
    }
    private void initHomeCategory(JSONArray dataAr,String type) {
        select_category_recycle.setNestedScrollingEnabled(false);
        select_category_recycle.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        select_category_recycle.setHasFixedSize(true);
        select_category_recycle.setItemAnimator(new DefaultItemAnimator());
        SelectCategorySubCategoryAdapter horizontalCategoriesAdapter = new SelectCategorySubCategoryAdapter(dataAr, this,type);
        select_category_recycle.setAdapter(horizontalCategoriesAdapter);
    }
    private void selectDocTypeDialog() {
        try {

            hideKeyboard(this);
            SimpleDialog simpleDialog = new SimpleDialog();
            final Dialog selectCategoryDialog = simpleDialog.simpleDailog(BankDetailScreen.this, R.layout.select_category_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
            select_category_recycle = selectCategoryDialog.findViewById(R.id.select_category_recycler);
            ImageView img_hideview = selectCategoryDialog.findViewById(R.id.img_hideview);
            final RelativeLayout ll_relativelayout = selectCategoryDialog.findViewById(R.id.ll_relativelayout);
            final TextView select_title = selectCategoryDialog.findViewById(R.id.select_title);
            final TextView select_sub_title = selectCategoryDialog.findViewById(R.id.select_sub_title);
            final TextView tv_done = selectCategoryDialog.findViewById(R.id.tv_done);
            animateUp(ll_relativelayout);
            select_title.setText(getResources().getString(R.string.document_ty));
            select_sub_title.setText("");
            JSONArray buisnessTypeAr = new JSONArray();

            JSONObject adhaar = new JSONObject();
            adhaar.put("name", "National ID");

         /*   JSONObject passport = new JSONObject();
            passport.put("name", "passport");

            JSONObject driving_license = new JSONObject();
            driving_license.put("name", "driving-license");

            JSONObject voter_id = new JSONObject();
            voter_id.put("name", "voter-id");
*/
            buisnessTypeAr.put(adhaar);
            // buisnessTypeAr.put(passport);
            // buisnessTypeAr.put(driving_license);
            //buisnessTypeAr.put(voter_id);



            initHomeCategory(buisnessTypeAr,"doc");



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
    private void saveKyc()
    {
        Map<String, String> m=new HashMap<>();
        m.put("kyc_type", BasicDetailScreen.select_typeTV.getText().toString());
        m.put("country_id", BasicDetailScreen.countryID);
        m.put("first_name", BasicDetailScreen.firstNameET.getText().toString());
        m.put("last_name", BasicDetailScreen.lastNameET.getText().toString());
        m.put("dob", BasicDetailScreen.dobET.getText().toString());
        m.put("address", BasicDetailScreen.addressET.getText().toString());
        m.put("state", BasicDetailScreen.select_stateTV.getText().toString());
        m.put("city", BasicDetailScreen.cityET.getText().toString());
        m.put("postal_code", BasicDetailScreen.pinCodeET.getText().toString());
        //m.put("pan_number", "pan");
        m.put("document_type", docType);
        m.put("id_number", IdentityDetailScreen.nationalIDNumberET.getText().toString());

        m.put("accountholdername", ed_bank_account_holder_name.getText().toString());
        m.put("bank_account_number", ed_bank_account_number.getText().toString());
        m.put("bank_name", ed_bank_account_number.getText().toString());
        m.put("branch", ed_bank_branch.getText().toString());
        m.put("iban", ed_bank_ifsc_code.getText().toString());


        m.put("token",savePreferences.reterivePreference(this, DefaultConstants.token)+"");
        m.put("DeviceToken",getDeviceToken()+"");
        m.put("Version",getAppVersion()+"");
        m.put("PlatForm", "Android");
        m.put("Timestamp", System.currentTimeMillis()+"");
        Map<String,String> headerMap=new HashMap<>();
        headerMap.put("X-API-KEY", UtilClass.xApiKey);
        headerMap.put("Rtoken", getNewRToken()+"");

        System.out.println("request::"+m);
        System.out.println("headerMap::"+headerMap);

        new ServerHandler().sendToServer(this, getApiUrl()+"submit-kyc-request", m, 0,headerMap, 20000, R.layout.progressbar, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {

                    JSONObject obj = new JSONObject(dta);
                    Log.d("Fait",obj+"");
                    /*  {"status":true,"msg":"Your KYC details have been submitted successfully","code":200}
                     */
                    if (obj.getBoolean("status")) {
                        try
                        {
                            if(obj.has("token"))
                            {
                                savePreferences.savePreferencesData(BankDetailScreen.this,obj.getString("token"),DefaultConstants.token);
                                savePreferences.savePreferencesData(BankDetailScreen.this,obj.getString("r_token"),DefaultConstants.r_token);
                                savePreferences.savePreferencesData(BankDetailScreen.this,"1",DefaultConstants.kyc_status);

                            }

                            alertDialogs.alertDialog(BankDetailScreen.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed) {
                                    unauthorizedAccess(obj);
                                    BasicDetailScreen.mBasicDetailScreen.finish();
                                    IdentityDetailScreen.mIdentityDetailScreen.finish();
                                    BankDetailScreen.mBankDetailScreen.finish();
                                    VerifyKycAccountDetailsScreen.mVerifyKycAccountDetailsScreen.finish();
                                    VerifyKycAccountDetailsScreen.mVerifyKycAccountDetailsScreen.finish();
                                }
                            });



                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    } else {
                        alertDialogs.alertDialog(BankDetailScreen.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
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

    }
}


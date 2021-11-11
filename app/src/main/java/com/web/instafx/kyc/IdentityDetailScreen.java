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
import com.google.gson.Gson;
import com.web.instafx.BaseActivity;
import com.web.instafx.BuildConfig;
import com.web.instafx.R;
import com.web.instafx.fileupload.AddEventInterface;
import com.web.instafx.fileupload.ApiProduction;
import com.web.instafx.fileupload.RxAPICallHelper;
import com.web.instafx.fileupload.RxAPICallback;
import com.web.instafx.fileupload.ServerResponse;
import com.web.instafx.kyc.adapter.SelectCategorySubCategoryAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class IdentityDetailScreen extends BaseActivity {
    public static IdentityDetailScreen mIdentityDetailScreen;
    private ImageView backIc = null;

    public static TextView select_DocTypeTV,adharNoTV,reDocTV,uploadFrontDocTitleTV,uploadBackDocTitleTV;
    RecyclerView select_category_recycle;
    private ImageView docImage,docBackImage;
    private RelativeLayout nationalFronUploadRL,nationalBackUploadRL;
    String imageType="pan";
    private ImageView commonImage;

    public static EditText nationalIDNumberET,reNationalIDNumberET;
    private String nationIDFrontImage="",nationIDBackImage="",passbook="",addressFrontImage="",addressBackImage="";
    private String docType="adhaar";



    private ImageView new_docImageadhar_front,new_docadharBackImage,new_img_passbook_front;
    private RelativeLayout browseAddressFrontUploadRL,browseAddressBackimageRL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identiy_details_screen);
        mIdentityDetailScreen=this;
        initiateObj();
        initView();
        setOnClickListener();
        actions();

    }

    private void initView() {
        backIc = findViewById(R.id.backIC);
        select_DocTypeTV=findViewById(R.id.select_DocTypeTV);


        nationalIDNumberET=findViewById(R.id.adharNoET);
        reNationalIDNumberET=findViewById(R.id.reDocET);


        docImage=findViewById(R.id.docImage);
        docBackImage=findViewById(R.id.docBackImage);


        nationalFronUploadRL=findViewById(R.id.docUploadRL);
        nationalBackUploadRL=findViewById(R.id.docBackUploadRL);


        adharNoTV=findViewById(R.id.adharNoTV);
        reDocTV=findViewById(R.id.reDocTV);
        uploadFrontDocTitleTV=findViewById(R.id.uploadFrontDocTitleTV);
        uploadBackDocTitleTV=findViewById(R.id.uploadBackDocTitleTV);



        new_docImageadhar_front=findViewById(R.id.new_docImageadhar_front);
        new_docadharBackImage=findViewById(R.id.new_docadharBackImage);

        browseAddressFrontUploadRL=findViewById(R.id.browseAddressFrontUploadRL);
        browseAddressBackimageRL=findViewById(R.id.browseAddressBackimage);




        new_img_passbook_front=findViewById(R.id.new_img_passbook_front);


        adharNoTV.setText(getString(R.string.national_id_));
        reDocTV.setText(getString(R.string.re_enter_national_id_));
        nationalIDNumberET.setHint(getString(R.string.enter_national_));
        reNationalIDNumberET.setHint(getString(R.string.enter_national_));
        uploadFrontDocTitleTV.setText(getString(R.string.upload_national_id_front_));
        uploadBackDocTitleTV.setText(getString(R.string.upload_national_id_back_));
        docImage.setImageDrawable(getDrawable(R.drawable.sample_aadhaar_card_front_1));
        docBackImage.setImageDrawable(getDrawable(R.drawable.sample_aadhaar_card_back_1));



    }

    private void setOnClickListener() {
        backIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        select_DocTypeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDocTypeDialog();
            }
        });
        findViewById(R.id.submitVerifyBT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nationalIDNumberET.getText().toString().length() == 0) {
                    String msg="";
                    if(docType.equals("adhaar")){
                        msg=getString(R.string.enter_national_);
                    }
                    alertDialogs.alertDialog(IdentityDetailScreen.this, getResources().getString(R.string.Required),msg, getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (!nationalIDNumberET.getText().toString().equals(reNationalIDNumberET.getText().toString())){

                    String msg="";
                    if(docType.equals("adhaar")){
                        msg=getString(R.string.natinal_id_must_match_);
                    }
                    alertDialogs.alertDialog(IdentityDetailScreen.this, getResources().getString(R.string.Required),msg, getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });

                    return;
                }

                if (nationIDFrontImage.length() == 0) {
                    alertDialogs.alertDialog(IdentityDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.national_id_front_image_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }

                if (nationIDBackImage.length() == 0) {
                    alertDialogs.alertDialog(IdentityDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.national_id_back_image_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (addressFrontImage.length() == 0) {
                    alertDialogs.alertDialog(IdentityDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.address_front_image_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }

                if (addressBackImage.length() == 0) {
                    alertDialogs.alertDialog(IdentityDetailScreen.this, getResources().getString(R.string.Required), getResources().getString(R.string.address_back_image_warning), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                Intent intent =new Intent(IdentityDetailScreen.this,BankDetailScreen.class);
                startActivity(intent);
                //saveKyc();
            }
        });


        browseAddressFrontUploadRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType="address_front";
                commonImage=new_docImageadhar_front;
                browseImage();

            }
        });

        browseAddressBackimageRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType="address_back";
                commonImage=new_docadharBackImage;
                browseImage();
            }
        });





    }

    private void actions() {

        nationalFronUploadRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType="national_front";
                commonImage=docImage;
                browseImage();
            }
        });
        nationalBackUploadRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType="national_back";
                commonImage=docBackImage;
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
                    if (imageReturnedIntent != null) {
                        String countryName=  imageReturnedIntent.getExtras().getString("_name");
                    }

                    break;
                case 102://selectState
                    if (imageReturnedIntent != null) {

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

        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), savePreferences.reterivePreference(IdentityDetailScreen.this, "token") + "");
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
                        if (imageType.equals("national_front")){
                            nationIDFrontImage="national_front";
                        }
                        else if (imageType.equals("national_back")){
                            nationIDBackImage="national_back";
                        }
                        else if (imageType.equals("address_front")){
                            addressFrontImage="address_front";
                        }
                        else if (imageType.equals("address_back")){
                            addressBackImage="address_back";
                        }
                        else if (imageType.equals("passbook")){
                            passbook="selfie";
                        }

                    } else {

                        alertDialogs.alertDialog(IdentityDetailScreen.this, getResources().getString(R.string.app_name), t.body().getMsg(), "ok", "", new DialogCallBacks() {
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
                alertDialogs.alertDialog(IdentityDetailScreen.this, "ok", "Message document not updated.", "ok", "", new DialogCallBacks() {
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
            final Dialog selectCategoryDialog = simpleDialog.simpleDailog(IdentityDetailScreen.this, R.layout.select_category_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
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
            final Dialog selectCategoryDialog = simpleDialog.simpleDailog(IdentityDetailScreen.this, R.layout.select_category_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
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
    public void setDocType(String s){
        if(!s.isEmpty()) {
            select_DocTypeTV.setText(s);


            if(s.equals("National ID")){
                docType="adhaar";
                adharNoTV.setText("National ID Number*");
                reDocTV.setText("Re-Enter National ID Number*");
                nationalIDNumberET.setHint("Enter National ID number");
                reNationalIDNumberET.setHint("Enter National ID number");
                uploadFrontDocTitleTV.setText("Upload front of National ID Card");
                uploadBackDocTitleTV.setText("Upload back of National ID Card");
                docImage.setImageDrawable(getDrawable(R.drawable.sample_aadhaar_card_front_1));
                docBackImage.setImageDrawable(getDrawable(R.drawable.sample_aadhaar_card_back_1));

            }
        }
        else {
            docType="adhaar";
            select_DocTypeTV.setText(getString(R.string.personal));
            adharNoTV.setText("Aadhaar Number*");
            reDocTV.setText("Re-Enter Aadhaar Number*");
            uploadFrontDocTitleTV.setText("Upload front of Aadhaar Card");
            uploadBackDocTitleTV.setText("Upload back of Aadhaar Card");
            docImage.setImageDrawable(getDrawable(R.drawable.sample_aadhaar_card_front_1));
            docBackImage.setImageDrawable(getDrawable(R.drawable.sample_aadhaar_card_back_1));

        }

    }
}

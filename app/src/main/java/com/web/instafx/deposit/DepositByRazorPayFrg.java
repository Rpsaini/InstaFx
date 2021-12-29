package com.web.instafx.deposit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.dialogsnpickers.SimpleDialog;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.web.instafx.DefaultConstants;
import com.web.instafx.LoginActivity;
import com.web.instafx.R;
import com.web.instafx.kyc.GoogleAuthentication;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DepositByRazorPayFrg extends Fragment  {


    private View view;
    private EditText ed_remarks, ed_amount;
    private DepositeInrActivity depositeInrActivity;

    private Dialog paymentWaitingDialog;
    private RelativeLayout rr_payment_sucess, rr_rocket_fly;
    private TextView txt_seconds;

    public DepositByRazorPayFrg() {
        // Required empty public constructor
    }


    public static DepositByRazorPayFrg newInstance(String param1, String param2) {
        DepositByRazorPayFrg fragment = new DepositByRazorPayFrg();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_deposit_by_razor_pay_frg, container, false);
        Checkout.preload(getActivity());
        depositeInrActivity = (DepositeInrActivity) getActivity();
        init();
        return view;
    }


    private void init() {

        ed_remarks = view.findViewById(R.id.ed_remarks);
        ed_amount = view.findViewById(R.id.ed_amount);
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (depositeInrActivity.validationRule.checkEmptyString(ed_amount) == 0) {
                    depositeInrActivity.alertDialogs.alertDialog(depositeInrActivity, getResources().getString(R.string.app_name), "Enter Deposit Amount.", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (depositeInrActivity.validationRule.checkEmptyString(ed_remarks) == 0) {
                    depositeInrActivity.alertDialogs.alertDialog(depositeInrActivity, getResources().getString(R.string.app_name), "Enter Remark.", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {
                        }
                    });
                    return;
                }

                startPayment();
            }
        });


    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Checkout co = new Checkout();
//        co.setKeyID("rzp_test_xjp3GjvvlvvUIQ");
        co.setKeyID("rzp_live_wUEnsGRTSxmmvr");
        co.setImage(R.mipmap.ic_launcher);
        try {

            JSONObject data = new JSONObject(depositeInrActivity.savePreferences.reterivePreference(depositeInrActivity, DefaultConstants.login_detail).toString());
            JSONObject options = new JSONObject();
            options.put("name", data.getString("name"));
            options.put("description", ed_remarks.getText().toString());
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
           // options.put("upi_link", true);
           // types
           //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
           /*
            options.put("types", "upi_qr");
            options.put("fixed_amount", 1);
            options.put("payment_amount", Double.parseDouble(ed_amount.getText().toString()) * 100);
           */
            options.put("amount", Double.parseDouble(ed_amount.getText().toString()) * 100);
            JSONObject preFill = new JSONObject();
            preFill.put("email", data.getString("email"));
            preFill.put("contact", data.getString("mobile"));//9876543210

            options.put("prefill", preFill);

            co.open(getActivity(), options);
        } catch (Exception e)
        {

            System.out.println("Payment==="+e.getMessage());

        }
    }





    private Dialog paymentDialog;
    ImageView img_rocketfly;
    TextView payment_main_titile, payment_sub_title;
    TextView btn_done;
    ProgressBar progressbar;

    public void paymentSuccessFailure(String type, String paymentId)
    {
        SimpleDialog simpleDialog = new SimpleDialog();
        paymentDialog = simpleDialog.simpleDailog(depositeInrActivity, R.layout.payment_success_failure, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
        paymentDialog.setCancelable(true);


        txt_seconds = paymentDialog.findViewById(R.id.txt_seconds);
        img_rocketfly = paymentDialog.findViewById(R.id.img_rocketfly);
        payment_main_titile = paymentDialog.findViewById(R.id.payment_main_titile);
        payment_sub_title = paymentDialog.findViewById(R.id.payment_sub_title);
        btn_done = paymentDialog.findViewById(R.id.btn_done);
        progressbar = paymentDialog.findViewById(R.id.progressbar);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                paymentDialog.dismiss();
                depositeInrActivity.finish();
            }
        });

        txt_seconds.setText("00:30");
        changeLoader();
        updatePaymentStatus(type,paymentId);
    }


    int progress = 30;
    private void changeLoader() {
        progress--;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progress > 0)
                {
                    if (progress <= 9) {
                        txt_seconds.setText("00:0" + progress);
                    } else {
                        txt_seconds.setText("00:" + progress);
                    }
                    changeLoader();
                }
                else {
                    txt_seconds.setText("00:00" );
                    progressbar.setVisibility(View.GONE);
                }
            }
        }, 1000);
    }


    private void updatePaymentStatus(String type,String paymentID)
    {
        Map<String, String> map = new HashMap<>();
        map.put("token", depositeInrActivity.savePreferences.reterivePreference(getActivity(), DefaultConstants.token) + "");
        map.put("paymentID", paymentID);
        map.put("DeviceToken", depositeInrActivity.getDeviceToken());
        map.put("Version", depositeInrActivity.getAppVersion());
        map.put("PlatForm", "Android");
        map.put("Timestamp", System.currentTimeMillis()+"");
        map.put("payment_id", paymentID+"");
        map.put("amount", ed_amount.getText().toString());
        map.put("status", type);
        map.put("currency", "INR");

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("X-API-KEY", UtilClass.xApiKey);
        headerMap.put("Rtoken", depositeInrActivity.getNewRToken() + "");

        System.out.println("Before to sendddd==="+map);


        new ServerHandler().sendToServer(depositeInrActivity, depositeInrActivity.getApiUrl() + "fiat-payment-status", map, 1, headerMap, 20000, R.layout.progressbar, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons)
            {
                System.out.println("Response ==="+dta);
                try {
                    JSONObject obj = new JSONObject(dta);
                    if (obj.getBoolean("status"))
                     {
                        if(type.equalsIgnoreCase("1"))
                        {
                            showErrorDialog(type,obj.getString("msg"));
                        }
                        else
                        {
                            showErrorDialog("0","");
                        }

                        if (obj.has("token"))
                        {
                            depositeInrActivity.savePreferences.savePreferencesData(getActivity(), obj.getString("token"), DefaultConstants.token);
                            depositeInrActivity.savePreferences.savePreferencesData(getActivity(), obj.getString("r_token"), DefaultConstants.r_token);
                        }
                    }
                    else {
                        showErrorDialog(type, "");
                    }
                } catch (Exception e) {
                    progress=0;
                    e.printStackTrace();
                }

            }
        });
    }

    private void showErrorDialog(String code,String msg)
    {
        if(code.equalsIgnoreCase("1"))
        {
            progress = 0;
            img_rocketfly.setImageResource(R.drawable.check);
            payment_main_titile.setText(getResources().getString(R.string.success));
            payment_sub_title.setText(msg);
            btn_done.setVisibility(View.VISIBLE);
        }
        else if(code.equalsIgnoreCase("0"))
           {
            progress=0;
            img_rocketfly.setImageResource(R.drawable.failed);
            payment_main_titile.setText(getResources().getString(R.string.failed));
            payment_sub_title.setText(getResources().getString(R.string.depositfailed));
            btn_done.setVisibility(View.VISIBLE);
           }

    }



}
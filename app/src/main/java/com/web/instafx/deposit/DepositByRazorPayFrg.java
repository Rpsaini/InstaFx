package com.web.instafx.deposit;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dialogsnpickers.DialogCallBacks;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;

import org.json.JSONObject;


public class DepositByRazorPayFrg extends Fragment implements PaymentResultListener {


    private View view;
    private EditText ed_remarks,ed_amount;
    private  DepositeInrActivity depositeInrActivity;
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
        depositeInrActivity=(DepositeInrActivity)getActivity();
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


        try {

            JSONObject data=new JSONObject(depositeInrActivity.savePreferences.reterivePreference(depositeInrActivity, DefaultConstants.login_detail).toString());




            JSONObject options = new JSONObject();
            options.put("name", data.getString("name"));
            options.put("description", ed_remarks.getText().toString());
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", Double.parseDouble(ed_amount.getText().toString())*100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", data.getString("email"));
            preFill.put("contact", data.getString("mobile"));//9876543210
            preFill.put("order_id", data.getString("email")+System.currentTimeMillis());//9876543210

            options.put("prefill", preFill);

            co.open(getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID)
    {
        try {
            Toast.makeText(getActivity(), "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
          System.out.println("Exception==="+e.getMessage());

        }
    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(getActivity(), "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            System.out.println("Exception==="+e.getMessage());
        }
    }


}
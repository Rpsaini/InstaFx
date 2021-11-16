package com.web.instafx.kyc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;

public class VerifyKycAccountDetailsScreen extends BaseActivity {

    public static VerifyKycAccountDetailsScreen mVerifyKycAccountDetailsScreen;
    private TextView submitVerifyBT=null,review_tv,done_tv,you_haven_t,please_comp;
    private ImageView backIC=null,submit_line,review_line,withdraw_ic,deposit_inr_ic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_submit_account_screen);
        mVerifyKycAccountDetailsScreen=this;
        initiateObj();
        initView();
        setOnClickListener();
    }

    private void initView(){
        submitVerifyBT=findViewById(R.id.submitVerifyBT);
        review_tv=findViewById(R.id.review_tv);
        done_tv=findViewById(R.id.done_tv);
        submit_line=findViewById(R.id.submit_line);
        review_line=findViewById(R.id.review_line);
        deposit_inr_ic=findViewById(R.id.deposit_inr_ic);
        withdraw_ic=findViewById(R.id.withdraw_ic);
        backIC=findViewById(R.id.backIC);
        you_haven_t=findViewById(R.id.you_haven_tv);
        please_comp=findViewById(R.id.please_comp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeHeaderLayoutColorByKycStatus();
    }

    private void changeHeaderLayoutColorByKycStatus(){
      String kycStatus=  savePreferences.reterivePreference(mVerifyKycAccountDetailsScreen, DefaultConstants.kyc_status).toString();



      if(kycStatus.equals("1")){
          submitVerifyBT.setVisibility(View.GONE);
          submit_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));
          review_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
          review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_review, 0, 0);
          done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_done, 0, 0);
          withdraw_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_428));
          deposit_inr_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_428));
          you_haven_t.setText("We've received your submitted document and other information for KYC. We'll review your information and if all is in order will approve your identity. Otherwise we will request it again.");
          please_comp.setText("KYC is under review");

      }
      else if(kycStatus.equals("2")){
          submitVerifyBT.setVisibility(View.GONE);
          review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_review, 0, 0);
          done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_select_done, 0, 0);

          review_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));
          submit_line.setBackground(getResources().getDrawable(R.drawable.ic_select_line));

          withdraw_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_429));
          deposit_inr_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_429));
          you_haven_t.setText("Your KYC has been approved");
          please_comp.setText("Approved");

      }
      else if(kycStatus.equals("3")){
          review_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
          submit_line.setBackground(getResources().getDrawable(R.drawable.ic_line));
          submitVerifyBT.setVisibility(View.VISIBLE);
          review_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_review, 0, 0);
          done_tv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_done, 0, 0);
          withdraw_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_428));
          deposit_inr_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_428));


          you_haven_t.setText("We're having difficulties verifying your identity. The information you had submitted was unfortunately rejected ");
          please_comp.setText("Rejected");

      }
    }
    private void setOnClickListener(){
        submitVerifyBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mVerifyKycAccountDetailsScreen, VerifyCompleteSubmitKycScreen.class);
                startActivity(intent);
            }
        });

        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

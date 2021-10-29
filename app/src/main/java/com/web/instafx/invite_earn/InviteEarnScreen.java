package com.web.instafx.invite_earn;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;

import com.web.instafx.invite_earn.adapter.CommissionHistoryAdapter;
import com.web.instafx.invite_earn.adapter.ReferredFriendsAdapter;
import com.web.instafx.order_details.OrderDetailsScreen;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InviteEarnScreen extends BaseActivity {
    private LinearLayout level_referralsRoot,direct_referralsRoot,copyLinkLL;
    private RelativeLayout level_referralsRL,direct_referralsRL;
    private TextView shareYourLinkTV,level_referralsTV,direct_referralsTV,totalReferredFriendValueTV,totalCommisionEarnedValueTV,totalCommisionRateValueTV,inviteEarnLinkTV;
    private View level_referralsLine,direct_referralsLine;
    private RecyclerView level_referralsRV,direct_referralsRV;
    private JSONArray level_referralsArray,direct_referralsArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_earn_screen);
        getSupportActionBar().hide();
        initiateObj();
        initView();
        setOnClickListener();
        getMyReferralsDetails();
    }
    private void initView(){
        copyLinkLL=findViewById(R.id.copyLinkLL);
        totalReferredFriendValueTV=findViewById(R.id.totalReferredFriendValueTV);
        totalCommisionEarnedValueTV=findViewById(R.id.totalCommisionEarnedValueTV);
        totalCommisionRateValueTV=findViewById(R.id.totalCommisionRateValueTV);
        inviteEarnLinkTV=findViewById(R.id.inviteEarnLinkTV);
        shareYourLinkTV=findViewById(R.id.shareYourLinkTV);

        level_referralsLine=findViewById(R.id.commissionHistoryLine);
        direct_referralsLine=findViewById(R.id.referredFriendsLine);
        level_referralsTV=findViewById(R.id.level_referralsTV);
        direct_referralsTV=findViewById(R.id.direct_referralsTV);
        level_referralsRL=findViewById(R.id.level_referralsRL);
        direct_referralsRL=findViewById(R.id.direct_referralsRL);
        level_referralsRoot=findViewById(R.id.commissions_history_layout);
        direct_referralsRoot=findViewById(R.id.referred_friends_layout);
        level_referralsRV=findViewById(R.id.commissionHistoryRV);
        direct_referralsRV=findViewById(R.id.referredFriendsRV);

    }

    private void setOnClickListener(){
        level_referralsRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level_referralsTV.setTextColor(getResources().getColor(R.color.white));
                level_referralsLine.setVisibility(View.VISIBLE);
                direct_referralsLine.setVisibility(View.GONE);
                direct_referralsTV.setAlpha(0.8f);
                level_referralsRoot.setVisibility(View.VISIBLE);
                direct_referralsRoot.setVisibility(View.GONE);
                showLevelReferralsDetails(level_referralsArray);
            }
        });
        direct_referralsRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direct_referralsTV.setTextColor(getResources().getColor(R.color.white));
                direct_referralsLine.setVisibility(View.VISIBLE);
                level_referralsLine.setVisibility(View.GONE);
                level_referralsTV.setAlpha(0.8f);
                direct_referralsRoot.setVisibility(View.VISIBLE);
                level_referralsRoot.setVisibility(View.GONE);
                showDirectReferralsDetails(direct_referralsArray);
            }
        });
    }

    private void showLevelReferralsDetails(JSONArray jsonArray)
    {
        CommissionHistoryAdapter mAdapter = new CommissionHistoryAdapter(this,jsonArray);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        level_referralsRV.setLayoutManager(horizontalLayoutManagaer);
        level_referralsRV.setItemAnimator(new DefaultItemAnimator());
        level_referralsRV.setAdapter(mAdapter);
    }

    private void showDirectReferralsDetails(JSONArray jsonArray)
    {
        ReferredFriendsAdapter mAdapter = new ReferredFriendsAdapter(this,jsonArray);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        direct_referralsRV.setLayoutManager(horizontalLayoutManagaer);
        direct_referralsRV.setItemAnimator(new DefaultItemAnimator());
        direct_referralsRV.setAdapter(mAdapter);
    }
    private void getMyReferralsDetails()
    {

        try {
            Map<String, String> m = new LinkedHashMap<>();
            m.put("token", savePreferences.reterivePreference(InviteEarnScreen.this, DefaultConstants.token) + "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("DeviceToken", getDeviceToken() + "");

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);

            new ServerHandler().sendToServer(InviteEarnScreen.this, getApiUrl() + "get-my-referrals", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                      //  Log.e("OrderDetails","OrderDetails::"+dta);


 /*
  {"status":true,"commission":{"res":true,
  "rows":{"total":"25"}},
  "level_referrals":[{"email":"dhillonn********@gmail.com","joined_on":"18
Oct, 2021 05:51 pm","level":"1"}],
"direct_referrals":[{"email":"dhillonn********@gmail.com","joined_on":"18 Oct, 2021
05:51 pm"}],"total_referred":"1","code":200} */

                        JSONObject obj = new JSONObject(dta);
                        if (obj.getBoolean("status")) {
                            totalReferredFriendValueTV.setText(obj.getString("total_referred"));
                            totalCommisionEarnedValueTV.setText(getString(R.string.inr_symbol)+obj.getString("commission"));
                            inviteEarnLinkTV.setText(obj.getString("referral_link"));
                           // level_referralsArray=obj.getJSONArray("level_referrals");
                           // direct_referralsArray=obj.getJSONArray("direct_referrals");
                           // showLevelReferralsDetails(level_referralsArray);
                          /*  try {

                               // ;
                                //;


                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/

                        } else {
                            alertDialogs.alertDialog(InviteEarnScreen.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed)
                                {
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
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}

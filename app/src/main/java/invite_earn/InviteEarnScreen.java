package invite_earn;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.web.instafx.BaseActivity;
import com.web.instafx.R;
import com.web.instafx.adapters.BankDetailsAdapters;

import org.json.JSONArray;

import invite_earn.adapter.CommissionHistoryAdapter;
import invite_earn.adapter.ReferredFriendsAdapter;

public class InviteEarnScreen extends BaseActivity {
    private LinearLayout commissionHistoryRoot,referredFriendsRoot;
    private RelativeLayout commissionHistoryRL,referredFriendsRL;
    private TextView commissionHistoryTV,referredFriendsTV;
    private View commissionHistoryLine,referredFriendsLine;
    private RecyclerView commissionHistoryRV,referredFriendsRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_earn_screen);
        getSupportActionBar().hide();
        initiateObj();
        initView();
        setOnClickListener();
        showCommissionHistoryDetails();
    }
    private void initView(){
        commissionHistoryLine=findViewById(R.id.commissionHistoryLine);
        referredFriendsLine=findViewById(R.id.referredFriendsLine);
        commissionHistoryTV=findViewById(R.id.commissionHistoryTV);
        referredFriendsTV=findViewById(R.id.referredFriendsTV);
        commissionHistoryRL=findViewById(R.id.commissionHistoryRL);
        referredFriendsRL=findViewById(R.id.referredFriendsRL);
        commissionHistoryRoot=findViewById(R.id.commissions_history_layout);
        referredFriendsRoot=findViewById(R.id.referred_friends_layout);
        commissionHistoryRV=findViewById(R.id.commissionHistoryRV);
        referredFriendsRV=findViewById(R.id.referredFriendsRV);

    }

    private void setOnClickListener(){
        commissionHistoryRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               commissionHistoryTV.setTextColor(getResources().getColor(R.color.white));
               commissionHistoryLine.setVisibility(View.VISIBLE);
               referredFriendsLine.setVisibility(View.GONE);
                referredFriendsTV.setAlpha(0.8f);
               commissionHistoryRoot.setVisibility(View.VISIBLE);
               referredFriendsRoot.setVisibility(View.GONE);
               showCommissionHistoryDetails();
            }
        });
        referredFriendsRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referredFriendsTV.setTextColor(getResources().getColor(R.color.white));
                referredFriendsLine.setVisibility(View.VISIBLE);
                commissionHistoryLine.setVisibility(View.GONE);
                commissionHistoryTV.setAlpha(0.8f);
                referredFriendsRoot.setVisibility(View.VISIBLE);
                commissionHistoryRoot.setVisibility(View.GONE);
                showReferredFriendsDetails();
            }
        });
    }

    private void showCommissionHistoryDetails()
    {
        CommissionHistoryAdapter mAdapter = new CommissionHistoryAdapter(this);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        commissionHistoryRV.setLayoutManager(horizontalLayoutManagaer);
        commissionHistoryRV.setItemAnimator(new DefaultItemAnimator());
        commissionHistoryRV.setAdapter(mAdapter);
    }

    private void showReferredFriendsDetails()
    {
        ReferredFriendsAdapter mAdapter = new ReferredFriendsAdapter(this);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        referredFriendsRV.setLayoutManager(horizontalLayoutManagaer);
        referredFriendsRV.setItemAnimator(new DefaultItemAnimator());
        referredFriendsRV.setAdapter(mAdapter);
    }
}

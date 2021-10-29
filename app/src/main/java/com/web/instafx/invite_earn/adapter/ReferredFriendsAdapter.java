package com.web.instafx.invite_earn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.web.instafx.R;

import org.json.JSONArray;
import org.json.JSONObject;

import com.web.instafx.invite_earn.InviteEarnScreen;


public class ReferredFriendsAdapter extends RecyclerView.Adapter<ReferredFriendsAdapter.MyViewHolder> {
    private InviteEarnScreen ira1;
    private JSONArray moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView emailTV,dateTV;
        private LinearLayout ll_deposit;


        public MyViewHolder(View view)
        {
            super(view);
            emailTV = view.findViewById(R.id.emailTV);
            dateTV = view.findViewById(R.id.dateTV);
            ll_deposit = view.findViewById(R.id.ll_deposit);
        }
    }

    public ReferredFriendsAdapter(InviteEarnScreen inviteEarnScreen, JSONArray dataAr) {
        this.moviesList = dataAr;
        this.ira1 = inviteEarnScreen;

    }
    public ReferredFriendsAdapter(InviteEarnScreen inviteEarnScreen) {
        this.ira1 = inviteEarnScreen;

    }

    @Override
    public ReferredFriendsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.referred_friends_row_items, parent, false);
        return new ReferredFriendsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ReferredFriendsAdapter.MyViewHolder holder, final int position) {
        try
         {
           /*
  {"status":true,"commission":{"res":true,
  "rows":{"total":"25"}},
  "level_referrals":[{"email":"dhillonn********@gmail.com","joined_on":"18
Oct, 2021 05:51 pm","level":"1"}],
"direct_referrals":[{"email":"dhillonn********@gmail.com","joined_on":"18 Oct, 2021
05:51 pm"}],"total_referred":"1","code":200} */
            JSONObject dataObj = moviesList.getJSONObject(position);
            holder.emailTV.setText(dataObj.getString("email"));
            holder.dateTV.setText(dataObj.getString("joined_on"));

         }
        catch(Exception e)
        {
            e.printStackTrace();
           }
    }

    @Override
    public int getItemCount() {
        return moviesList.length();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
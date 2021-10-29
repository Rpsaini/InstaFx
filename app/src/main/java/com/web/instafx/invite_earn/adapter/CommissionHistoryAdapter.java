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


public class CommissionHistoryAdapter extends RecyclerView.Adapter<CommissionHistoryAdapter.MyViewHolder> {
    private InviteEarnScreen ira1;
    private JSONArray moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private  TextView emailTV,dateTV,commissionTV;
        private LinearLayout ll_deposit;


        public MyViewHolder(View view)
        {
            super(view);
            emailTV = view.findViewById(R.id.emailTV);
            dateTV = view.findViewById(R.id.dateTV);
            commissionTV = view.findViewById(R.id.commissionTV);
            ll_deposit = view.findViewById(R.id.ll_deposit);
        }
    }

    public CommissionHistoryAdapter(InviteEarnScreen inviteEarnScreen, JSONArray dataAr) {
        this.moviesList = dataAr;
        this.ira1 = inviteEarnScreen;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.commission_history_row_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try
         {

            JSONObject dataObj = moviesList.getJSONObject(position);
            holder.emailTV.setText(dataObj.getString("email"));
            holder.dateTV.setText(dataObj.getString("joined_on"));
            holder.commissionTV.setText(dataObj.getString("level"));

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
package com.web.instafx.activity_log.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.web.instafx.R;
import com.web.instafx.activity_log.ActivityLogScreens;

import org.json.JSONArray;
import org.json.JSONObject;


public class ActivityLogsAdapter extends RecyclerView.Adapter<ActivityLogsAdapter.MyViewHolder> {
    private ActivityLogScreens ira1;
    private JSONArray moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
     /*   private  TextView txt_currency_name;
        private ImageView img_currencyicon,selectIC;*/
        private LinearLayout ll_list_row;
        private View line;
        private TextView dateValueTV,ipTV,successTV;
        public MyViewHolder(View view)
        {
            super(view);
           /*
            txt_currency_name = view.findViewById(R.id.txt_currency_name);
            img_currencyicon = view.findViewById(R.id.img_currencyicon);
            selectIC = view.findViewById(R.id.selectIC);
            */

            ll_list_row = view.findViewById(R.id.ll_fund_list_row);
            dateValueTV = view.findViewById(R.id.dateValueTV);
            ipTV = view.findViewById(R.id.ipValueTV);
            successTV = view.findViewById(R.id.activityValueTV);
            line = view.findViewById(R.id.line);
        }
    }


    public ActivityLogsAdapter(ActivityLogScreens showFiatCurrencyDepositWithdraw, JSONArray dataAr) {
        this.moviesList = dataAr;
        this.ira1 = showFiatCurrencyDepositWithdraw;

    }
    public ActivityLogsAdapter(ActivityLogScreens showFiatCurrencyDepositWithdraw) {
        this.ira1 = showFiatCurrencyDepositWithdraw;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_log_row_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try
        {
          /*

           {"type":"Placed Order",
           "description":"User company has placed Limit order of volume
           17 @price 166.35326898 Vide order id 4096500","ip":"13.127.217.37",
           "date":"2021-11-11 19:58:19"}

      */
            JSONObject dataObj = moviesList.getJSONObject(position);
            holder.dateValueTV.setText( dataObj.getString("date"));
            holder.ipTV.setText( dataObj.getString("ip"));
            holder.successTV.setText( dataObj.getString("type"));
           if (position==9){
               holder.line.setVisibility(View.GONE);
           }
           else {
               holder.line.setVisibility(View.VISIBLE);
           }

        } catch (Exception e) {
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
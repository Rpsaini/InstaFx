package com.web.instafx.staking.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.web.instafx.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class StakeCoinAdapter extends RecyclerView.Adapter<StakeCoinAdapter.MyViewHolder> {

    private JSONArray datAr;
    private AppCompatActivity pActivity;

    public StakeCoinAdapter(JSONArray ar, AppCompatActivity paActiviity) {
        datAr = ar;
        pActivity = paActiviity;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        LinearLayout ll_best_restaurant;
        TextView sr_noTV,currencyTV,stackForTV,amountTV;
        TextView titleTV;


        public MyViewHolder(View view) {
            super(view);

            ll_best_restaurant = view.findViewById(R.id.ll_deposit);
            sr_noTV = view.findViewById(R.id.sr_noTV);
            currencyTV = view.findViewById(R.id.currencyTV);
            stackForTV = view.findViewById(R.id.staked_forTV);
            amountTV = view.findViewById(R.id.amountTV);


        }
    }


    @Override
    public StakeCoinAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stake_coin_items, parent, false);

        return new StakeCoinAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StakeCoinAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        /*

         "user_stakings":[{"symbol":"CGB","type":"2","value":"180","amount":"1539.6341","interest":"3"
         ,"staked_on":"2021-11-15 12:03:37","expired_on":"2022-05-14 12:03:37","status":"1"},


*/
        try {
            JSONObject object = (JSONObject) datAr.get(position);
            String currency = object.getString("symbol");
            String stackFor = object.getString("value");
            String amount = object.getString("amount");

            int srNo=position+1;
            holder.sr_noTV.setText(""+srNo);
            holder.currencyTV.setText(currency);
            holder.stackForTV.setText(stackFor+" Days");
            holder.amountTV.setText(amount);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return datAr.length();
    }


    private void showImage(final String url, final ImageView header_img) {
        pActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(pActivity)
                        .load(url)
                        .placeholder(R.drawable.no_data)
                        // .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .into(header_img);
            }
        });


    }
}

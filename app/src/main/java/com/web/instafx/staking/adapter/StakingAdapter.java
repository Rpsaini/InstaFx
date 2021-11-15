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

import com.web.instafx.staking.StakingScreen;

public class StakingAdapter extends RecyclerView.Adapter<StakingAdapter.MyViewHolder> {

    private JSONArray datAr;
    private AppCompatActivity pActivity;
    private String imageUrl = "";
    private RadioButton commonChekBox;
    private String type = "";
    public StakingAdapter(AppCompatActivity paActiviity,JSONArray ar, String type) {
        pActivity = paActiviity;
        datAr = ar;
        this.type=type;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {


        LinearLayout ll_best_restaurant;
        TextView txt_description;
        TextView titleTV;


        public MyViewHolder(View view) {
            super(view);

            ll_best_restaurant = view.findViewById(R.id.ll_deposit);
            txt_description = view.findViewById(R.id.txt_description);
            titleTV = view.findViewById(R.id.titleTV);


        }
    }


    @Override
    public StakingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.auto_cgb_stak_items, parent, false);

        return new StakingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StakingAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
/*

{"id":"1","currency_id":"7","name":"$100-$499 - 180 Days
(3%)","type":"2","bonus":"5","value":"180","interest":"3","minimum_stake":"100","maximum_stake":"499","status":"1","created_on":"2021-08-18
12:15:40","symbol":"CGB"},{"id":"2","currency_id":"7","name":"$100-$499 - 365 Days
(4%)","type":"2","bonus":"5","value":"365","interest":"4","minimum_stake":"100","maximum_stake":"499","status":"1","created_on":"2021-08-18
12:25:13","symbol":"CGB"} */
        if (!type.isEmpty()) {
        try {
                JSONObject object = (JSONObject) datAr.get(position);
                String name = object.getString("name");
                String interest = object.getString("interest");
                holder.titleTV.setText(name);
                holder.txt_description.setText(interest+"%");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
          try {
              JSONObject object = (JSONObject) datAr.get(position);
              String name = object.getString("title");
              String interest = object.getString("value");
              holder.titleTV.setText(name);
              holder.txt_description.setText(interest);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

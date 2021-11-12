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

import com.web.instafx.staking.StakingScreen;

public class StakingAdapter extends RecyclerView.Adapter<StakingAdapter.MyViewHolder> {

    private JSONArray datAr;
    private AppCompatActivity pActivity;
    private String imageUrl = "";
    private RadioButton commonChekBox;
    private String type = "";
    public StakingAdapter(AppCompatActivity paActiviity, String type) {
        pActivity = paActiviity;
    }
    public StakingAdapter(JSONArray ar, AppCompatActivity paActiviity) {
        datAr = ar;
        pActivity = paActiviity;
    }

    public StakingAdapter(JSONArray ar, AppCompatActivity paActiviity, String type) {
        datAr = ar;
        pActivity = paActiviity;
        this.type = type;
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
       /* if (pActivity instanceof StakingScreen) {
          *//*  try {
                JSONObject object = (JSONObject) datAr.get(position);
                String type = object.getString("name");


            } catch (Exception e) {
                e.printStackTrace();
            }*//*

        }*/
        if (!type.isEmpty()) {
            holder.titleTV.setText("For $100-$499-180 Days(3%)");
            holder.txt_description.setText("3%");
        }
    }

    @Override
    public int getItemCount() {
        return 5;
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

package com.web.instafx.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.web.instafx.MainActivity;
import com.web.instafx.R;
import com.web.instafx.fragments.QuickBuyFragment;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuickBuyAdapter extends RecyclerView.Adapter<QuickBuyAdapter.MyViewHolder> {
    private MainActivity ira1;
    private JSONArray quickAr;
    private QuickBuyFragment fundFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_currency_name, tv_buy, txt_currency_price, txt_currency_fullname, txt_change;

        LinearLayout ll_fund_list_row;
        ImageView img_currencyicon, img_arrow;


        public MyViewHolder(View view) {
            super(view);
            txt_currency_name = view.findViewById(R.id.txt_currency_name);
            tv_buy = view.findViewById(R.id.tv_buy);
            ll_fund_list_row = view.findViewById(R.id.ll_fund_list_row);
            img_currencyicon = view.findViewById(R.id.img_currencyicon);
            txt_currency_price = view.findViewById(R.id.txt_currency_price);
            txt_currency_fullname = view.findViewById(R.id.txt_currency_fullname);
            img_arrow = view.findViewById(R.id.img_arrow);
            txt_change = view.findViewById(R.id.txt_change);


        }
    }


    public QuickBuyAdapter(JSONArray quickAr, MainActivity mainActivity, QuickBuyFragment fundFragment) {
        this.quickAr = quickAr;
        this.ira1 = mainActivity;
        this.fundFragment = fundFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quick_buy_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {

            JSONObject dataObj = quickAr.getJSONObject(position);
            holder.txt_currency_name.setText(dataObj.getString("base"));
            holder.txt_currency_price.setText(dataObj.getString("buy_price") + " " + dataObj.getString("term"));
            holder.txt_currency_fullname.setText(dataObj.getString("base_name"));
            showImage(dataObj.getString("icon"), holder.img_currencyicon);
            holder.tv_buy.setTag(dataObj);

            String change = dataObj.getString("change");
            System.out.println("Change====" + change);
            holder.txt_change.setText(change);
            if (change.contains("+")) {
                holder.txt_change.setTextColor(ira1.getResources().getColor(R.color.greencolor));

                holder.img_arrow.setRotation(270);
                holder.img_arrow.setColorFilter(ira1.getResources().getColor(R.color.greencolor),PorterDuff.Mode.SRC_ATOP);


            } else if (change.contains("-")) {

                holder.txt_change.setTextColor(ira1.getResources().getColor(R.color.darkRed));
                holder.img_arrow.setRotation(90);
                holder.img_arrow.setColorFilter(ira1.getResources().getColor(R.color.darkRed), PorterDuff.Mode.SRC_ATOP);



            } else {

                holder.txt_change.setTextColor(ira1.getResources().getColor(R.color.greencolor));
                holder.img_arrow.setRotation(270);
                holder.img_arrow.setColorFilter(ira1.getResources().getColor(R.color.greencolor),PorterDuff.Mode.SRC_ATOP);


            }


            holder.tv_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        JSONObject data = new JSONObject(v.getTag().toString());
                        fundFragment.buysellDialog(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return quickAr.length();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void showImage(final String url, final ImageView header_img) {
        ira1.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(ira1)
                        .load(url)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(3)))
                        .into(header_img);
            }
        });
    }
}
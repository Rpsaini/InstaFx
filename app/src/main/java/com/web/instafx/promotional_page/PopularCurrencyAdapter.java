package com.web.instafx.promotional_page;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.web.instafx.R;
import com.web.instafx.activity_log.ActivityLogScreens;


import org.json.JSONArray;
import org.json.JSONObject;

public class PopularCurrencyAdapter extends RecyclerView.Adapter<PopularCurrencyAdapter.MyViewHolder> {
    private AppCompatActivity ira1;
    private JSONArray moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView currencyIC;
        private LinearLayout ll_list_row;
        private TextView favouriteTV,currencyTV,amountTV,percentTV;
        public MyViewHolder(View view)
        {
            super(view);
            ll_list_row = view.findViewById(R.id.ll_deposit);
            currencyIC = view.findViewById(R.id.currencyIC);
            favouriteTV = view.findViewById(R.id.favouriteTV);
            currencyTV = view.findViewById(R.id.currencyTV);
            amountTV = view.findViewById(R.id.amountTV);
            percentTV = view.findViewById(R.id.percentTV);
        }
    }


    public PopularCurrencyAdapter(AppCompatActivity showFiatCurrencyDepositWithdraw, JSONArray dataAr) {
        this.moviesList = dataAr;
        this.ira1 = showFiatCurrencyDepositWithdraw;

    }

    @Override
    public PopularCurrencyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_currency_items, parent, false);
        return new PopularCurrencyAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PopularCurrencyAdapter.MyViewHolder holder, final int position) {
        try
        {
          /*

        {"price":"4809078.0903",
        "symbol":"BTC",
        "icon":"https:\/\/instfx.com\/front\/resources\/img\/currency-icons\/BTC.png"}
      */
            JSONObject dataObj = moviesList.getJSONObject(position);
            holder.amountTV.setText(dataObj.getString("price"));
            holder.currencyTV.setText(dataObj.getString("symbol"));
            holder.percentTV.setText(dataObj.getString("percentage")+"%");
            showImage(dataObj.getString("icon"),holder.currencyIC);
            if (dataObj.getDouble("price")>0){
                setTextViewDrawableColor(holder.percentTV, R.color.greencolor);
                holder.percentTV.setTextColor(ira1.getResources().getColor(R.color.greencolor));
            }
            else {
                setTextViewDrawableColor(holder.percentTV, R.color.percentNegativeRed);
                holder.percentTV.setTextColor(ira1.getResources().getColor(R.color.percentNegativeRed));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ira1.getResources().getColor(color), PorterDuff.Mode.SRC_IN));
            }
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

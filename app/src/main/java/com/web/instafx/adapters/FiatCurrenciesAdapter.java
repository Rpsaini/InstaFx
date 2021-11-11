package com.web.instafx.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.web.instafx.FundHistoryActivity;
import com.web.instafx.MainActivity;
import com.web.instafx.R;
import com.web.instafx.fiatdepositwithdraw.ShowFiatCurrencyDepositWithdraw;
import com.web.instafx.fragments.FundFragment;
import com.web.instafx.fund_withdrawal.WithdrawalFundScreen;

import org.json.JSONObject;

import java.util.ArrayList;


public class FiatCurrenciesAdapter extends RecyclerView.Adapter<FiatCurrenciesAdapter.MyViewHolder> {
    private MainActivity ira1;
    private ArrayList<JSONObject> moviesList;
    private FundFragment fundFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_currency_name, tv_balance;

        LinearLayout ll_fund_list_row;
        ImageView img_currencyicon;
        ImageView ic_more;

        public MyViewHolder(View view) {
            super(view);


            txt_currency_name = view.findViewById(R.id.txt_currency_name);
            tv_balance = view.findViewById(R.id.tv_balance);
            ll_fund_list_row = view.findViewById(R.id.ll_fund_list_row);
            img_currencyicon = view.findViewById(R.id.img_currencyicon);
            ic_more = view.findViewById(R.id.ic_more);


        }
    }


    public FiatCurrenciesAdapter(ArrayList<JSONObject> moviesList, MainActivity mainActivity, FundFragment fundFragment) {
        this.moviesList = moviesList;
        this.ira1 = mainActivity;
        this.fundFragment = fundFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fund_adapter_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {

            JSONObject dataObj = moviesList.get(position);

            holder.txt_currency_name.setText(dataObj.getString("symbol"));
            holder.tv_balance.setText(dataObj.getString("available_balance"));

            showImage(dataObj.getString("icon"), holder.img_currencyicon);
            holder.ll_fund_list_row.setTag(dataObj);

            holder.ll_fund_list_row.setTag(dataObj);
            holder.ll_fund_list_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ira1, FundHistoryActivity.class);
                    intent.putExtra("data",v.getTag()+"");
                    ira1.startActivity(intent);
                }
            });


            holder.ic_more.setTag(dataObj);
            holder.ic_more.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    PopupWindow popupwindow_obj = popupDisplay(v.getTag() + "");
                    popupwindow_obj.setBackgroundDrawable(new ColorDrawable(
                            android.graphics.Color.TRANSPARENT));
                    popupwindow_obj.showAsDropDown(v, -10, 5);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
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

    public PopupWindow popupDisplay(String dataStr) {
        final PopupWindow popupWindow = new PopupWindow(ira1);
        try {
            LayoutInflater inflater = (LayoutInflater) ira1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.fund_item_popup_menu, null);
            LinearLayout depositLLItem = view.findViewById(R.id.depositLL);
            LinearLayout withdrawalLLItem = view.findViewById(R.id.withdrawalLL);
            TextView depositTV = view.findViewById(R.id.depositTV);
            LinearLayout p2ptransfer = view.findViewById(R.id.p2ptransfer);
            withdrawalLLItem.setVisibility(View.GONE);
            p2ptransfer.setVisibility(View.GONE);

            popupWindow.setFocusable(true);
            popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

            popupWindow.setContentView(view);
            JSONObject data = new JSONObject(dataStr);
            String symbol = data.getString("symbol");
            depositTV.setText("Deposit/Withdraw "+symbol);
           depositLLItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                { if(symbol.equalsIgnoreCase("INR"))
                    {
                        Intent intent = new Intent(ira1, ShowFiatCurrencyDepositWithdraw.class);
                        intent.putExtra("data", data + "");
                        ira1.startActivity(intent);
                    } else
                        {
                            Intent intent = new Intent(ira1, ShowFiatCurrencyDepositWithdraw.class);
                            intent.putExtra("data", data + "");
                            ira1.startActivity(intent);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                popupWindow.dismiss();
            }
        });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return popupWindow;
    }

}
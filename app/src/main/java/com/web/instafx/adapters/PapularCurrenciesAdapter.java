package com.web.instafx.adapters;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.web.instafx.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class PapularCurrenciesAdapter extends RecyclerView.Adapter<PapularCurrenciesAdapter.MyViewHolder> {
    private AppCompatActivity ira1;
    private JSONArray moviesList;
    Map<Integer,String> changeMap=new HashMap<>();



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_currencyname, txt_change, txt_curreny_price, txt_currency_fiat_price,txt_currencyname_fiat;



        public MyViewHolder(View view) {
            super(view);

            txt_currencyname = view.findViewById(R.id.txt_currencyname);
            txt_change = view.findViewById(R.id.txt_change);
            txt_curreny_price = view.findViewById(R.id.txt_curreny_price);
            txt_currency_fiat_price = view.findViewById(R.id.txt_currency_fiat_price);
            txt_currencyname_fiat = view.findViewById(R.id.txt_currencyname_fiat);


        }
    }


    public PapularCurrenciesAdapter(JSONArray moviesList, AppCompatActivity mainActivity) {
        this.moviesList = moviesList;
        ira1=mainActivity;



    }

    @Override
    public PapularCurrenciesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.papular_currencies_list_row, parent, false);

        return new PapularCurrenciesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PapularCurrenciesAdapter.MyViewHolder holder, final int position) {
        try {


            JSONObject object=moviesList.getJSONObject(position);

            String pair_id=object.getString("pair_id");
            String symbol=object.getString("symbol");
            String price= object.getString("price");
            String change=object.getString("percentage");
            String icon=object.getString("icon");


            holder.txt_currencyname.setText(symbol.split("\\/")[0]);
            holder.txt_currencyname_fiat.setText("/"+symbol.split("\\/")[1]);
            holder.txt_change.setText(change+"%");
            holder.txt_curreny_price.setText(price);
            holder.txt_currency_fiat_price.setText("$"+price);

            if(change.contains("+"))
            {
                holder.txt_curreny_price.setTextColor(ira1.getResources().getColor(R.color.greencolor));
                holder.txt_change.setTextColor(ira1.getResources().getColor(R.color.greencolor));
            }
            else if(change.contains("-"))
            {

                holder.txt_curreny_price.setTextColor(ira1.getResources().getColor(R.color.darkRed));
                holder.txt_change.setTextColor(ira1.getResources().getColor(R.color.darkRed));
            }
            else
            {
                holder.txt_curreny_price.setTextColor(ira1.getResources().getColor(R.color.greencolor));
                holder.txt_change.setTextColor(ira1.getResources().getColor(R.color.greencolor));
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



    private void animationEffect(int x, LinearLayout linearLayout, int position)
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(x<0)
                {
                    linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.light_color_red));
                }
                else if(x>0)
                {

                    linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.light_color_green));
                }
                else
                {
                    linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.light_color_green));
                }
            }
        },500) ;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                if(position%2==0)
                {
                    linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.section_color_lite));

                }
                else
                {
                    linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.section_color));
                }
            }
        },1500);


    }
}
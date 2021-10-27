package com.web.instafx.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.web.instafx.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.MyViewHolder>
  {
    private AppCompatActivity ira1;
    private JSONArray moviesList;
  private String type="";

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_date,tv_balance,txt_status,txt_currency;
        ImageView input_arrow;

        public MyViewHolder(View view)
        {
            super(view);
            txt_date=view.findViewById(R.id.txt_date);
            tv_balance=view.findViewById(R.id.tv_balance);
            txt_status=view.findViewById(R.id.txt_status);
            txt_currency=view.findViewById(R.id.txt_currency);
            input_arrow=view.findViewById(R.id.input_arrow);

        }
    }


    public TransactionHistoryAdapter(JSONArray moviesList, AppCompatActivity mainActivity,String type)
      {
        this.moviesList = moviesList;
        ira1=mainActivity;
        this.type=type;

      }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_history, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        try
        {

            JSONObject dataObj=moviesList.getJSONObject(position);

            String status=dataObj.getString("status");


            holder.txt_date.setText(dataObj.getString("date"));
            holder.tv_balance.setText(dataObj.getString("amount")+" "+dataObj.getString("symbol"));
            if(status.equalsIgnoreCase("1"))
            {
                holder.txt_status.setText("(Approved)");
                holder.txt_status.setTextColor(ira1.getResources().getColor(R.color.green));
            }
            else
            {
                holder.txt_status.setText("(Pending)");
                holder.txt_status.setTextColor(ira1.getResources().getColor(R.color.app_red_color));
            }

            if(type.equalsIgnoreCase("deposite"))
            {
                holder.txt_currency.setText("Deposit");
                holder.input_arrow.setRotation(300);
            }
            else
            {
                holder.txt_currency.setText("Withdraw");
                holder.input_arrow.setRotation(130);
            }
//            status    1 approved   0 pending


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount()
    {
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


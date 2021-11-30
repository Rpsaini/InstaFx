package com.web.instafx.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.instafx.DefaultConstants;
import com.web.instafx.FundHistoryActivity;
import com.web.instafx.GetKeysActivity;
import com.web.instafx.R;
import com.web.instafx.fiatdepositwithdraw.WithdrawInrActivity;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.MyViewHolder> {
    private FundHistoryActivity ira1;
    private ArrayList<JSONObject> moviesList;
    private String type = "";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_date, tv_balance, txt_status, txt_currency, txt_cancel;
        ImageView input_arrow;

        public MyViewHolder(View view) {
            super(view);
            txt_date = view.findViewById(R.id.txt_date);
            tv_balance = view.findViewById(R.id.tv_balance);
            txt_status = view.findViewById(R.id.txt_status);
            txt_currency = view.findViewById(R.id.txt_currency);
            input_arrow = view.findViewById(R.id.input_arrow);
            txt_cancel = view.findViewById(R.id.txt_cancel);

        }
    }


    public TransactionHistoryAdapter(ArrayList<JSONObject> moviesList, FundHistoryActivity mainActivity, String type) {
        this.moviesList = moviesList;
        ira1 = mainActivity;
        this.type = type;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_history, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {

            JSONObject dataObj = moviesList.get(position);
            String status = dataObj.getString("status");

            System.out.println("after change==="+dataObj);
            holder.txt_date.setText(dataObj.getString("date"));
            holder.tv_balance.setText(dataObj.getString("amount") + " " + dataObj.getString("symbol"));
            if (status.equalsIgnoreCase("1"))
            {
                holder.txt_status.setText("(Approved)");
                holder.txt_status.setTextColor(ira1.getResources().getColor(R.color.green));
            } else if(status.equalsIgnoreCase("0")) {
                holder.txt_status.setText("(Pending)");
                holder.txt_status.setTextColor(ira1.getResources().getColor(R.color.blue_color));
            }
            else if(status.equalsIgnoreCase("3"))
             {
                holder.txt_status.setText("(Cancelled)");
                holder.txt_status.setTextColor(ira1.getResources().getColor(R.color.app_red_color));
            }
             else
            {
                holder.txt_status.setText("(Rejected)");
                holder.txt_status.setTextColor(ira1.getResources().getColor(R.color.app_red_color));
            }

            if (type.equalsIgnoreCase("deposite")) {
                holder.txt_currency.setText("Deposit");
                holder.input_arrow.setRotation(300);
            } else {
                holder.txt_currency.setText("Withdraw");
                holder.input_arrow.setRotation(130);

                String cancelable = dataObj.getString("cancelable");
                if(cancelable.equalsIgnoreCase("1"))
                {
                    holder.txt_cancel.setVisibility(View.VISIBLE);
                    holder.txt_cancel.setTag(position);
                    holder.txt_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                int pos = Integer.parseInt(v.getTag().toString());
                                JSONObject data = moviesList.get(pos);
                                String id = data.getString("id");
                                cancelOrder(id, pos);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    });
                } else {
                    holder.txt_cancel.setVisibility(View.GONE);
                }
            }
//            status    1 approved   0 pending




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

    private void cancelOrder(String id,int pos)
    {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", ira1.savePreferences.reterivePreference(ira1, DefaultConstants.token) + "");
            map.put("id", id);

            map.put("DeviceToken", ira1.getDeviceToken());
            map.put("Version", ira1.getAppVersion());
            map.put("PlatForm", "android");
            map.put("Timestamp", System.currentTimeMillis()+"");

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);
            headerMap.put("Rtoken", ira1.getNewRToken() + "");


            new ServerHandler().sendToServer(ira1, ira1.getApiUrl() + "app-cancel-withdraw", map, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        JSONObject obj = new JSONObject(dta);
                        if (obj.getBoolean("status")) {

                            try
                            {

                                JSONObject data=moviesList.get(pos);
                                data.remove("status");
                                data.put("status","3");
                                data.put("cancelable","0");
                                moviesList.set(pos,data);
                                System.out.println("cancel data==="+data);

                                notifyDataSetChanged();

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }



                        } else {
                            ira1.alertDialogs.alertDialog(ira1, ira1.getResources().getString(R.string.Response), obj.getString("msg"), ira1.getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed)
                                {
                                   ira1.unauthorizedAccess(obj);

                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}


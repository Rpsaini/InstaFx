package com.web.instafx.activity_log;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;
import com.web.instafx.activity_log.adapter.ActivityLogsAdapter;
import com.web.instafx.utilpackage.UtilClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityLogScreens extends BaseActivity {
    private ImageView backIC;
    private RecyclerView activityLogsRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_screen);
        initiateObj();
        initView();
        setOnClickListener();
        getActivityLogDetails("0");
    }

    private void initView(){
        backIC=findViewById(R.id.backIC);
        activityLogsRV=findViewById(R.id.activity_logs_rv);
    }

    private void setAdapterData(JSONArray dataAr){
        ActivityLogsAdapter mAdapter = new ActivityLogsAdapter(ActivityLogScreens.this,dataAr);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(ActivityLogScreens.this, LinearLayoutManager.VERTICAL, false);
        activityLogsRV.setLayoutManager(horizontalLayoutManager);
        activityLogsRV.setItemAnimator(new DefaultItemAnimator());
        activityLogsRV.setAdapter(mAdapter);
    }
    private void setOnClickListener(){
        backIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getActivityLogDetails(String page)
    {
        try {
            Map<String, String> m = new LinkedHashMap<>();
            m.put("token", savePreferences.reterivePreference(ActivityLogScreens.this, DefaultConstants.token) + "");
            m.put("Version", getAppVersion());
            m.put("PlatForm", "android");
            m.put("Timestamp", System.currentTimeMillis() + "");
            m.put("DeviceToken", getDeviceToken() + "");
            m.put("page", page);
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-API-KEY", UtilClass.xApiKey);
            Log.e("OrderDetails","request param::"+m);

            new ServerHandler().sendToServer(ActivityLogScreens.this, getApiUrl() + "get-all-activities", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {
                        Log.e("OrderDetails","OrderDetails::"+dta);


                /*
                    {"type":"Placed Order","description":"User company has placed Limit order of volume 17 @price 166.35326898 Vide order id 4096500","ip":"13.127.217.37","date":"2021-11-11 19:58:19"}

                 */

                        JSONObject obj = new JSONObject(dta);
                        if (obj.getBoolean("status")) {
                            try {
                              //  JSONObject detailObj = new JSONObject(obj.getString("order_detail"));
                                setAdapterData(obj.getJSONArray("activites"));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            alertDialogs.alertDialog(ActivityLogScreens.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed)
                                {
                                    unauthorizedAccess(obj);

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

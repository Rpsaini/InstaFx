//package com.example.whatashot.pairdetailfragments;
//
//import android.app.Dialog;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.app.dialogsnpickers.DialogCallBacks;
//import com.app.dialogsnpickers.SimpleDialog;
//import com.app.vollycommunicationlib.CallBack;
//import com.app.vollycommunicationlib.ServerHandler;
//import com.example.whatashot.DefaultConstants;
//import com.example.whatashot.LoginActivity;
//import com.example.whatashot.MainActivity;
//import com.example.whatashot.R;
//import com.example.whatashot.communication.SocketHandlers;
//import com.google.android.gms.vision.text.Line;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import io.socket.emitter.Emitter;
//
//public class BuySellFragment extends Fragment {
//
//    private String pairid = "";
//    public String str_side = "buy";
//    private EditText ed_quantity, ed_amount;
//    private PairDetailView pairDetailView;
//    private Dialog buySellDialog;
//
//    public BuySellFragment() {
//        // Required empty public constructor
//    }
//
//
//    public static BuySellFragment newInstance(String param1, String param2) {
//        BuySellFragment fragment = new BuySellFragment();
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//        pairDetailView = (PairDetailView) getActivity();
//        buysellDialog();
//        init();
//
//        return inflater.inflate(R.layout.fragment_buy_sell, container, false);
//    }
//
//    private void init() {
//        TextView buytab = buySellDialog.findViewById(R.id.tab_buy);
//        TextView selltab = buySellDialog.findViewById(R.id.tab_sell);
//        //  TextView txt_currency_price =view.findViewById(R.id.txt_currency_price);
//        ed_quantity = buySellDialog.findViewById(R.id.ed_quantity);
//        ed_amount = buySellDialog.findViewById(R.id.ed_amount);
//        final TextView txt_buy = buySellDialog.findViewById(R.id.txt_buy);
//        final TextView txt_sell = buySellDialog.findViewById(R.id.txt_sell);
//        final TextView txt_maincurrency = buySellDialog.findViewById(R.id.txt_maincurrency);
//        final TextView txt_subcurrency = buySellDialog.findViewById(R.id.txt_subcurrency);
//
//        txt_maincurrency.setText(pairDetailView.mainPair);
//        txt_subcurrency.setText(pairDetailView.sub_pair);
//
//        txt_buy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                calculateOrderPrice();
//            }
//        });
//
//        txt_sell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                calculateOrderPrice();
//            }
//        });
//        pairDetailView.txt_price.setText(pairDetailView.buy_price);
//        pairDetailView.txt_price.setTextColor(getResources().getColor(R.color.greencolor));
//        ed_amount.setText(pairDetailView.buy_price.replace(",", ""));
//
//        buytab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                str_side = "buy";
//
//                buytab.setAlpha(1f);
//                selltab.setAlpha(.4f);
//                ed_amount.setText(pairDetailView.buy_price.replace(",", ""));
//                pairDetailView.txt_price.setText(pairDetailView.buy_price);
//                pairDetailView.txt_price.setTextColor(getResources().getColor(R.color.greencolor));
//                txt_buy.setVisibility(View.VISIBLE);
//                txt_sell.setVisibility(View.GONE);
//
//            }
//        });
//
//        selltab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                buytab.setAlpha(.4f);
//                selltab.setAlpha(1f);
//                str_side = "sell";
//                ed_amount.setText(pairDetailView.sell_price.replace(",", ""));
//                pairDetailView.txt_price.setText(pairDetailView.sell_price);
//                pairDetailView.txt_price.setTextColor(getResources().getColor(R.color.darkRed));
//                txt_buy.setVisibility(View.GONE);
//                txt_sell.setVisibility(View.VISIBLE);
//
//            }
//        });
//    }
//
//    private void calculateOrderPrice() {
//
//        if (pairDetailView.validationRule.checkEmptyString(ed_amount) == 0) {
//            pairDetailView.alertDialogs.alertDialog(pairDetailView, getResources().getString(R.string.Response), "Enter amount.", getResources().getString(R.string.ok), "", new DialogCallBacks() {
//                @Override
//                public void getDialogEvent(String buttonPressed) {
//                }
//            });
//            return;
//        }
//
//        if (pairDetailView.validationRule.checkEmptyString(ed_quantity) == 0) {
//            pairDetailView.alertDialogs.alertDialog(pairDetailView, getResources().getString(R.string.Response), "Enter Quantity.", getResources().getString(R.string.ok), "", new DialogCallBacks() {
//                @Override
//                public void getDialogEvent(String buttonPressed) {
//                }
//            });
//            return;
//        }
//
//        Map<String, String> m = new HashMap<>();
//        m.put("isherder", "true");
//        m.put("order_type", "limit");
//        m.put("pair_id", pairDetailView.pair_id);
//        m.put("side", str_side);
//        m.put("amount", ed_quantity.getText().toString());
//        m.put("price", ed_amount.getText().toString());
//        m.put("market_type", "exchange");
//        m.put("Version", pairDetailView.getAppVersion());
//        m.put("PlatForm", "android");
//        m.put("Timestamp", System.currentTimeMillis() + "");
//
//
//        m.put(DefaultConstants.r_token, pairDetailView.getNewRToken() + "");
//
//
//        calCulateOrder(m);
//    }
//
//    private void calCulateOrder(Map map) {
//
//        map.put("token", pairDetailView.savePreferences.reterivePreference(pairDetailView, DefaultConstants.token));
//        map.put("DeviceToken", pairDetailView.getDeviceToken() + "");
//        Map<String, String> headerMap = new HashMap<>();
//        headerMap.put("X-API-KEY", "Q4GxNgqgKV9XJyoKHNgs");
//        headerMap.put("Rtoken", pairDetailView.getNewRToken() + "");
//
//
//        new ServerHandler().sendToServer(pairDetailView, pairDetailView.getApiUrl() + "calculate-order-price", map, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
//            @Override
//            public void getRespone(String dta, ArrayList<Object> respons) {
//                try {
//                    System.out.println("Response===" + dta);
//                    JSONObject obj = new JSONObject(dta);
//                    if (obj.getBoolean("status")) {
//
//                        try {
//                            pairDetailView.savePreferences.savePreferencesData(pairDetailView, obj.getString("r_token"), DefaultConstants.r_token);
//                            shoOrderEstimation(obj, map);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    } else {
//                        pairDetailView.alertDialogs.alertDialog(pairDetailView, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
//                            @Override
//                            public void getDialogEvent(String buttonPressed) {
//                            }
//                        });
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//    }
//
//    Dialog showOrderPlacedConfirmDia;
//
//    private void shoOrderEstimation(JSONObject obj, final Map<String, String> m) {
//
//
//        try {
//            showOrderPlacedConfirmDia = new Dialog(pairDetailView);
//            showOrderPlacedConfirmDia.setContentView(R.layout.show_order_place_dialog);
//
//
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            Window window = showOrderPlacedConfirmDia.getWindow();
//            lp.copyFrom(window.getAttributes());
//            showOrderPlacedConfirmDia.setCancelable(true);
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//            window.setAttributes(lp);
//            showOrderPlacedConfirmDia.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            showOrderPlacedConfirmDia.show();
//
//            TextView orderTotalamount = showOrderPlacedConfirmDia.findViewById(R.id.orderTotalamount);
//            TextView orderType = showOrderPlacedConfirmDia.findViewById(R.id.orderType);
//            TextView orderPrice = showOrderPlacedConfirmDia.findViewById(R.id.orderPrice);
//            TextView orderVolume = showOrderPlacedConfirmDia.findViewById(R.id.orderVolume);
//            TextView orderSide = showOrderPlacedConfirmDia.findViewById(R.id.orderSide);
//
//
//            TextView orderConfirm = showOrderPlacedConfirmDia.findViewById(R.id.orderConfirm);
//            TextView orderCancel = showOrderPlacedConfirmDia.findViewById(R.id.orderCancel);
//
//
//            orderTotalamount.setText(obj.getString("total_price"));
//            orderType.setText(obj.getString("type"));
//            orderPrice.setText(obj.getString("price"));
//            orderVolume.setText(obj.getString("volume"));
//            orderSide.setText(obj.getString("side"));
//
//            orderConfirm.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    placeBuySellOrder(m);
//                }
//            });
//
//            orderCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showOrderPlacedConfirmDia.dismiss();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void placeBuySellOrder(Map<String, String> m) {
//        Map<String, String> headerMap = new HashMap<>();
//        headerMap.put("X-API-KEY", "Q4GxNgqgKV9XJyoKHNgs");
//        headerMap.put("Rtoken", pairDetailView.getNewRToken() + "");
//
//
//        System.out.println("Map params==" + m);
//        System.out.println("Map header==" + headerMap);
//
//        new ServerHandler().sendToServer(pairDetailView, pairDetailView.getApiUrl() + "place-order", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
//            @Override
//            public void getRespone(String dta, ArrayList<Object> respons) {
//                try {
//                    System.out.println("Response===" + dta);
//                    JSONObject obj = new JSONObject(dta);
//                    if (obj.getBoolean("status")) {
//
//                        pairDetailView.alertDialogs.alertDialog(pairDetailView, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
//                            @Override
//                            public void getDialogEvent(String buttonPressed) {
//                                showOrderPlacedConfirmDia.dismiss();
//                                pairDetailView.downSourceDestinationView(ll_buysell, buySellDialog);
//                                sendBroadCastWhaenOrderPlaced(m.get("pair_id"));
//
//                            }
//                        });
//
//                    } else {
//                        pairDetailView.alertDialogs.alertDialog(pairDetailView, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
//                            @Override
//                            public void getDialogEvent(String buttonPressed) {
//                            }
//                        });
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//
//    }
//
//    private void sendBroadCastWhaenOrderPlaced(String pairId) {
//        SocketHandlers socketHandlers = new SocketHandlers();
//        socketHandlers.createConnection();
//
//        Map<String, String> map = new HashMap<>();
//        map.put("pair_id", pairId);
//        System.out.println("Paird===" + pairId);
//        socketHandlers.socket.emit("broadcast_sent_server", map);
//
//    }
//
//     LinearLayout ll_buysell;
//    private void buysellDialog() {
//        try {
//
//            pairDetailView.hideKeyboard(pairDetailView);
//            SimpleDialog simpleDialog = new SimpleDialog();
//            buySellDialog = simpleDialog.simpleDailog(pairDetailView, R.layout.buy_slle_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
//
//
//            final RelativeLayout rr_main_buysell = buySellDialog.findViewById(R.id.rr_main_buysell);
//             ll_buysell = buySellDialog.findViewById(R.id.ll_buysell);
//
//            pairDetailView.animateUp(ll_buysell);
//
//
//            rr_main_buysell.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    pairDetailView.downSourceDestinationView(ll_buysell, buySellDialog);
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//}
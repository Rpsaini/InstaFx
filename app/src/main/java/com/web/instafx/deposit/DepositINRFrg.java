package com.web.instafx.deposit;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class DepositINRFrg extends Fragment {

 private View view;
    private TextView txt_important_note, txt_deposit_code, submit;

    private EditText ed_amount, ed_remarks;
    DepositeInrActivity depositeInrActivity;
    public DepositINRFrg() {
        // Required empty public constructor
    }


    public static DepositINRFrg newInstance(String param1, String param2) {
        DepositINRFrg fragment = new DepositINRFrg();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_deposit_i_n_r_frg, container, false);
        depositeInrActivity=(DepositeInrActivity)getActivity();
        init();
        actions();
        setData();
        generateSixDigitRandomNumber();
        return view;
    }

    private void init() {
        txt_important_note = view.findViewById(R.id.txt_important_note);
        txt_deposit_code = view.findViewById(R.id.txt_deposite_code);
        submit = view.findViewById(R.id.submit);
        ed_remarks = view.findViewById(R.id.ed_remarks);
        ed_amount = view.findViewById(R.id.ed_amount);


    }

    private void actions() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (depositeInrActivity.validationRule.checkEmptyString(ed_amount) == 0) {
                    depositeInrActivity.alertDialogs.alertDialog(depositeInrActivity, getResources().getString(R.string.app_name), "Enter Deposit Amount.", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                        }
                    });
                    return;
                }
                if (depositeInrActivity.validationRule.checkEmptyString(ed_remarks) == 0) {
                    depositeInrActivity.alertDialogs.alertDialog(depositeInrActivity, getResources().getString(R.string.app_name), "Enter Remark.", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {
                        }
                    });
                    return;
                }
                sendForDeposit();

            }
        });


        txt_deposit_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depositeInrActivity.copyCode(txt_deposit_code.getText().toString());
            }
        });

    }

    private void setData() {
        String content = "<p><strong>Important Notes</strong></p>\n" +
                "<p>Please Provide your Deposit Code in the bank slip - remarks field to recognize your Deposit.</p>\n" +
                "<p>When you add your deposit code belonging to your account to the description section  while making a transfer, your investments are automatically transferred to your account.</p>\n" +
                "<p>You must make transfers from your individual deposit accounts opened in your name. Investments made from accounts belonging to a different person are not accepted.</p>\n" +
                "<p>All transfers (with / without card) using ATM will not be accepted as it is not possible to confirm the sender information.</p>\n" +
                "<p>The amount you send will be automatically reflected in your account after the security checks.</p>\n" +
                "<p>No other action from you is needed.During the transfer, you must write the deposit code determined specifically for you in the description field.Transfers made without deposit code will be refunded by deducting the transaction fee.</p>";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txt_important_note.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
        } else {
            txt_important_note.setText(Html.fromHtml(content));
        }
    }

    private void generateSixDigitRandomNumber() {
        Random r = new Random();
        int numbers = 100000 + (int) (r.nextFloat() * 899900);
        txt_deposit_code.setText(numbers + "");
    }


    private void sendForDeposit() {
        try {
            final Map<String, String> m = new HashMap<>();
            m.put("token", depositeInrActivity.savePreferences.reterivePreference(depositeInrActivity, DefaultConstants.token) + "");
            m.put("DeviceToken", depositeInrActivity.getDeviceToken() + "");
            m.put("currency", "INR");
            m.put("amount", ed_amount.getText().toString());
            m.put("reference", txt_deposit_code.getText().toString());
            m.put("remarks", ed_remarks.getText().toString());


            final Map<String, String> obj = new HashMap<>();
            obj.put("X-API-KEY", depositeInrActivity.getXapiKey());
            obj.put("Rtoken", depositeInrActivity.getNewRToken() + "");

            System.out.println("Deposit==="+m);


            new ServerHandler().sendToServer(depositeInrActivity, depositeInrActivity.getApiUrl() + "app-fiat-deposit", m, 0, obj, 20000, R.layout.progressbar, new CallBack() {
                @Override
                public void getRespone(String dta, ArrayList<Object> respons) {
                    try {

                        System.out.println("Data==="+depositeInrActivity.getApiUrl() + "app-fiat-deposit");
                        JSONObject jsonObject = new JSONObject(dta);
                        if (jsonObject.getBoolean("status")) {
                            depositeInrActivity.alertDialogs.alertDialog(depositeInrActivity, getResources().getString(R.string.Response), jsonObject.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed) {
                                    depositeInrActivity.finish();
                                }
                            });
                        } else {
                            depositeInrActivity.alertDialogs.alertDialog(depositeInrActivity, getResources().getString(R.string.Response), jsonObject.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                                @Override
                                public void getDialogEvent(String buttonPressed) {
                                    depositeInrActivity.unauthorizedAccess(jsonObject);
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
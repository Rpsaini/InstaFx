package com.web.instafx.forgot_pwd

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.app.dialogsnpickers.DialogCallBacks
import com.web.instafx.BaseActivity
import com.web.instafx.DefaultConstants

import com.web.instafx.R
import com.web.instafx.utilpackage.UtilClass
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class EnterOTP : BaseActivity() {

    lateinit var otpArray: ArrayList<EditText>;
    lateinit var userID: String;
    lateinit var OTP: String;
    lateinit var email: String;
    lateinit var RRsignuptoplayout: LinearLayout;
    lateinit var tv_submit: TextView;
    lateinit var txt_resendOtp: TextView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_o_t_p)
        RRsignuptoplayout = findViewById(R.id.RRsignuptoplayout);
        tv_submit = findViewById(R.id.tv_submit);
        txt_resendOtp = findViewById(R.id.txt_resendOtp);
        initiateObj()

        OTP = intent.getStringExtra(UtilClass.otp).toString();
        email = intent.getStringExtra(UtilClass.email).toString();
        userID = intent?.getStringExtra(UtilClass.user_id).toString();
        println("entered otp====" + "===" + userID)
        init();
        toolBarInit()
    }

    private fun toolBarInit() {
        val back = findViewById<ImageView>(R.id.backIC)
        back.setOnClickListener { v: View? -> onBackPressed() }

    }
    fun init() {

        otpArray = ArrayList();
        otpArray.add(findViewById<EditText>(R.id.txt_otpone))
        otpArray.add(findViewById<EditText>(R.id.txt_otptwo))
        otpArray.add(findViewById<EditText>(R.id.txt_otpthree))
        otpArray.add(findViewById<EditText>(R.id.txt_otpfour))


        for (i in 0..3) {

            otpArray.get(i).setTag(i);

            otpArray.get(i).setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        var tag = otpArray.get(i).getTag().toString();
                        val index: Int? = tag.toInt()

                        if (index != null) {
                            println("length===" + index)
                            if (index > 0) {
                                otpArray.get(i).setText("");
                                otpArray.get(i - 1).requestFocus()
                            }
                        }
                    }
                    return false
                }
            })

            otpArray.get(i).addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {


                    var tag = otpArray.get(i).getTag().toString();
                    val index: Int? = tag.toInt()

                    if (index != null) {
                        println("length===" + index)
                        if (index < 3) {
                            if (s?.length!! > 0) {

                                otpArray.get(i + 1).requestFocus()
                            }
                        }
                    }

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })


        }


        tv_submit.setOnClickListener(View.OnClickListener {
            addClickEventEffet(tv_submit)
            var enteredOtp: String;
            enteredOtp = "";

            for (i in 0..3) {
                enteredOtp = enteredOtp.plus(otpArray.get(i).text.toString());
            }
            println("entered otp====" + enteredOtp + "===" + OTP)

            if (enteredOtp.equals(OTP)) {
                val intent = Intent(this@EnterOTP, RecoverPassword::class.java)
                intent.putExtra(UtilClass.otp, OTP)
                intent.putExtra(UtilClass.user_id,userID)
                intent.putExtra(UtilClass.email, email)
                startActivityForResult(intent, 1001)
            } else {
                alertDialogs.alertDialog(this, resources.getString(R.string.Invalid), resources.getString(R.string.invalidOtp), resources.getString(R.string.ok), "", DialogCallBacks { })
                for (i in 0..3) {
                    otpArray.get(i).setText("")
                }

            }

        })

        txt_resendOtp.setOnClickListener(View.OnClickListener {
            addClickEventEffet(txt_resendOtp)
            resendOtp(email);
        })


    }


    open fun resendOtp(email: String) {
        try {
            val m: MutableMap<String, String> = HashMap()
            m["email"] = email
            m["token"] = savePreferences.reterivePreference(this@EnterOTP, DefaultConstants.token).toString() + ""
            m["Version"] = appVersion
            m["PlatForm"] = "android"
            m["Timestamp"] = System.currentTimeMillis().toString() + ""
            m["DeviceToken"] = deviceToken + ""

            val headerMap: MutableMap<String, String> = HashMap()
            headerMap["X-API-KEY"] = UtilClass.xApiKey
            Log.e("OrderDetails", "request param::$m")

            serverHandler.sendToServer(this, getApiUrl() + "forgot-password-otp", m, 0, headerMap, 20000, R.layout.progressbar) { dta, respons ->
                try {
                    val jsonObject = JSONObject(dta)
                    if (jsonObject.getBoolean("status")) {
                        OTP = jsonObject.getString("otp");
                    } else {
                        alertDialogs.alertDialog(this@EnterOTP, resources.getString(R.string.Response), jsonObject.getString("msg"), resources.getString(R.string.ok), "") { }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            var intent = Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}






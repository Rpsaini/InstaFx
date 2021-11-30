package com.web.instafx.forgot_pwd

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.app.dialogsnpickers.DialogCallBacks
import com.web.instafx.BaseActivity
import com.web.instafx.R
import com.web.instafx.utilpackage.UtilClass

import org.json.JSONObject

class RecoverPassword : BaseActivity() {
    lateinit var userID: String;
    lateinit var email: String;
    lateinit var Otp: String;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recover_password)
        supportActionBar?.hide();
        initiateObj()
        init();
        toolBarInit();
    }
    private fun toolBarInit() {
        val toolbar_back_arrow = findViewById<ImageView>(R.id.backIC)
        toolbar_back_arrow.setOnClickListener { v: View? -> onBackPressed() }

    }
    fun init() {
        var et_password = findViewById<EditText>(R.id.et_password);
        var et_conf_password = findViewById<EditText>(R.id.et_conf_password);
        var tv_submit = findViewById<TextView>(R.id.tv_submit);
        var img_eye_password = findViewById<ImageView>(R.id.img_eye_password);
        var img_eye_conf_password = findViewById<ImageView>(R.id.img_eye_conf_password);
        val root = findViewById<LinearLayout>(R.id.ll_top)
        root.setOnClickListener { hideKeyboard(this@RecoverPassword) }
        Otp = intent?.getStringExtra(UtilClass.otp).toString();
        email = intent?.getStringExtra(UtilClass.email).toString();
        userID = intent?.getStringExtra(UtilClass.user_id).toString();
        println("entered otp====" + "===" + userID)

        img_eye_password.setTag("0")
        img_eye_password.setOnClickListener(View.OnClickListener { v ->
            if (v.tag == "0") {
                img_eye_password.setTag("1")
                hideShowPassword(1, et_password,img_eye_password)
            } else {
                img_eye_password.setTag("0")
                hideShowPassword(0, et_password,img_eye_password)
            }
        })

        img_eye_conf_password.setTag("0")
        img_eye_conf_password.setOnClickListener(View.OnClickListener { v ->
            if (v.tag == "0") {
                img_eye_conf_password.setTag("1")
                hideShowPassword(1, et_conf_password,img_eye_conf_password)
            } else {
                img_eye_conf_password.setTag("0")
                hideShowPassword(0, et_conf_password,img_eye_conf_password)
            }
        })


        tv_submit.setOnClickListener(View.OnClickListener {
            addClickEventEffet(tv_submit)
            if (et_password.text.toString().length == 0) {
                alertDialogs.alertDialog(this, resources.getString(R.string.Required), resources.getString(R.string.enter_password), resources.getString(R.string.ok), "", DialogCallBacks { })
            } else if (et_conf_password.text.toString().length == 0) {
                alertDialogs.alertDialog(this, resources.getString(R.string.Required), resources.getString(R.string.enter_your_new_password), resources.getString(R.string.ok), "", DialogCallBacks { })
            } else if (!et_conf_password.text.toString().equals(et_password.text.toString())) {
                alertDialogs.alertDialog(this, resources.getString(R.string.Invalid), resources.getString(R.string.password_invalid), resources.getString(R.string.ok), "", DialogCallBacks { })
            } else {
                callResetPassword(et_password.text.toString(), et_conf_password.text.toString());
            }

        })

    }

    private fun callResetPassword(password: String, confirmPass: String) {

        var map = LinkedHashMap<String, String>();
        map["new_password"] = password;
        map["confirm_password"] = confirmPass;
        map["user_id"] =userID
        map["otp"] = Otp;
        map["Version"] = appVersion
        map["PlatForm"] = "android"
        map["Timestamp"] = System.currentTimeMillis().toString() + ""
        map["DeviceToken"] = deviceToken + ""

        val headerMap: MutableMap<String, String> = java.util.HashMap()
        headerMap["X-API-KEY"] = UtilClass.xApiKey
        Log.e("OrderDetails", "request param::$map")

        System.out.println("Before---"+map)

        serverHandler.sendToServer(this, getApiUrl() + "verify-forgot-password-otp",  map, 0, headerMap, 20000, R.layout.progressbar) { dta, respons ->
            try {
                val jsonObject = JSONObject(dta)
                if (jsonObject.getBoolean("status"))
                {
                    alertDialogs.alertDialog(this@RecoverPassword, resources.getString(R.string.Response), jsonObject.getString("msg"), resources.getString(R.string.ok), "") {
                        var intent= Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    alertDialogs.alertDialog(this@RecoverPassword, resources.getString(R.string.Response), jsonObject.getString("msg"), resources.getString(R.string.ok), "") { }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }


    }
}

package com.web.instafx.googleauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.app.dialogsnpickers.DialogCallBacks;

import com.web.instafx.BaseActivity;
import com.web.instafx.DefaultConstants;
import com.web.instafx.R;

public class PasteBackupKey extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paste_backup_key);
        getSupportActionBar().hide();
        initiateObj();
        init();
    }
    private void init()
    {
        final EditText ed_backupkey =findViewById(R.id.ed_backupkey);
        findViewById(R.id.txt_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(ed_backupkey.getText().toString().equals(getIntent().getStringExtra(DefaultConstants.google_sceret_code))) {
                    Intent intent = new Intent(PasteBackupKey.this, SetUpKey.class);
                    intent.putExtra(DefaultConstants.google_sceret_code, getIntent().getStringExtra(DefaultConstants.google_sceret_code));
                    intent.putExtra(DefaultConstants.status, getIntent().getStringExtra(DefaultConstants.status));
                    startActivityForResult(intent, 1001);
                }
                else
                {
                    alertDialogs.alertDialog(PasteBackupKey.this, getResources().getString(R.string.app_name), "Invalid Secret key entered.", "Ok", "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {
                        }
                    });
                }
            }});

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        findViewById(R.id.txt_pastekey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_backupkey.setText(pasteClipboardData());

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            if(requestCode==1001)
            {
                Intent intent=new Intent();
                intent.putExtra("data","setup");
                setResult(RESULT_OK,intent);
                finish();
            }

        }

    }
}
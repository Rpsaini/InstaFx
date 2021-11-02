package com.web.instafx.googleauthentication.googleauthslider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.web.instafx.R;
import com.web.instafx.googleauthentication.SetUpKey;


public class SetUpCodeFirstScreen extends Fragment {

    private View view;
    private SetUpKey setUpKey;
    public SetUpCodeFirstScreen() {
        // Required empty public constructor
    }

    public static SetUpCodeFirstScreen newInstance(String param1, String param2) {
        SetUpCodeFirstScreen fragment = new SetUpCodeFirstScreen();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_set_up_code_first_screen, container, false);
        setUpKey=(SetUpKey) getActivity();

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpKey.finish();
            }
        });

        return view;
    }

//    private void init()
//    {
//        view.findViewById(R.id.txt_download).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                downloadandinstall.openExternalUrls("https://play.google.com/store/apps/details?id=com.google.android.apps.authenticator2");
//                Intent intent=new Intent(downloadandinstall, BackUpKey.class);
//                startActivityForResult(intent,1001);
//            }
//        });
//
//        downloadandinstall.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                downloadandinstall.finish();
//            }
//        });
//
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            if(requestCode==1001)
            {
                Intent intent=new Intent();
                intent.putExtra("data","setup");
                setUpKey. setResult(setUpKey.RESULT_OK,intent);
                setUpKey. finish();
            }

        }

    }
}
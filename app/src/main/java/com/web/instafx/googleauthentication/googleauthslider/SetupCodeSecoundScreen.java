package com.web.instafx.googleauthentication.googleauthslider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.web.instafx.R;
import com.web.instafx.googleauthentication.SetUpKey;


public class SetupCodeSecoundScreen extends Fragment {
  private com.web.instafx.googleauthentication.SetUpKey SetUpKey;
 private View view;

    public SetupCodeSecoundScreen() {
        // Required empty public constructor
    }


    public static SetupCodeSecoundScreen newInstance(String param1, String param2) {
        SetupCodeSecoundScreen fragment = new SetupCodeSecoundScreen();
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
        SetUpKey=(SetUpKey)getActivity();
        view=inflater.inflate(R.layout.fragment_setup_code_secound_screen, container, false);
        return view;
    }
}
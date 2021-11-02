package com.web.instafx.googleauthentication.googleauthslider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.web.instafx.R;

public class SetupcodeFourthScreen extends Fragment {


    public SetupcodeFourthScreen() {
        // Required empty public constructor
    }


    public static SetupcodeFourthScreen newInstance(String param1, String param2) {
        SetupcodeFourthScreen fragment = new SetupcodeFourthScreen();
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
        return inflater.inflate(R.layout.fragment_setupcode_fourth_screen, container, false);
    }
}
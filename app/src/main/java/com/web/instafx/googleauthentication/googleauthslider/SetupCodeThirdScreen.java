package com.web.instafx.googleauthentication.googleauthslider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.web.instafx.R;


public class SetupCodeThirdScreen extends Fragment {


    public SetupCodeThirdScreen() {
        // Required empty public constructor
    }


    public static SetupCodeThirdScreen newInstance(String param1, String param2) {
        SetupCodeThirdScreen fragment = new SetupCodeThirdScreen();

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
        return inflater.inflate(R.layout.fragment_setup_code_third_screen, container, false);
    }
}
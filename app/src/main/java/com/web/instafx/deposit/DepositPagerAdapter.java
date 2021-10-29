package com.web.instafx.deposit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.web.instafx.fragments.CommonFragment;



public class DepositPagerAdapter extends FragmentStatePagerAdapter {


    int mNumOfTabs;
    public DepositPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;


    }
    @Override
    public Fragment getItem(int position) {

        if(position==0)
        {
            DepositINRFrg tab1 = new DepositINRFrg();
            Bundle bundle=new Bundle();
            bundle.putString("pos",position+"");
            tab1.setArguments(bundle);
            return tab1;

        }
        else
        {
            DepositByRazorPayFrg tab1 = new DepositByRazorPayFrg();
            Bundle bundle=new Bundle();
            bundle.putString("pos",position+"");
            tab1.setArguments(bundle);
            return tab1;

        }


    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
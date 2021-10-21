package com.web.instafx.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.web.instafx.pairdetailfragments.PairHistoryFragment;
import com.web.instafx.pairdetailfragments.PairOpenOrderFragment;


public class PairOrderAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PairOrderAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PairOpenOrderFragment tab1 = new PairOpenOrderFragment();
                return tab1;
            case 1:
                PairHistoryFragment tab2 = new PairHistoryFragment();
                return tab2;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
package com.web.instafx.googleauthentication.googleauthslider;

//public class GoogleAuthSliderPageAdapter {
//}


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class GoogleAuthSliderPageAdapter extends FragmentStatePagerAdapter {


    int mNumOfTabs;

    public GoogleAuthSliderPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SetUpCodeFirstScreen tab1 = new SetUpCodeFirstScreen();
                return tab1;
            case 1:
                SetupCodeSecoundScreen tab2 = new SetupCodeSecoundScreen();
                return tab2;

            case 2:
                SetupCodeThirdScreen tab3 = new SetupCodeThirdScreen();
                return tab3;
            case 3:
                SetupcodeFourthScreen tab4 = new SetupcodeFourthScreen();
                return tab4;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

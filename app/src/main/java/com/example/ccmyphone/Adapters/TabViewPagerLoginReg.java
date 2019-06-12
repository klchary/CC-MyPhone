package com.example.ccmyphone.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ccmyphone.LoginRegActFrag.LoginFragment;
import com.example.ccmyphone.R;
import com.example.ccmyphone.LoginRegActFrag.RegisterFragment;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public class TabViewPagerLoginReg extends FragmentStatePagerAdapter {

//    extends SmartFragmentStatePagerAdapter
//    FirstFragment fragment = (FirstFragment) adapterViewPager.getRegisteredFragment(0);
//    adapterViewPager.getRegisteredFragment(vpPager.getCurrentItem());

    private int tabCount;
    private Context mContext;

    public TabViewPagerLoginReg(FragmentManager fm, int tabCount, Context mContext) {
        super(fm);
        this.tabCount = tabCount;
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
            default:
                return new LoginFragment();
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.LoginFragment);
            case 1:
                return mContext.getString(R.string.RegisterFragment);
            default:
                return null;
        }
    }
}

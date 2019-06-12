package com.example.ccmyphone.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ccmyphone.TotaliserFragments.ConverterFragment;
import com.example.ccmyphone.DeviceInfoFragments.GeneralFragment;
import com.example.ccmyphone.TotaliserFragments.PercentageFragment;
import com.example.ccmyphone.R;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public class TabViewPagerTotaliser extends FragmentStatePagerAdapter {

//    extends SmartFragmentStatePagerAdapter
//    FirstFragment fragment = (FirstFragment) adapterViewPager.getRegisteredFragment(0);
//    adapterViewPager.getRegisteredFragment(vpPager.getCurrentItem());

    private int tabCount;
    private Context mContext;

    public TabViewPagerTotaliser(FragmentManager fm, int tabCount, Context mContext) {
        super(fm);
        this.tabCount = tabCount;
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new PercentageFragment();
            case 1:
                return new ConverterFragment();
//            case 2:
//                return new GeneralCalculatorFragment();
            default:
                return new GeneralFragment();
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
                return mContext.getString(R.string.PercentageFragmentTab);
            case 1:
                return mContext.getString(R.string.ConverterFragmentTab);
//            case 2:
//                return mContext.getString(R.string.GeneralCalculatorFragmentTab);
            default:
                return null;
        }
    }
}

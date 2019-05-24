package com.example.ccmyphone.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ccmyphone.APPsFragment;
import com.example.ccmyphone.BatteryFragment;
import com.example.ccmyphone.GeneralFragment;
import com.example.ccmyphone.MemoryFragment;
import com.example.ccmyphone.NetworkFragment;
import com.example.ccmyphone.R;
import com.example.ccmyphone.TorchFragment;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public class TabViewPagerOriginal extends FragmentStatePagerAdapter {

//    extends SmartFragmentStatePagerAdapter
//    FirstFragment fragment = (FirstFragment) adapterViewPager.getRegisteredFragment(0);
//    adapterViewPager.getRegisteredFragment(vpPager.getCurrentItem());

    private int tabCount;
    private Context mContext;

    public TabViewPagerOriginal(FragmentManager fm, int tabCount, Context mContext) {
        super(fm);
        this.tabCount = tabCount;
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new TorchFragment();
//            case 1:
//                return new MemoryFragment();
//            case 2:
//                return new BatteryFragment();
//            case 3:
//                return new NetworkFragment();
//            case 4:
//                return new APPsFragment();
            default:
                return new TorchFragment();
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
                return mContext.getString(R.string.TorchFragmentTab);
//            case 1:
//                return mContext.getString(R.string.MemoryFragmentTab);
//            case 2:
//                return mContext.getString(R.string.BatteryFragmentTab);
//            case 3:
//                return mContext.getString(R.string.NetworkFragmentTab);
//            case 4:
//                return mContext.getString(R.string.APPsFragmentTab);
            default:
                return null;
        }
    }
}

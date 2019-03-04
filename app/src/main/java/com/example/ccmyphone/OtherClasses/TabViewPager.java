package com.example.ccmyphone.OtherClasses;

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

public class TabViewPager extends FragmentStatePagerAdapter {

//    extends SmartFragmentStatePagerAdapter
//    FirstFragment fragment = (FirstFragment) adapterViewPager.getRegisteredFragment(0);
//    adapterViewPager.getRegisteredFragment(vpPager.getCurrentItem());

    private int tabCount;
    private Context mContext;

    public TabViewPager(FragmentManager fm, int tabCount, Context mContext) {
        super(fm);
        this.tabCount = tabCount;
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new GeneralFragment();
            case 1:
                return new MemoryFragment();
            case 2:
                return new BatteryFragment();
            case 3:
                return new NetworkFragment();
            case 4:
                return new APPsFragment();
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
                return mContext.getString(R.string.GeneralFragmentTab);
            case 1:
                return mContext.getString(R.string.MemoryFragmentTab);
            case 2:
                return mContext.getString(R.string.BatteryFragmentTab);
            case 3:
                return mContext.getString(R.string.NetworkFragmentTab);
            case 4:
                return mContext.getString(R.string.APPsFragmentTab);
            default:
                return null;
        }
    }
}

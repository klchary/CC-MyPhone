package com.example.ccmyphone;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ccmyphone.Adapters.TabViewPagerOriginal;
import com.example.ccmyphone.OriginalFragments.TorchFragment;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public class OriginalActivity extends AppCompatActivity {

    static boolean originalActive = false;
    public static DrawerLayout originalDrawerLayout;
    FragmentManager fragmentManager;

    private TabLayout tabLayout;
    public static ViewPager viewPagerOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPagerOriginal = (ViewPager) findViewById(R.id.viewPager);
        originalDrawerLayout = findViewById(R.id.originalDrawerLayout);

        tabLayout.addTab(tabLayout.newTab());

        TabViewPagerOriginal tabViewPager = new TabViewPagerOriginal(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPagerOriginal.setAdapter(tabViewPager);
        tabLayout.setupWithViewPager(viewPagerOriginal);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerOriginal.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPagerOriginal.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(DeviceInfoActivity.this, "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    public void LoadTorchFragment() {
        fragmentManager = getSupportFragmentManager();
        TorchFragment torchFragment = new TorchFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.beginTransaction().add(R.id.fragmentLayout, torchFragment, "TorchFragment").commit();
        fragmentTransaction.replace(R.id.fragmentLayout, torchFragment, "TorchFragment").addToBackStack(torchFragment.getClass().getName()).commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        originalActive = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        originalActive = false;
    }
}

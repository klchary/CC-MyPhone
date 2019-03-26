package com.example.ccmyphone;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ccmyphone.Adapters.TabViewPagerOriginal;
import com.example.ccmyphone.Adapters.TabViewPagerTotaliser;

public class TotaliserActivity extends AppCompatActivity {

    static boolean totaliserActive = false;
    public static DrawerLayout totaliserDrawerLayout;

    FragmentManager fragmentManager;

    private TabLayout tabLayout;
    public static ViewPager viewPagerTotaliser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totaliser);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPagerTotaliser = (ViewPager) findViewById(R.id.viewPager);
        totaliserDrawerLayout = findViewById(R.id.totaliserDrawerLayout);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        TabViewPagerTotaliser tabViewPager = new TabViewPagerTotaliser(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPagerTotaliser.setAdapter(tabViewPager);
        tabLayout.setupWithViewPager(viewPagerTotaliser);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerTotaliser.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPagerTotaliser.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    public void LoadSimpleCalculatorFragment() {
        fragmentManager = getSupportFragmentManager();
        GeneralCalculatorFragment calculatorFragment = new GeneralCalculatorFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.beginTransaction().add(R.id.fragmentLayout, calculatorFragment, "GeneralCalculatorFragment").commit();
        fragmentTransaction.replace(R.id.fragmentLayout, calculatorFragment, "GeneralCalculatorFragment").addToBackStack(calculatorFragment.getClass().getName()).commit();
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        totaliserActive = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        totaliserActive = false;
    }
}

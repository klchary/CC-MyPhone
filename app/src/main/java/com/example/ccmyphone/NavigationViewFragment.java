package com.example.ccmyphone;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.ccmyphone.DeviceInfoActivity.deviceDrawerLayout;
import static com.example.ccmyphone.DeviceInfoActivity.deviceInfoActive;
import static com.example.ccmyphone.DeviceInfoActivity.viewPager;
import static com.example.ccmyphone.TotaliserActivity.totaliserActive;
import static com.example.ccmyphone.TotaliserActivity.totaliserDrawerLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationViewFragment extends Fragment implements View.OnClickListener {

    ImageView navMobilePic;
    TextView navUserName, navUserMobile, navUserMobileDes;

    // Activity DeviceInfo
    Button navPhoneAct;
    LinearLayout phoneCateLayout;
    Button navGeneral, navMemory, navBattery, navNetwork, navInApps;

    // Activity Totaliser
    Button navTotaliserAct;


    public NavigationViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_view, container, false);

        navPhoneAct = view.findViewById(R.id.navPhoneAct);
        phoneCateLayout = view.findViewById(R.id.phoneCateLayout);
        navGeneral = view.findViewById(R.id.navGeneral);
        navMemory = view.findViewById(R.id.navMemory);
        navBattery = view.findViewById(R.id.navBattery);
        navNetwork = view.findViewById(R.id.navNetwork);
        navInApps = view.findViewById(R.id.navInApps);
        navGeneral.setOnClickListener(this);
        navMemory.setOnClickListener(this);
        navBattery.setOnClickListener(this);
        navNetwork.setOnClickListener(this);
        navInApps.setOnClickListener(this);

        navTotaliserAct = view.findViewById(R.id.navTotaliserAct);

        phoneCateLayout.setVisibility(View.GONE);
        navPhoneAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deviceInfoActive){
                    phoneCateLayout.setVisibility(View.VISIBLE);
                    navPhoneAct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile, 0, R.drawable.ic_minus, 0);
                }else {
                    phoneCateLayout.setVisibility(View.GONE);
                    navPhoneAct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile, 0, R.drawable.ic_plus, 0);
                    Intent intent = new Intent(getActivity(), DeviceInfoActivity.class);
                    startActivity(intent);
                    deviceDrawerLayout.closeDrawers();
                }
            }
        });

        navTotaliserAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (totaliserActive){
//                    phoneCateLayout.setVisibility(View.VISIBLE);
//                    navPhoneAct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile, 0, R.drawable.ic_minus, 0);
//                }else {
//                    phoneCateLayout.setVisibility(View.GONE);
//                    navPhoneAct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile, 0, R.drawable.ic_plus, 0);
//                    Intent intent = new Intent(getActivity(), TotaliserActivity.class);
//                    startActivity(intent);
//                }
                Intent intent = new Intent(getActivity(), TotaliserActivity.class);
                startActivity(intent);
                if (deviceInfoActive){
                    deviceDrawerLayout.closeDrawers();
                } else if (totaliserActive) {
                    totaliserDrawerLayout.closeDrawers();
                }

            }
        });



        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navGeneral:
                viewPager.setCurrentItem(0);
                deviceDrawerLayout.closeDrawers();
                break;
            case R.id.navMemory:
                viewPager.setCurrentItem(1);
                deviceDrawerLayout.closeDrawers();
                break;
            case R.id.navBattery:
                viewPager.setCurrentItem(2);
                deviceDrawerLayout.closeDrawers();
                break;
            case R.id.navNetwork:
                viewPager.setCurrentItem(3);
                deviceDrawerLayout.closeDrawers();
                break;
            case R.id.navInApps:
                viewPager.setCurrentItem(4);
                deviceDrawerLayout.closeDrawers();
                break;
        }
    }
}

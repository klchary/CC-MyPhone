package com.example.ccmyphone;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ccmyphone.Models.UserDetails;
import com.google.gson.Gson;

import static com.example.ccmyphone.ApplicationConstants.SHARED_PERSISTENT_VALUES;
import static com.example.ccmyphone.ApplicationConstants.USER_DETAILS;
import static com.example.ccmyphone.DeviceInfoActivity.deviceDrawerLayout;
import static com.example.ccmyphone.DeviceInfoActivity.deviceInfoActive;
import static com.example.ccmyphone.DeviceInfoActivity.viewPagerDevice;
import static com.example.ccmyphone.OriginalActivity.originalActive;
import static com.example.ccmyphone.OriginalActivity.originalDrawerLayout;
import static com.example.ccmyphone.OriginalActivity.viewPagerOriginal;
import static com.example.ccmyphone.TotaliserActivity.totaliserActive;
import static com.example.ccmyphone.TotaliserActivity.totaliserDrawerLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationViewFragment extends Fragment implements View.OnClickListener {

    String TAG = "NavigationViewFragment";

    ImageView navMobilePic;
    TextView navUserName, navUserMobile, navUserMobileDes;

    // Activity DeviceInfo
    Button navPhoneAct;
    LinearLayout phoneCateLayout;
    Button navGeneral, navMemory, navBattery, navNetwork, navInApps;

    // Activity Totaliser
    Button navTotaliserAct;
    LinearLayout totaliserCateLayout;
    Button navGenCalculator;

    // Activity Original
    Button navOriginalAct;
    LinearLayout originalCateLayout;
    Button navTorch;

    // For all Activities
    SharedPreferences sharedpref;
    String userSharedDetails;
    Gson gson = new Gson();
    UserDetails userDetails;


    public NavigationViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_view, container, false);
        FindAllViews(view);

        navGeneral.setOnClickListener(this);
        navMemory.setOnClickListener(this);
        navBattery.setOnClickListener(this);
        navNetwork.setOnClickListener(this);
        navInApps.setOnClickListener(this);

        sharedpref = getActivity().getSharedPreferences(SHARED_PERSISTENT_VALUES, Context.MODE_PRIVATE);
        userSharedDetails = sharedpref.getString(USER_DETAILS, null);
        if (userSharedDetails != null) {
            userDetails = gson.fromJson(userSharedDetails, UserDetails.class);
        }
        Log.d(TAG, "userDetails " + userDetails);

        if (userDetails != null) {
            navUserName.setText(userDetails.getUserName());
            navUserMobile.setText(userDetails.getUserMobile());
            navUserMobileDes.setText(Build.MODEL);
        } else {
            navUserName.setText("Guest");
            navUserMobile.setText("");
            navUserMobileDes.setText(Build.MODEL);
        }

        phoneCateLayout.setVisibility(View.GONE);
        navPhoneAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deviceInfoActive) {
                    phoneCateLayout.setVisibility(View.VISIBLE);
                    navPhoneAct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile, 0, R.drawable.ic_minus, 0);
                } else {
                    phoneCateLayout.setVisibility(View.GONE);
                    navPhoneAct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile, 0, R.drawable.ic_plus, 0);
                    Intent intent = new Intent(getActivity(), DeviceInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    closerDrawers();
                }
            }
        });

        totaliserCateLayout.setVisibility(View.GONE);
        navTotaliserAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totaliserActive) {
                    totaliserCateLayout.setVisibility(View.VISIBLE);
                    navTotaliserAct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile, 0, R.drawable.ic_minus, 0);
                } else {
                    totaliserCateLayout.setVisibility(View.GONE);
                    navTotaliserAct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile, 0, R.drawable.ic_plus, 0);
                    Intent intent = new Intent(getActivity(), TotaliserActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    closerDrawers();
                }

            }
        });

        originalCateLayout.setVisibility(View.GONE);
        navOriginalAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (originalActive) {
                    originalCateLayout.setVisibility(View.VISIBLE);
                    navOriginalAct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile, 0, R.drawable.ic_minus, 0);
                } else {
                    originalCateLayout.setVisibility(View.GONE);
                    navOriginalAct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile, 0, R.drawable.ic_plus, 0);
                    Intent intent = new Intent(getActivity(), OriginalActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    closerDrawers();
                }

            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navGeneral:
                viewPagerDevice.setCurrentItem(0);
                closerDrawers();
                break;
            case R.id.navMemory:
                viewPagerDevice.setCurrentItem(1);
                closerDrawers();
                break;
            case R.id.navBattery:
                viewPagerDevice.setCurrentItem(2);
                closerDrawers();
                break;
            case R.id.navNetwork:
                viewPagerDevice.setCurrentItem(3);
                closerDrawers();
                break;
            case R.id.navInApps:
                viewPagerDevice.setCurrentItem(4);
                closerDrawers();
                break;


            case R.id.navTorch:
                viewPagerOriginal.setCurrentItem(0);
                closerDrawers();
                break;
        }
    }

    public void closerDrawers() {
        if (deviceInfoActive) {
            deviceDrawerLayout.closeDrawers();
        } else if (totaliserActive) {
            totaliserDrawerLayout.closeDrawers();
        } else if (originalActive) {
            originalDrawerLayout.closeDrawers();
        }
    }

    private void FindAllViews(View view) {
        navPhoneAct = view.findViewById(R.id.navPhoneAct);
        phoneCateLayout = view.findViewById(R.id.phoneCateLayout);
        navGeneral = view.findViewById(R.id.navGeneral);
        navMemory = view.findViewById(R.id.navMemory);
        navBattery = view.findViewById(R.id.navBattery);
        navNetwork = view.findViewById(R.id.navNetwork);
        navInApps = view.findViewById(R.id.navInApps);

        navTotaliserAct = view.findViewById(R.id.navTotaliserAct);
        totaliserCateLayout = view.findViewById(R.id.totaliserCateLayout);
        navGenCalculator = view.findViewById(R.id.navGenCalculator);

        navOriginalAct = view.findViewById(R.id.navOriginalAct);
        originalCateLayout = view.findViewById(R.id.originalCateLayout);
        navTorch = view.findViewById(R.id.navTorch);

        navMobilePic = view.findViewById(R.id.navMobilePic);
        navUserName = view.findViewById(R.id.navUserName);
        navUserMobile = view.findViewById(R.id.navUserMobile);
        navUserMobileDes = view.findViewById(R.id.navUserMobileDes);
    }

}

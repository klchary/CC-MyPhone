package com.example.ccmyphone;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Movie;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.ccmyphone.Adapters.RecyclerViewAdapter;
import com.example.ccmyphone.Models.InfoModel;
import com.example.ccmyphone.OtherClasses.RecyclerTouchListener;
import com.example.ccmyphone.OtherClasses.RecyclerviewDivider;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BatteryFragment extends Fragment {

    private String title;
    private int page;
    public static String TAG = "BatteryFragment";

    TabHost tabHost;

    RecyclerView batteryRecyclerview;
    ArrayList<InfoModel> batteryInfoArray;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerViewAdapter batteryAdapter;

    String bPercentStr, bVoltageStr, bTempStr, bStatusStr, bChargingPlugStr, bHealthStr;



    public BatteryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battery, container, false);

        batteryInfoArray = new ArrayList<InfoModel>();
        batteryRecyclerview = view.findViewById(R.id.recyclervewBattery);

        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onReceive(Context arg0, Intent intent) {
                //this will give you battery current status

                try {
                    int level = intent.getIntExtra("level", 0);
                    float temp = (float) (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
                    int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
                    int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
                    int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                    int BHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
                    int BIcon = intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, 0);
                    int BTech = intent.getIntExtra(BatteryManager.EXTRA_TECHNOLOGY, 0);
                    Log.d(TAG, "Voltage " + voltage);

                    String BatteryStatus = "No Data";
                    if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                        BatteryStatus = "Charging";
                    }
                    if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                        BatteryStatus = "Discharging";
                    }
                    if (status == BatteryManager.BATTERY_STATUS_FULL) {
                        BatteryStatus = "Full";
                    }
                    if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                        BatteryStatus = "Not Charging";
                    }
                    if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                        BatteryStatus = "Unknown";
                    }
                    Log.d(TAG, "BatteryStatus " + BatteryStatus);

                    String BattPowerSource = "";
                    if (chargePlug == BatteryManager.BATTERY_PLUGGED_AC) {
                        BattPowerSource = "AC";
                    }
                    if (chargePlug == BatteryManager.BATTERY_PLUGGED_USB) {
                        BattPowerSource = "USB";
                    }
                    if (chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                        BattPowerSource = "WIRELESS";
                    }
                    Log.d(TAG, "PowerSource " + BattPowerSource);

                    String BatteryHealth = "No Data";
                    if (BHealth == BatteryManager.BATTERY_HEALTH_COLD) {
                        BatteryHealth = "Cold";
                    }
                    if (BHealth == BatteryManager.BATTERY_HEALTH_DEAD) {
                        BatteryHealth = "Dead";
                    }
                    if (BHealth == BatteryManager.BATTERY_HEALTH_GOOD) {
                        BatteryHealth = "Good";
                    }
                    if (BHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                        BatteryHealth = "Over-Voltage";
                    }
                    if (BHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                        BatteryHealth = "Overheat";
                    }
                    if (BHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                        BatteryHealth = "Unknown";
                    }
                    if (BHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                        BatteryHealth = "Unspecified Failure";
                    }
                    Log.d(TAG, "Health " + BatteryHealth);

                    float fullVoltage = (float) (voltage * 0.001);
                    bPercentStr = String.valueOf(level) + "%";
                    bTempStr = temp + "°C";
                    bVoltageStr = fullVoltage + "volt";
                    bHealthStr = BatteryHealth;
                    bStatusStr = BatteryStatus;
                    bChargingPlugStr = BattPowerSource;

                    batteryInfoArray.add(new InfoModel("Battery Level", bPercentStr));
                    batteryInfoArray.add(new InfoModel("Battery Status", bStatusStr));
                    batteryInfoArray.add(new InfoModel("Power Source", bChargingPlugStr));
                    batteryInfoArray.add(new InfoModel("Battery Voltage", bVoltageStr));
                    batteryInfoArray.add(new InfoModel("Battery Temperature", bTempStr));
                    batteryInfoArray.add(new InfoModel("Battery Health", bHealthStr));

                    batteryAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    Log.d(TAG, "Battery Info Error");
                }
            }
        };

        getActivity().registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        batteryInfoArray.add(new InfoModel("Battery Capacity", getBatteryCapacity(getActivity()) + " mAh"));

        batteryAdapter = new RecyclerViewAdapter(batteryInfoArray);
        batteryRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        batteryRecyclerview.setLayoutManager(mLayoutManager);
        batteryRecyclerview.setItemAnimator(new DefaultItemAnimator());
        batteryRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        batteryRecyclerview.setAdapter(batteryAdapter);
        batteryRecyclerview.addItemDecoration(new RecyclerviewDivider(getActivity()));
        batteryAdapter.notifyDataSetChanged();

        batteryRecyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), batteryRecyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                InfoModel infoModel = batteryInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "Click selected!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {
                InfoModel infoModel = batteryInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "LongClick selected!", Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

    public double getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(context);

            batteryCapacity = (double) Class.forName(POWER_PROFILE_CLASS)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return batteryCapacity;
    }

}

package com.example.ccmyphone.DeviceInfoFragments;


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
import com.example.ccmyphone.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public class BatteryFragment extends Fragment {

    private String title;
    private int page;
    public static String TAG = "BatteryFragment";

    TabHost tabHost;

    RecyclerView batteryRecyclerview;
    ArrayList<InfoModel> batteryInfoArray;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerViewAdapter batteryAdapter;

    String bPercentStr = null, bVoltageStr = null, bTempStr = null, bStatusStr = null;
    String bChargingPlugStr = null, bHealthStr = null, bCapacityStr = null;
    int colorText; // 0 = Normal, 1 = Yellow, 2 = Red, 3 = Green
    String bPercentStrT = null, bVoltageStrT = null, bTempStrT = null, bStatusStrT = null;
    String bChargingPlugStrT = null, bHealthStrT = null, bCapacityStrT = null;

    Gson gson = new Gson();
    InfoModel infoModel;
    JSONObject jsonObject = null;
    String[] titles, descriptions, descTexts;


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
//        infoModel = new InfoModel();

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
                        bStatusStrT = "Battery is Charging,is at " + level + "%";
                    } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                        BatteryStatus = "Discharging";
                        bStatusStrT = "Battery is draining continuously,is at " + level + "%";
                    } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
                        BatteryStatus = "Full";
                        bStatusStrT = "Battery is Full,is at " + level + "%";
                    } else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                        BatteryStatus = "Not Charging";
                        bStatusStrT = "Battery is Not Charging,is at " + level + "%";
                    } else if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                        BatteryStatus = "Unknown";
                        bStatusStrT = "Battery status is Unknown,is at " + level + "%";
                    }
                    Log.d(TAG, "BatteryStatus " + BatteryStatus);

                    String BattPowerSource = "";
                    if (chargePlug == BatteryManager.BATTERY_PLUGGED_AC) {
                        BattPowerSource = "AC";
                        bChargingPlugStr = "Battery is Charging, connected with AC charger";
                    } else if (chargePlug == BatteryManager.BATTERY_PLUGGED_USB) {
                        BattPowerSource = "USB";
                        bChargingPlugStr = "Battery is Charging, connected with USB";
                    } else if (chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                        BattPowerSource = "WIRELESS";
                        bChargingPlugStr = "Battery is Charging, connected with WIRELESS";
                    } else {
                        BattPowerSource = "Not connected to Charger";
                        bChargingPlugStr = "";
                    }
                    Log.d(TAG, "PowerSource " + BattPowerSource);

                    String BatteryHealth = "No Data";
                    if (BHealth == BatteryManager.BATTERY_HEALTH_COLD) {
                        BatteryHealth = "Cold";
                        bHealthStrT = "Battery temperature is too Cold";
                    } else if (BHealth == BatteryManager.BATTERY_HEALTH_DEAD) {
                        BatteryHealth = "Dead";
                        bHealthStrT = "Battery is Dead, replace your battery";
                    } else if (BHealth == BatteryManager.BATTERY_HEALTH_GOOD) {
                        BatteryHealth = "Good";
                        bHealthStrT = "Battery condition is Good";
                    } else if (BHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                        BatteryHealth = "Over-Voltage";
                        bHealthStrT = "Battery is Over-Voltage, try to change your Charger";
                    } else if (BHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                        BatteryHealth = "Overheat";
                        bHealthStrT = "Battery is Overheating, try to clear background running apps (or) replace your battery";
                    } else if (BHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                        BatteryHealth = "Unknown";
                        bHealthStrT = "";
                    } else if (BHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                        BatteryHealth = "Unspecified Failure";
                        bHealthStrT = "";
                    }
                    Log.d(TAG, "Health " + BatteryHealth);

                    float fullVoltage = (float) (voltage * 0.001);
                    bCapacityStr = getBatteryCapacity(getActivity()) + " mAh";
                    bPercentStr = String.valueOf(level) + "%";
                    bTempStr = temp + "°C";
                    bVoltageStr = fullVoltage + "volt";
                    bHealthStr = BatteryHealth;
                    bStatusStr = BatteryStatus;
                    bChargingPlugStr = BattPowerSource;

//                    batteryInfoArray.add(new InfoModel("Battery Capacity", bCapacityStr));
//                    batteryInfoArray.add(new InfoModel("Battery Level", bPercentStr));
//                    batteryInfoArray.add(new InfoModel("Battery Status", bStatusStr));
//                    batteryInfoArray.add(new InfoModel("Power Source", bChargingPlugStr));
//                    batteryInfoArray.add(new InfoModel("Battery Voltage", bVoltageStr));
//                    batteryInfoArray.add(new InfoModel("Battery Temperature", bTempStr));
//                    batteryInfoArray.add(new InfoModel("Battery Health", bHealthStr));

                    JSONArray jsonArray = new JSONArray();
                    infoModel = new InfoModel();
                    descTexts = new String[]{bCapacityStrT, bPercentStrT, bStatusStrT, bChargingPlugStrT, bVoltageStrT, bTempStrT, bHealthStrT};
                    descriptions = new String[]{bCapacityStr, bPercentStr, bStatusStr, bChargingPlugStr, bVoltageStr, bTempStr, bHealthStr};
                    titles = new String[]{"Battery Capacity", "Battery Level", "Battery Status",
                            "Power Source", "Battery Voltage", "Battery Temperature", "Battery Health"};
                    for (int i = 0; i < titles.length; i++) {
                        jsonObject = new JSONObject();
                        try {
                            jsonObject.put("infoTitle", titles[i]);
                            jsonObject.put("infoDetail", descriptions[i]);
                            jsonObject.put("infoDetailText", descTexts[i]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArray.put(jsonObject);
                    }
                    for (int j = 0; j < jsonArray.length(); j++) {
                        String jsonString = jsonArray.getJSONObject(j).toString();
                        infoModel = gson.fromJson(jsonString, InfoModel.class);
                        batteryInfoArray.add(infoModel);
                        Log.d(TAG, "jsonJob" + jsonString);
                    }
                    Log.d(TAG, "JSONArray" + jsonArray);
//                    JSONObject finalobject = new JSONObject();
//                    finalobject.put("FullObject", jsonArray);

                    batteryAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    Log.d(TAG, "Battery Info Error");
                }
            }
        };

        getActivity().registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

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

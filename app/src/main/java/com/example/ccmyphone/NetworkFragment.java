package com.example.ccmyphone;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ccmyphone.Adapters.RecyclerViewAdapter;
import com.example.ccmyphone.Models.InfoModel;
import com.example.ccmyphone.OtherClasses.RecyclerTouchListener;
import com.example.ccmyphone.OtherClasses.RecyclerviewDivider;
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

public class NetworkFragment extends Fragment {

    private String title;
    private int page;
    public static final String TAG = "NetworkFragment";

    RecyclerView simRecyclreview;
    ArrayList<InfoModel> simInfoArray;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerViewAdapter simAdapter;

    String simType, simTypeT;

    Gson gson = new Gson();
    InfoModel infoModel;
    JSONObject jsonObject = null;
    String[] titles, descriptions, descTexts;

    String imeiStr0 = null, imeiStr1 = null, defaDevicIdStr = null, simSerialNumStr = null, simNoStr = null, countryIsoStr = null,
            simOperaStr = null, simOperaNameStr = null, softVersionStr = null,
            simStateStr = null, simState0Str = null, simState1Str = null, networkTypeStr = null, dataStateStr = null, dataActivStr = null,
            simNaiStr = null, serviceStateStr = null, signalStrengthStr = null;
    String imeiStr0T = null, imeiStr1T = null, defaDevicIdStrT = null, simSerialNumStrT = null, simNoStrT = null, countryIsoStrT = null,
            simOperaStrT = null, simOperaNameStrT = null, softVersionStrT = null,
            simStateStrT = null, simState0StrT = null, simState1StrT = null, networkTypeStrT = null, dataStateStrT = null, dataActivStrT = null,
            simNaiStrT = null, serviceStateStrT = null, signalStrengthStrT = null;

    public NetworkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_network, container, false);

        simRecyclreview = view.findViewById(R.id.recyclervewSIM);
        simInfoArray = new ArrayList<InfoModel>();

        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            simType = String.valueOf(manager.getPhoneCount());
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            imeiStr0 = manager.getDeviceId(0);
            imeiStr1 = manager.getDeviceId(1);
        }

        simStateStr = String.valueOf(manager.getSimState());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            simState0Str = String.valueOf(manager.getSimState(0));
            simState1Str = String.valueOf(manager.getSimState(1));
            serviceStateStr = String.valueOf(manager.getServiceState());
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            networkTypeStr = String.valueOf(manager.getDataNetworkType());
//        }

        networkTypeStr = getNetworkClass(getActivity());

        dataStateStr = String.valueOf(manager.getDataState());
        dataActivStr = String.valueOf(manager.getDataActivity());
//        signalStrengthStr = String.valueOf(manager.getSignalStrength());

        defaDevicIdStr = manager.getDeviceId();
        simSerialNumStr = manager.getSimSerialNumber();
        simNoStr = manager.getLine1Number();
        countryIsoStr = manager.getSimCountryIso();
        simOperaStr = manager.getSimOperator();
        simOperaNameStr = manager.getSimOperatorName();
        softVersionStr = manager.getDeviceSoftwareVersion();
        simNaiStr = manager.getNai();

        JSONArray jsonArray = new JSONArray();
        infoModel = new InfoModel();
        descTexts = new String[]{imeiStr0T, imeiStr1T, simTypeT, defaDevicIdStrT, simSerialNumStrT, simNoStrT, countryIsoStrT, simOperaStrT,
                simOperaNameStrT, softVersionStrT, simStateStrT, simState0StrT, simState1StrT, networkTypeStrT,
                dataStateStrT, dataActivStrT, simNaiStrT, serviceStateStrT, signalStrengthStrT};
        descriptions = new String[]{imeiStr0, imeiStr1, simType, defaDevicIdStr, simSerialNumStr, simNoStr, countryIsoStr, simOperaStr,
                simOperaNameStr, softVersionStr, simStateStr, simState0Str, simState1Str, networkTypeStr,
                dataStateStr, dataActivStr, simNaiStr, serviceStateStr, signalStrengthStr};
        titles = new String[]{"IMEI 1", "IMEI 2", "SIM Count", "Defualt Device ID", "SIM Serial No.", "SIM No.", "SIM Country ISO",
                "SIM Operator", "SIM Operator Name", "SIM Software Version", "SIM State",
                "SIM State0", "SIM State1", "SIM Data NetworkType", "SIM Data State", "SIM Data Activity", "SIM Nai", "Service State", "Signal Strength"};

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
            String jsonString = null;
            try {
                jsonString = jsonArray.getJSONObject(j).toString();
                infoModel = gson.fromJson(jsonString, InfoModel.class);
                simInfoArray.add(infoModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "jsonJob" + jsonString);
        }
        Log.d(TAG, "JSONArray" + jsonArray);
//        JSONObject finalobject = new JSONObject();
//        finalobject.put("FullObject", jsonArray);

        simAdapter = new RecyclerViewAdapter(simInfoArray);
        simRecyclreview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        simRecyclreview.setLayoutManager(mLayoutManager);
        simRecyclreview.addItemDecoration(new RecyclerviewDivider(getActivity()));
        simRecyclreview.setItemAnimator(new DefaultItemAnimator());
        simRecyclreview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        simRecyclreview.setAdapter(simAdapter);

        simRecyclreview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), simRecyclreview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                InfoModel infoModel = simInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "Click selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                InfoModel infoModel = simInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "LongClick selected!", Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

    public String getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "Unknown";
        }
    }

}

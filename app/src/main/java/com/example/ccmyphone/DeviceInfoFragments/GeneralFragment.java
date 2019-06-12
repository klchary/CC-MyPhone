package com.example.ccmyphone.DeviceInfoFragments;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public class GeneralFragment extends Fragment {

    private String title;
    private int page;
    public static final String TAG = "GeneralFragment";

    RecyclerView generalRecyclreview;
    ArrayList<InfoModel> generalInfoArray;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerViewAdapter generalAdapter;

    static int width;
    static int height;

    Gson gson = new Gson();
    InfoModel infoModel;
    JSONObject jsonObject = null;
    String[] titles, descriptions, descTexts;

    String screenSizeTypeStr = null, screenSizeStr = null, apiLevelStr = null, screenResoStr = null, serialNoStr = null, deviceModelStr = null,
            idNumberStr = null, manufacturerStr = null, brandNameStr = null, typeStr = null, userStr = null, baseVersionStr = null, boardStr = null,
            incrementalStr = null, sdkVersionStr = null, hostStr = null, fingerPrintStr = null, bootloaderStr = null, hardwareStr = null, tagsStr = null,
            displayStr = null, radioVersionStr = null, buildTimeStr = null;
    String screenSizeTypeStrT = null, screenSizeStrT = null, apiLevelStrT = null, screenResoStrT = null, serialNoStrT = null, deviceModelStrT = null,
            idNumberStrT = null, manufacturerStrT = null, brandNameStrT = null, typeStrT = null, userStrT = null, baseVersionStrT = null, boardStrT = null,
            incrementalStrT = null, sdkVersionStrT = null, hostStrT = null, fingerPrintStrT = null, bootloaderStrT = null, hardwareStrT = null, tagsStrT = null,
            displayStrT = null, radioVersionStrT = null, buildTimeStrT = null;

    public GeneralFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general, container, false);

        generalRecyclreview = view.findViewById(R.id.recyclervewGeneral);
        generalInfoArray = new ArrayList<InfoModel>();

        getScreenResolution(getActivity());

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int density = dm.densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                screenSizeTypeStr = "LDPI";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                screenSizeTypeStr = "MDPI";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                screenSizeTypeStr = "HDPI";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                screenSizeTypeStr = "XHDPI";
                break;
        }
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                screenSizeStr = "X - Large Screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                screenSizeStr = "Large Screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                screenSizeStr = "Normal Screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                screenSizeStr = "Small Screen";
                break;
            default:
                Toast.makeText(getActivity(), "Screen size is neither large, normal or small", Toast.LENGTH_LONG).show();

        }

        baseVersionStr = String.valueOf(Build.VERSION_CODES.BASE);
        String BaseVersionCode1 = String.valueOf(Build.VERSION_CODES.BASE_1_1);
        sdkVersionStr = apiLevelStr = String.valueOf(Build.VERSION.SDK_INT);

        screenResoStr = width + " X " + height;
        serialNoStr = Build.SERIAL;
        deviceModelStr = Build.MODEL;
        idNumberStr = Build.ID;
        manufacturerStr = Build.MANUFACTURER;
        brandNameStr = Build.BRAND;
        typeStr = Build.TYPE;
        userStr = Build.USER;
        boardStr = Build.BOARD;
        incrementalStr = Build.VERSION.INCREMENTAL;
        hostStr = Build.HOST;
        fingerPrintStr = Build.FINGERPRINT;
        bootloaderStr = Build.BOOTLOADER;
        hardwareStr = Build.HARDWARE;
        tagsStr = Build.TAGS;
        displayStr = Build.DISPLAY;
        radioVersionStr = Build.getRadioVersion();

        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMM yyyy - hh:mm:ss");
        buildTimeStr = formatter.format(new Date(Build.TIME));

        JSONArray jsonArray = new JSONArray();
        infoModel = new InfoModel();
        descTexts = new String[]{screenSizeTypeStrT, screenSizeStrT, apiLevelStrT, screenResoStrT, serialNoStrT, deviceModelStrT, idNumberStrT,
                manufacturerStrT, brandNameStrT, typeStrT, userStrT, baseVersionStrT, boardStrT, incrementalStrT, sdkVersionStrT, hostStrT, fingerPrintStrT,
                bootloaderStrT, hardwareStrT, tagsStrT, displayStrT, radioVersionStrT, buildTimeStrT};
        descriptions = new String[]{screenSizeTypeStr, screenSizeStr, apiLevelStr, screenResoStr, serialNoStr, deviceModelStr, idNumberStr,
                manufacturerStr, brandNameStr, typeStr, userStr, baseVersionStr, boardStr, incrementalStr, sdkVersionStr, hostStr, fingerPrintStr,
                bootloaderStr, hardwareStr, tagsStr, displayStr, radioVersionStr, buildTimeStr};
        titles = new String[]{"Screen Size Type", "Screen Size", "API Level", "Screen Resolution", "Serial Number", "Device Model",
                "ID Number", "Manufacturer Name", "Brand Name", "Type", "User", "Base Version", "Board", "Incremental", "SDK Version",
                "Host", "FingerPrint", "Bootloader", "Hardware", "Tags", "Display", "Radio Version", "Build Time"};
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
                generalInfoArray.add(infoModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "jsonJob" + jsonString);
        }
        Log.d(TAG, "JSONArray" + jsonArray);
//                    JSONObject finalobject = new JSONObject();
//                    finalobject.put("FullObject", jsonArray);

        generalAdapter = new RecyclerViewAdapter(generalInfoArray);
        generalRecyclreview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        generalRecyclreview.setLayoutManager(mLayoutManager);
        generalRecyclreview.setItemAnimator(new DefaultItemAnimator());
        generalRecyclreview.setAdapter(generalAdapter);
        generalRecyclreview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        generalRecyclreview.addItemDecoration(new RecyclerviewDivider(getActivity()));

        generalRecyclreview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), generalRecyclreview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                InfoModel infoModel = generalInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "Click selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                InfoModel infoModel = generalInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "LongClick selected!", Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

    private String getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        return "{" + width + "," + height + "}";
    }

}

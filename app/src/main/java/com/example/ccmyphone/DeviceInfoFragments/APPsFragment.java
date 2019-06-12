package com.example.ccmyphone.DeviceInfoFragments;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ccmyphone.Adapters.AppListViewAdapter;
import com.example.ccmyphone.Models.AppsListModel;
import com.example.ccmyphone.R;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public class APPsFragment extends Fragment {

    public static String TAG = "APPsFragment";

    ListView appsListview;
    ArrayList<AppsListModel> appsInfoArray;
    AppListViewAdapter installedAppAdapter;

    public APPsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apps, container, false);

        appsListview = view.findViewById(R.id.appsListview);
        appsInfoArray = new ArrayList<AppsListModel>();

//        ArrayList<AppsListModel> installedApps = getInstalledApps();
        appsInfoArray = getInstalledApps();
        installedAppAdapter = new AppListViewAdapter(getActivity(), appsInfoArray);
        appsListview.setAdapter(installedAppAdapter);

        return view;
    }

    public void startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private ArrayList<AppsListModel> getInstalledApps() {
        List<PackageInfo> packs = getActivity().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo packageInfo = packs.get(i);
            if ((!isSystemPackage(packageInfo))) {
                long installedTime = 0, updateTime = 0;
                String installedDT = "", updateDT = "", appSize = "";
                String appName = packageInfo.applicationInfo.loadLabel(getActivity().getPackageManager()).toString();
                Drawable icon = packageInfo.applicationInfo.loadIcon(getActivity().getPackageManager());
                String packageName = packageInfo.packageName;
//                    installedTime = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).firstInstallTime;
                installedTime = packageInfo.firstInstallTime;
                updateTime = packageInfo.lastUpdateTime;
                installedDT = DateFormat.format("dd MMM yyyy EEE - hh:mm:ss a", new Date(installedTime)).toString();
                updateDT = DateFormat.format("dd MMM yyyy EEE - hh:mm:ss a", new Date(updateTime)).toString();

                StringBuilder stringBuilder = null;
                try {
                    packageInfo = getActivity().getPackageManager().getPackageInfo(
                            packageInfo.applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
                    String[] requestedPermissions = packageInfo.requestedPermissions;
                    stringBuilder = new StringBuilder();
                    if (requestedPermissions != null) {
                        for (int j = 0; j < requestedPermissions.length; j++) {                     //for loop
                            Log.d(TAG, "requestedPermissions " + requestedPermissions[j]);
                            stringBuilder.append(requestedPermissions[j]).append("\n");
                        }
//                        for (String requestedPermission : requestedPermissions) {                 //foreach loop
//                            Log.d(TAG, "requestedPermissions " + requestedPermission);
//                            stringBuilder.append(requestedPermission).append("\n");
//                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                appsInfoArray.add(new AppsListModel(appName, icon, installedDT, updateDT, packageName, stringBuilder.toString()));
            }
        }
        return appsInfoArray;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

}

package com.example.ccmyphone;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ccmyphone.Adapters.AppListViewAdapter;
import com.example.ccmyphone.Models.AppsListModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 */
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
        installedAppAdapter = new AppListViewAdapter(getActivity().getApplicationContext(), appsInfoArray);
        appsListview.setAdapter(installedAppAdapter);

        appsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

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

                appsInfoArray.add(new AppsListModel(appName, icon, installedDT, updateDT, packageName));
            }
        }
        return appsInfoArray;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

}

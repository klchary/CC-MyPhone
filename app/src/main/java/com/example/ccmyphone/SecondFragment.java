package com.example.ccmyphone;


import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    public static String TAG = "SecondFragment";

    private static final int PHONE_STATE_PERMISSION_CODE = 24;
    private static final int STORAGE_PERMISSION_CODE = 23;

    TabHost tabHost2;
    RecyclerView simRecyclreview;
    ListView appsListview;
    ArrayList<InfoModel> simInfoArray;
    ArrayList<AppsListModel> appsInfoArray;
    RecyclerView.LayoutManager mLayoutManager;
    FirtstFragment.CustomAdapter simAdapter, appsAdapter;

    String simType;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        tabHost2 = view.findViewById(R.id.tabhost2);
        tabHost2.setup();
        TabHost.TabSpec spec = tabHost2.newTabSpec("SIM");
        spec.setContent(R.id.simInfo);
        spec.setIndicator("SIM");
        tabHost2.addTab(spec);

        spec = tabHost2.newTabSpec("APP's");
        spec.setContent(R.id.appsInfo);
        spec.setIndicator("APP's");
        tabHost2.addTab(spec);

        simRecyclreview = view.findViewById(R.id.recyclervewSIM);
        appsListview = view.findViewById(R.id.appsListview);
        simInfoArray = new ArrayList<InfoModel>();
        appsInfoArray = new ArrayList<AppsListModel>();

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
            simInfoArray.add(new InfoModel("IMEI 1", manager.getDeviceId(0)));
            simInfoArray.add(new InfoModel("IMEI 2", manager.getDeviceId(1)));
        }

//        String simCarrierId = String.valueOf(manager.getSimCarrierId());
//        String simCarrieridName = String.valueOf(manager.getSimCarrierIdName());
        String simState = String.valueOf(manager.getSimState());
        String simState0 = null, simState1 = null, serviceStaee = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            simState0 = String.valueOf(manager.getSimState(0));
            simState1 = String.valueOf(manager.getSimState(1));
            serviceStaee = String.valueOf(manager.getServiceState());
        }
        String dataNetworktype = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dataNetworktype = String.valueOf(manager.getDataNetworkType());
        }
        String dataState = String.valueOf(manager.getDataState());
        String dataActivity = String.valueOf(manager.getDataActivity());
//        String signalStrenth = String.valueOf(manager.getSignalStrength());


        simInfoArray.add(new InfoModel("SIM Count", simType));
        simInfoArray.add(new InfoModel("Defualt Device ID", manager.getDeviceId()));
        simInfoArray.add(new InfoModel("SIM Serial No.", manager.getSimSerialNumber()));
        simInfoArray.add(new InfoModel("SIM No.", manager.getLine1Number()));
        simInfoArray.add(new InfoModel("SIM Country ISO", manager.getSimCountryIso()));
        simInfoArray.add(new InfoModel("SIM Operator", manager.getSimOperator()));
        simInfoArray.add(new InfoModel("SIM Operator Name", manager.getSimOperatorName()));
//        simInfoArray.add(new InfoModel("SIM Carrier ID", simCarrierId));
//        simInfoArray.add(new InfoModel("SIM CarrierId Name", simCarrieridName));
        simInfoArray.add(new InfoModel("SIM Software Version", manager.getDeviceSoftwareVersion()));
        simInfoArray.add(new InfoModel("SIM State", simState));
        simInfoArray.add(new InfoModel("SIM State0", simState0));
        simInfoArray.add(new InfoModel("SIM State1", simState1));
        simInfoArray.add(new InfoModel("SIM Data NetworType", dataNetworktype));
        simInfoArray.add(new InfoModel("SIM Data State", dataState));
        simInfoArray.add(new InfoModel("SIM Data Activity", dataActivity));
        simInfoArray.add(new InfoModel("SIM Nai", manager.getNai()));
        simInfoArray.add(new InfoModel("Service State", serviceStaee));
//        simInfoArray.add(new InfoModel("Signal Strenth", signalStrenth));

        simAdapter = new FirtstFragment.CustomAdapter(simInfoArray);
        simRecyclreview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        simRecyclreview.setLayoutManager(mLayoutManager);
        simRecyclreview.addItemDecoration(new RecyclerviewDivider(getActivity()));
        simRecyclreview.setItemAnimator(new DefaultItemAnimator());
        simRecyclreview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        simRecyclreview.setAdapter(simAdapter);

//        ArrayList<AppsListModel> installedApps = getInstalledApps();
        appsInfoArray = getInstalledApps();
        AppListAdapter installedAppAdapter = new AppListAdapter(getActivity().getApplicationContext(), appsInfoArray);
        appsListview.setAdapter(installedAppAdapter);

        return view;
    }

    private ArrayList<AppsListModel> getInstalledApps() {
        List<PackageInfo> packs = getActivity().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!isSystemPackage(p))) {
                long installedTime = 0, updateTime = 0;
                String installedDT = "", updateDT = "";
                String appName = p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getActivity().getPackageManager());
//                    installedTime = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).firstInstallTime;
                installedTime = p.firstInstallTime;
                updateTime = p.lastUpdateTime;
                installedDT = DateFormat.format("dd MMM yyyy EEE - hh:mm:ss a", new Date(installedTime)).toString();
                updateDT = DateFormat.format("dd MMM yyyy EEE - hh:mm:ss a", new Date(updateTime)).toString();

                appsInfoArray.add(new AppsListModel(appName, icon, installedDT, updateDT));
            }
        }
        return appsInfoArray;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public static class AppListAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        ArrayList<AppsListModel> appsInfoArray;

        AppListAdapter(Context context, ArrayList<AppsListModel> appsInfoArray) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.appsInfoArray = appsInfoArray;
        }

        @Override
        public int getCount() {
            return appsInfoArray.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder listViewHolder;
            if (convertView == null) {
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.listview_apps, parent, false);

                listViewHolder.textInListView = (TextView) convertView.findViewById(R.id.appName);
                listViewHolder.installTime = (TextView) convertView.findViewById(R.id.appFirstinstall);
                listViewHolder.updateTime = (TextView) convertView.findViewById(R.id.appLastupdate);
                listViewHolder.imageInListView = (ImageView) convertView.findViewById(R.id.appIcon);

                convertView.setTag(listViewHolder);
            } else {
                listViewHolder = (ViewHolder) convertView.getTag();
            }
            listViewHolder.textInListView.setText(appsInfoArray.get(position).getAppName());
            listViewHolder.imageInListView.setImageDrawable(appsInfoArray.get(position).getAppIcon());
            listViewHolder.installTime.setText("First Install Time : " + appsInfoArray.get(position).getInstalledTime());
            listViewHolder.updateTime.setText("Last Update Time : " + appsInfoArray.get(position).getUpdateTime());

            return convertView;
        }

        static class ViewHolder {

            TextView textInListView, installTime, updateTime;
            ImageView imageInListView;
        }
    }

}

package com.example.ccmyphone;


import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirtstFragment extends Fragment {

    public static String TAG = "FirtstFragment";

    TabHost tabHost;
    static int width;
    static int height;

    RecyclerView generalRecyclreview, memoryRecyclerview, batteryRecyclerview;
    ArrayList<InfoModel> generalInfoArray;
    ArrayList<InfoModel> memoryInfoArray;
    ArrayList<InfoModel> batteryInfoArray;
    RecyclerView.LayoutManager mLayoutManager;
    CustomAdapter generalAdapter, memoryAdapter, batteryAdapter;

    String bPercentStr, bVoltageStr, bTempStr, bStatusStr, bChargingPlugStr, bHealthStr;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
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
                batteryInfoArray.add(new InfoModel("Battery Type", ""));
                batteryInfoArray.add(new InfoModel("Battery Status", bStatusStr));
                batteryInfoArray.add(new InfoModel("Power Source", bChargingPlugStr));
                batteryInfoArray.add(new InfoModel("Battery Voltage", bVoltageStr));
                batteryInfoArray.add(new InfoModel("Battery Temperature", bTempStr));
                batteryInfoArray.add(new InfoModel("Battery Health", bHealthStr));
                batteryAdapter.notifyDataSetChanged();
                batteryRecyclerview.invalidate();

            } catch (Exception e) {
                Log.d(TAG, "Battery Info Error");
            }
        }
    };

    public FirtstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_firtst, container, false);

        tabHost = view.findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("General");
        spec.setContent(R.id.generalInfo);
        spec.setIndicator("General");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Memory");
        spec.setContent(R.id.memoryInfo);
        spec.setIndicator("Memory");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Battery");
        spec.setContent(R.id.batteryInfo);
        spec.setIndicator("Battery");
        tabHost.addTab(spec);

        generalInfoArray = new ArrayList<InfoModel>();
        generalRecyclreview = view.findViewById(R.id.recyclervewGeneral);
        memoryInfoArray = new ArrayList<InfoModel>();
        memoryRecyclerview = view.findViewById(R.id.recyclervewMemory);
        batteryInfoArray = new ArrayList<InfoModel>();
        batteryRecyclerview = view.findViewById(R.id.recyclervewBattery);

        getScreenResolution(getActivity());

        ActivityManager actManager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem;
        sizeformat(totalMemory);

        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                Toast.makeText(getActivity(), "LDPI", Toast.LENGTH_SHORT).show();
                generalInfoArray.add(new InfoModel("Screen Size Type", "LDPI"));
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                Toast.makeText(getActivity(), "MDPI", Toast.LENGTH_SHORT).show();
                generalInfoArray.add(new InfoModel("Screen Size Type", "MDPI"));
                break;
            case DisplayMetrics.DENSITY_HIGH:
                Toast.makeText(getActivity(), "HDPI", Toast.LENGTH_SHORT).show();
                generalInfoArray.add(new InfoModel("Screen Size Type", "HDPI"));
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                Toast.makeText(getActivity(), "XHDPI", Toast.LENGTH_SHORT).show();
                generalInfoArray.add(new InfoModel("Screen Size Type", "XHDPI"));
                break;
        }
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                Toast.makeText(getActivity(), "X - Large screen", Toast.LENGTH_LONG).show();
                generalInfoArray.add(new InfoModel("Screen Size", "X - Large Screen"));
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                Toast.makeText(getActivity(), "Large screen", Toast.LENGTH_LONG).show();
                generalInfoArray.add(new InfoModel("Screen Size", "Large Screen"));
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                Toast.makeText(getActivity(), "Normal screen", Toast.LENGTH_LONG).show();
                generalInfoArray.add(new InfoModel("Screen Size", "Normal Screen"));
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                Toast.makeText(getActivity(), "Small screen", Toast.LENGTH_LONG).show();
                generalInfoArray.add(new InfoModel("Screen Size", "Small Screen"));
                break;
            default:
                Toast.makeText(getActivity(), "Screen size is neither large, normal or small", Toast.LENGTH_LONG).show();

        }

        String BaseVersionCode = String.valueOf(Build.VERSION_CODES.BASE);
        String BaseVersionCode1 = String.valueOf(Build.VERSION_CODES.BASE_1_1);
        String SdkIntVersion = String.valueOf(Build.VERSION.SDK_INT);

        generalInfoArray.add(new InfoModel("API Level", SdkIntVersion));
        generalInfoArray.add(new InfoModel("Screen Resolution", width + " X " + height));
        generalInfoArray.add(new InfoModel("Serial Number", Build.SERIAL));
        generalInfoArray.add(new InfoModel("Device Model", Build.MODEL));
        generalInfoArray.add(new InfoModel("ID Number", Build.ID));
        generalInfoArray.add(new InfoModel("Manufacturer Name", Build.MANUFACTURER));
        generalInfoArray.add(new InfoModel("Brand Name", Build.BRAND));
        generalInfoArray.add(new InfoModel("Type", Build.TYPE));
        generalInfoArray.add(new InfoModel("User", Build.USER));
        generalInfoArray.add(new InfoModel("Base Version", BaseVersionCode));
        generalInfoArray.add(new InfoModel("Board", Build.BOARD));
        generalInfoArray.add(new InfoModel("Incremental", Build.VERSION.INCREMENTAL));
        generalInfoArray.add(new InfoModel("SDK Version", SdkIntVersion));
        generalInfoArray.add(new InfoModel("Host", Build.HOST));
        generalInfoArray.add(new InfoModel("FingerPrint", Build.FINGERPRINT));
        generalInfoArray.add(new InfoModel("Bootloader", Build.BOOTLOADER));
        generalInfoArray.add(new InfoModel("Hardware", Build.HARDWARE));
        generalInfoArray.add(new InfoModel("Tags", Build.TAGS));
        generalInfoArray.add(new InfoModel("Display", Build.DISPLAY));
        generalInfoArray.add(new InfoModel("Radio Version", Build.getRadioVersion()));

        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMM yyyy - hh:mm:ss");
        String dateString = formatter.format(new Date(Build.TIME));
        generalInfoArray.add(new InfoModel("Build Time", dateString));

        generalAdapter = new CustomAdapter(generalInfoArray);
        generalRecyclreview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        generalRecyclreview.setLayoutManager(mLayoutManager);
        generalRecyclreview.setItemAnimator(new DefaultItemAnimator());
        generalRecyclreview.setAdapter(generalAdapter);
        generalRecyclreview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        memoryInfoArray.add(new InfoModel("Available Internal Memory", getAvailableInternalMemorySize()));
        memoryInfoArray.add(new InfoModel("Available External Memory", getAvailableExternalMemorySize()));
        memoryInfoArray.add(new InfoModel("Total Internal Memory", getTotalExternalMemorySize()));
        memoryInfoArray.add(new InfoModel("Total External Memory", getTotalInternalMemorySize()));
        memoryInfoArray.add(new InfoModel("Total RAM Size", sizeformat(totalMemory)));

        memoryAdapter = new CustomAdapter(memoryInfoArray);
        memoryRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        memoryRecyclerview.setLayoutManager(mLayoutManager);
        memoryRecyclerview.setItemAnimator(new DefaultItemAnimator());
        memoryRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        memoryRecyclerview.setAdapter(memoryAdapter);


        getActivity().registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        batteryInfoArray.add(new InfoModel("Battery Capacity", getBatteryCapacity(getActivity()) + " mAh"));

        batteryAdapter = new CustomAdapter(batteryInfoArray);
        batteryRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        batteryRecyclerview.setLayoutManager(mLayoutManager);
        batteryRecyclerview.setItemAnimator(new DefaultItemAnimator());
        batteryRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        batteryRecyclerview.setAdapter(batteryAdapter);

        batteryRecyclerview.addItemDecoration(new RecyclerviewDivider(getActivity()));
        memoryRecyclerview.addItemDecoration(new RecyclerviewDivider(getActivity()));
        generalRecyclreview.addItemDecoration(new RecyclerviewDivider(getActivity()));

        return view;
    }

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return sizeformat(availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return sizeformat(totalBlocks * blockSize);
    }

    public static String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return sizeformat(availableBlocks * blockSize);
        } else {
            return sizeformat(0);
        }
    }

    public static String getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return sizeformat(totalBlocks * blockSize);
        } else {
            return sizeformat(0);
        }
    }

    public static String sizeformat(long size) {
        String hrSize = "";
        double kb = size;
        Log.d(TAG, String.valueOf(size));
        double mb = size / 1024.0;
        double gb = size / 1048576.0;
        double tb = size / 1073741824.0;
        DecimalFormat dec = new DecimalFormat("0.00");

        if (size >= 1024) {
            hrSize = dec.format(size).concat(" KB");
            size /= 1024;
            if (size >= 1024) {
                hrSize = dec.format(mb).concat(" MB");
                size /= 1024;
                if (size >= 1024) {
                    hrSize = dec.format(gb).concat(" MB");
                    size /= 1024;
                }

            }
        }
        return hrSize;
    }

    public static String formatSize(double size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
                if (size >= 1024) {
                    suffix = "GB";
                    size /= 1024;
                    if (size >= 1024) {
                        suffix = "TB";
                        size /= 1024;
                    }
                }

            }
        }

//        StringBuilder resultBuffer = new StringBuilder(Double.toString(size));
//
//        int commaOffset = resultBuffer.length() - 3;
//        while (commaOffset > 0) {
//            resultBuffer.insert(commaOffset, ',');
//            commaOffset -= 3;
//        }
//
//        if (suffix != null) resultBuffer.append(suffix);
//        return resultBuffer.toString();
        return String.valueOf(size);
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

    public double getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(context);

            batteryCapacity = (double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return batteryCapacity;
    }

    static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        private ArrayList<InfoModel> infoModel;

        public CustomAdapter(ArrayList<InfoModel> infoModel) {
            this.infoModel = infoModel;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView Title, Des;

            public MyViewHolder(View view) {
                super(view);
                Title = view.findViewById(R.id.infoTitle);
                Des = view.findViewById(R.id.infoDetails);
            }
        }
        @NonNull
        @Override
        public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);

            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

            myViewHolder.Title.setText(infoModel.get(i).getInfoTitle());
            myViewHolder.Des.setText(infoModel.get(i).getInfoDetail());

            myViewHolder.Title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        @Override
        public int getItemCount() {
            if (infoModel != null) {
                return infoModel.size();
            } else {
                return 0;
            }
        }
    }

}

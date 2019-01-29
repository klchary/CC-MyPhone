package com.example.ccmyphone;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActivity";

    TextView currentDate, currentTime;
    TextView batteryPercet, batteryHealth, batteryVolt, batteryTemp;
    TabHost tabHost;

    ListView deviceinfoList;
    ArrayList<String> stringArray;
    ArrayAdapter<String> arrayAdapter;

    ListView deviceinfoList1;
    ArrayList<String> stringArray1;
    ArrayAdapter<String> arrayAdapter1;

    static int width;
    static int height;

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
                bVoltageStr = fullVoltage + "\nvolt";
                bHealthStr = BatteryHealth;
                bStatusStr = BatteryStatus;
                bChargingPlugStr = BattPowerSource;

                batteryHealth.setText(bHealthStr + "\n" + bChargingPlugStr);
                batteryPercet.setText(bPercentStr);
                batteryTemp.setText(bTempStr + " " + bStatusStr);
                batteryVolt.setText(bVoltageStr);

                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                    if (level <= 20) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_20_charging, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Red_Dark));
                    } else if (level <= 30) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_30_charging, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Red_Dark_Lite));
                    } else if (level <= 50) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_50_charging, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Green_Normal));
                    } else if (level <= 60) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_60_charging, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Green_Normal));
                    } else if (level <= 80) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_80_charging, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Green_Dark_Lite));
                    } else if (level <= 90) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_90_charging, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Green_Dark_Lite));
                    } else if (level <= 100) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_100, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Green_Dark));
                    }
                }else {
                    if (level <= 20) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_20, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Red_Dark));
                    } else if (level <= 30) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_30, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Red_Dark_Lite));
                    } else if (level <= 50) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_50, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Green_Normal));
                    } else if (level <= 60) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_60, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Green_Normal));
                    } else if (level <= 80) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_80, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Green_Dark_Lite));
                    } else if (level <= 90) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_90, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Green_Dark_Lite));
                    } else if (level <= 100) {
                        batteryPercet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_battery_100, 0);
                        batteryPercet.setTextColor(getResources().getColor(R.color.Green_Dark));
                    }
                }

//                stringArray1.remove("Battery Percentage :\n" + bPercentStr);
//                stringArray1.remove("Battery Charging :\n" + bStatusStr);
//                stringArray1.remove("Battery Charging Type :\n" + bChargingPlugStr);
//                stringArray1.remove("Battery Temprature :\n" + bTempStr);
//                stringArray1.remove("Battery Voltage :\n" + bVoltageStr);
//                stringArray1.remove("Battery Health :\n" + bHealthStr);
//
//                stringArray1.add("Battery Percentage :\n" + bPercentStr);
//                stringArray1.add("Battery Charging :\n" + bStatusStr);
//                stringArray1.add("Battery Charging Type :\n" + bChargingPlugStr);
//                stringArray1.add("Battery Temprature :\n" + bTempStr);
//                stringArray1.add("Battery Voltage :\n" + bVoltageStr);
//                stringArray1.add("Battery Health :\n" + bHealthStr);
//                stringArray1.add("Battery Technology :\n" + BTech);
//                stringArray1.add("Battery Icon :\n" + BIcon);


            } catch (Exception e) {
                Log.d(TAG, "Battery Info Error");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("General");
        spec.setContent(R.id.generalInfo);
        spec.setIndicator("General");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("MemoryBattery");
        spec.setContent(R.id.memoryBattery);
        spec.setIndicator("Memory & Battery");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("MemoryBattery");
        spec.setContent(R.id.advanceInfo);
        spec.setIndicator("Memory & Battery");
        tabHost.addTab(spec);

        deviceinfoList = findViewById(R.id.deviceinfoListview);
        stringArray = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringArray);

        getScreenResolution(this);

        stringArray.add("Screen Resolution :\n" + width + " X " + height);
        stringArray.add("Serial Number :\n" + Build.SERIAL);
        stringArray.add("Device Model :\n" + Build.MODEL);
        stringArray.add("ID Number :\n" + Build.ID);
        stringArray.add("Manufacturer Name :\n" + Build.MANUFACTURER);
        stringArray.add("Brand Name :\n" + Build.BRAND);
        stringArray.add("Type :\n" + Build.TYPE);
        stringArray.add("User :\n" + Build.USER);
        stringArray.add("Base Version :\n" + Build.VERSION_CODES.BASE);
        stringArray.add("Board :\n" + Build.BOARD);
        stringArray.add("Incremental :\n" + Build.VERSION.INCREMENTAL);
        stringArray.add("SDK Version :\n" + Build.VERSION.SDK_INT);
        stringArray.add("Host :\n" + Build.HOST);
        stringArray.add("FingerPrint :\n" + Build.FINGERPRINT);
        stringArray.add("Bootloader :\n" + Build.BOOTLOADER);
        stringArray.add("Hardware :\n" + Build.HARDWARE);
        stringArray.add("Tags :\n" + Build.TAGS);
        stringArray.add("Display :\n" + Build.DISPLAY);
        stringArray.add("Radio Version :\n" + Build.getRadioVersion());

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy - hh:mm:ss");
        String dateString = formatter.format(new Date(Build.TIME));
        stringArray.add("Time :\n" + dateString);
        Log.d("General", "Device Info :" + stringArray);
        deviceinfoList.setAdapter(arrayAdapter);

        currentDate = findViewById(R.id.currentDate);
        currentTime = findViewById(R.id.currentTime);
        batteryPercet = findViewById(R.id.batteryPercent);
        batteryTemp = findViewById(R.id.batteryTemp);
        batteryVolt = findViewById(R.id.batteryVoltage);
        batteryHealth = findViewById(R.id.batteryHealth);


        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
//        currentTime.setText(timeFormat.format(Calendar.getInstance().getTime()));
        currentDate.setText(dateFormat.format(Calendar.getInstance().getTime()));

        final Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String timezone = String.valueOf(TimeZone.getDefault().getID());
                currentTime.setText(new SimpleDateFormat("h:mm:ss a").format(new Date()) + " - " +  timezone);
                handler.postDelayed(this, 1000);
            }
        }, 10);


        deviceinfoList1 = findViewById(R.id.memoryBatteryListview);
        stringArray1 = new ArrayList<String>();
        arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringArray1);

        ActivityManager actManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem;
        sizeformat(totalMemory);

        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                Toast.makeText(this, "LDPI", Toast.LENGTH_SHORT).show();
                stringArray.add("Screen Size Type :\n" + "LDPI");
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                Toast.makeText(this, "MDPI", Toast.LENGTH_SHORT).show();
                stringArray.add("Screen Size Type :\n" + "MDPI");
                break;
            case DisplayMetrics.DENSITY_HIGH:
                Toast.makeText(this, "HDPI", Toast.LENGTH_SHORT).show();
                stringArray.add("Screen Size Type :\n" + "HDPI");
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                Toast.makeText(this, "XHDPI", Toast.LENGTH_SHORT).show();
                stringArray.add("Screen Size Type :\n" + "XHDPI");
                break;
        }
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                Toast.makeText(this, "X - Large screen", Toast.LENGTH_LONG).show();
                stringArray.add("Screen Size :\n" + "X - Large Screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                Toast.makeText(this, "Large screen", Toast.LENGTH_LONG).show();
                stringArray.add("Screen Size :\n" + "Large Screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                Toast.makeText(this, "Normal screen", Toast.LENGTH_LONG).show();
                stringArray.add("Screen Size :\n" + "Normal Screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                Toast.makeText(this, "Small screen", Toast.LENGTH_LONG).show();
                stringArray.add("Screen Size :\n" + "Small Screen");
                break;
            default:
                Toast.makeText(this, "Screen size is neither large, normal or small", Toast.LENGTH_LONG).show();

        }

//        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        Intent batteryStatus = registerReceiver(mBatInfoReceiver, ifilter);

        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


        stringArray1.add("Available Internal Memory :\n" + getAvailableInternalMemorySize());
        stringArray1.add("Available External Memory :\n" + getAvailableExternalMemorySize());
        stringArray1.add("Total External Memory :\n" + getTotalExternalMemorySize());
        stringArray1.add("Total Internal Memory :\n" + getTotalInternalMemorySize());
        stringArray1.add("Total RAM Size :\n" + sizeformat(totalMemory));
        stringArray1.add("Battery Capacity :\n" + getBatteryCapacity(this) + " mAh");


        Log.d(TAG, "Device Memory :" + stringArray1);
        deviceinfoList1.setAdapter(arrayAdapter1);


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

}

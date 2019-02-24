package com.example.ccmyphone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static String TAG = "MainActivity";
    RelativeLayout fragmentLayout;
    TextView currentDate, currentTime, currentTimezone;
    TextView batteryPercet;
    String bPercentStr, bVoltageStr, bTempStr, bStatusStr, bChargingPlugStr, bHealthStr;
    BottomNavigationView bottomNavigationView;

    private static final int PHONE_STATE_PERMISSION_CODE = 24;
    private static final int STORAGE_PERMISSION_CODE = 23;


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

                batteryPercet.setText(bPercentStr);
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
                } else {
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

            } catch (Exception e) {
                Log.d(TAG, "Battery Info Error");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFirstFragment();

        bottomNavigationView = findViewById(R.id.bottombar);
        fragmentLayout = findViewById(R.id.fragmentLayout);
        currentTime = findViewById(R.id.currentTime);
        currentDate = findViewById(R.id.currentDate);
        currentTimezone = findViewById(R.id.currentTimezone);
        batteryPercet = findViewById(R.id.batteryPercent);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
//        currentTime.setText(timeFormat.format(Calendar.getInstance().getTime()));
        currentDate.setText(dateFormat.format(Calendar.getInstance().getTime()));
        String timezoneP = String.valueOf(TimeZone.getDefault().getID()); // Output is "Asia/Culcutta"
        String timezone = String.valueOf(TimeZone.getDefault().getDisplayName()); // Output is "India Standard time"

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("Z z", Locale.getDefault());

        String localTime = date.format(currentLocalTime); // Output is "+0530 IST"
        TimeZone timeZone = TimeZone.getDefault();
        String timeZoneInGMTFormat = timeZone.getDisplayName(false, TimeZone.SHORT); // Output is if SHORT "IST" if LONG "India Standard Time"
        currentTimezone.setText(localTime + " - " + timezoneP);

        final Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentTime.setText(new SimpleDateFormat("h:mm:ss a").format(new Date()));
                handler.postDelayed(this, 1000);
            }
        }, 10);

//        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        Intent batteryStatus = registerReceiver(mBatInfoReceiver, ifilter);

        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("FirstFrag");
        if (currentFragment != null && currentFragment.isVisible()) {
            finish();
        } else {
            loadFirstFragment();
        }
    }

    public void loadFirstFragment() {
        FragmentManager fmhome = getSupportFragmentManager();
        FirtstFragment firstFragment = new FirtstFragment();
        fmhome.beginTransaction().add(R.id.fragmentLayout, firstFragment, "FirstFrag").commit();
        FragmentTransaction abouttransaction = getSupportFragmentManager().beginTransaction();
        abouttransaction.replace(R.id.fragmentLayout, firstFragment, "FirstFrag").addToBackStack(firstFragment.getClass().getName()).commit();
    }

    public void loadSecondFragment() {
        FragmentManager fmhome = getSupportFragmentManager();
        SecondFragment secondFragment = new SecondFragment();
        fmhome.beginTransaction().add(R.id.fragmentLayout, secondFragment, "FirstFrag").commit();
        FragmentTransaction abouttransaction = getSupportFragmentManager().beginTransaction();
        abouttransaction.replace(R.id.fragmentLayout, secondFragment, "FirstFrag").addToBackStack(secondFragment.getClass().getName()).commit();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.bFragmentOne:
                loadFirstFragment();
                break;

            case R.id.bFragmentTwo:
                loadSecondFragment();
                break;
        }

        return true;
    }

    public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
//                    item.setShiftingMode(false);
                    item.setShifting(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }

}

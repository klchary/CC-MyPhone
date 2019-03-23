package com.example.ccmyphone;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ccmyphone.Adapters.TabViewPagerDevice;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DeviceInfoActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static String TAG = "DeviceInfoActivity";
    RelativeLayout fragmentLayout;
    TextView currentDate, currentTime, currentTimezone;
    TextView batteryPercet;
    String bPercentStr, bVoltageStr, bTempStr, bStatusStr, bChargingPlugStr, bHealthStr;

    public static DrawerLayout deviceDrawerLayout;

    private TabLayout tabLayout;
    public static ViewPager viewPagerDevice;

    static boolean deviceInfoActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPagerDevice = (ViewPager) findViewById(R.id.viewPager);

        fragmentLayout = findViewById(R.id.fragmentLayout);
        currentTime = findViewById(R.id.currentTime);
        currentDate = findViewById(R.id.currentDate);
        currentTimezone = findViewById(R.id.currentTimezone);
        batteryPercet = findViewById(R.id.batteryPercent);

        deviceDrawerLayout = findViewById(R.id.devideDrawerLayout);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

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

        TabViewPagerDevice tabViewPager = new TabViewPagerDevice(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPagerDevice.setAdapter(tabViewPager);
        tabLayout.setupWithViewPager(viewPagerDevice);
//        viewPager.setCurrentItem(2);
//        viewPager.getCurrentItem();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerDevice.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPagerDevice.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(DeviceInfoActivity.this, "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

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
                        } else if (level <= 99) {
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
        this.registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        deviceInfoActive = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        deviceInfoActive = false;
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

    private void addShortcut() {
        Intent shortcutIntent = new Intent(getApplicationContext(), DeviceInfoActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, R.string.app_name);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }

    private void removeShortcut() {
        Intent shortcutIntent = new Intent(getApplicationContext(), DeviceInfoActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, R.string.app_name);
        addIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }

}

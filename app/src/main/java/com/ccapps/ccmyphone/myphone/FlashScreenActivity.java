package com.ccapps.ccmyphone.myphone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccapps.ccmyphone.myphone.LoginRegActFrag.LoginRegisterActivity;
import com.ccapps.ccmyphone.myphone.Models.UserDetails;
import com.google.gson.Gson;

import static com.ccapps.ccmyphone.myphone.ApplicationConstants.SHARED_PERSISTENT_VALUES;
import static com.ccapps.ccmyphone.myphone.ApplicationConstants.USER_DETAILS;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.ccapps.ccmyphone.myphone
 * Project Name CCMyPhone
 **/

public class FlashScreenActivity extends Activity {

    String TAG = "FlashScreenActivity";

    final int SPLASH_DISPLAY_LENGTH = 3000;

    CardView logoImageCardLay;
    ImageView flashScreenLogo;
    TextView appVersion;
    SharedPreferences sharedpref;
    String userSharedDetails;
    Gson gson = new Gson();
    UserDetails userDetails;

    Animation bounceAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_flash_screen);

        flashScreenLogo = findViewById(R.id.flashScreenLogo);
        logoImageCardLay = findViewById(R.id.logoImageCardLay);
        bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        logoImageCardLay.startAnimation(bounceAnimation);
        appVersion = findViewById(R.id.appVersion);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            appVersion.setText(getString(R.string.appVersion, "App Version: ", version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        sharedpref = getSharedPreferences(SHARED_PERSISTENT_VALUES, Context.MODE_PRIVATE);
        userSharedDetails = sharedpref.getString(USER_DETAILS, null);
        if (userSharedDetails != null) {
            userDetails = gson.fromJson(userSharedDetails, UserDetails.class);
        }
        Log.d(TAG, "userDetails " + userDetails);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent activityIntent;
                // go straight to main if a token is stored
                if (userDetails != null) {
                    activityIntent = new Intent(FlashScreenActivity.this, MasterActivity.class);
                } else {
                    activityIntent = new Intent(FlashScreenActivity.this, LoginRegisterActivity.class);
                }
                startActivity(activityIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}

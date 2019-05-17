package com.example.ccmyphone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.ccmyphone.Models.UserDetails;
import com.google.gson.Gson;

import static com.example.ccmyphone.ApplicationConstants.SHARED_PERSISTENT_VALUES;
import static com.example.ccmyphone.ApplicationConstants.USER_DETAILS;

public class FlashScreenActivity extends Activity {

    String TAG = "FlashScreenActivity";

    final int SPLASH_DISPLAY_LENGTH = 3000;

    ImageView flashScreenLogo;
    SharedPreferences sharedpref;
    String userSharedDetails;
    Gson gson = new Gson();
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_flash_screen);

        flashScreenLogo = findViewById(R.id.flashScreenLogo);

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

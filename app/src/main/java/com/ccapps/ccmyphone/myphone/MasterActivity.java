package com.ccapps.ccmyphone.myphone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccapps.ccmyphone.myphone.Models.UserDetails;
import com.ccapps.ccmyphone.myphone.OtherClasses.PermissionsClass;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ccapps.ccmyphone.myphone.ApplicationConstants.ADMOB_ADUNIT_ID_BANNER;
import static com.ccapps.ccmyphone.myphone.ApplicationConstants.ADMOB_ADUNIT_ID_FULL_SCREEN;
import static com.ccapps.ccmyphone.myphone.ApplicationConstants.ADMOB_APPID;
import static com.ccapps.ccmyphone.myphone.ApplicationConstants.CC_MyPhone_PATH;
import static com.ccapps.ccmyphone.myphone.ApplicationConstants.SHARED_PERSISTENT_VALUES;
import static com.ccapps.ccmyphone.myphone.ApplicationConstants.USER_DETAILS;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.ccapps.ccmyphone.myphone
 * Project Name CCMyPhone
 **/

public class MasterActivity extends AppCompatActivity {

    String TAG = "MasterActivity";

    CardView cardMainDevice, cardMainTotaliser, cardMainOriginal;
    ImageView ivCateDevice, ivCateTotaliser, ivCateOriginal;
    Button btnCateDevice, btnCateTotaliser, btnCateOriginal;
    TextView userName;

    public static final int REQUIRED_MULTIPLE_PERMISSIONS = 100;

    SharedPreferences sharedpref;
    String userSharedDetails;
    Gson gson = new Gson();
    UserDetails userDetails;

    //    AdView masterAcAdBanner;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        FindAllViews();
        checkAndRequestPermissions();
        createDirectory();

        MobileAds.initialize(this, ADMOB_APPID);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(ADMOB_ADUNIT_ID_BANNER);
        AdRequest adRequest = new AdRequest.Builder().build();
//        masterAcAdBanner.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(ADMOB_ADUNIT_ID_FULL_SCREEN);
        AdRequest adRequestFull = new AdRequest.Builder().addTestDevice("deviceid").build();
//        mInterstitialAd.loadAd(adRequestFull);

        sharedpref = getSharedPreferences(SHARED_PERSISTENT_VALUES, Context.MODE_PRIVATE);
        userSharedDetails = sharedpref.getString(USER_DETAILS, null);
        if (userSharedDetails != null) {
            userDetails = gson.fromJson(userSharedDetails, UserDetails.class);
        }
        Log.d(TAG, "userDetails " + userDetails);

        if (userDetails != null) {
            userName.setText("Hello, " + userDetails.getUserName());
        } else {
            userName.setText("Hello, Guest");
        }

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });

        cardMainDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterActivity.this, DeviceInfoActivity.class);
                startActivity(intent);
            }
        });

        cardMainTotaliser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterActivity.this, TotaliserActivity.class);
                startActivity(intent);
            }
        });

        cardMainOriginal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterActivity.this, OriginalActivity.class);
                startActivity(intent);
            }
        });

        ivCateDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterActivity.this, DeviceInfoActivity.class);
                startActivity(intent);
            }
        });

        ivCateTotaliser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterActivity.this, TotaliserActivity.class);
                startActivity(intent);
            }
        });

        ivCateOriginal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterActivity.this, OriginalActivity.class);
                startActivity(intent);
            }
        });

        btnCateDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterActivity.this, DeviceInfoActivity.class);
                startActivity(intent);
            }
        });

        btnCateTotaliser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterActivity.this, TotaliserActivity.class);
                startActivity(intent);
            }
        });

        btnCateOriginal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterActivity.this, OriginalActivity.class);
                startActivity(intent);
            }
        });
//        masterAcAdBanner.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//                Log.d(TAG, "AdMob BannerAd Loaded");
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//                if (errorCode == 0) {
//                    Log.d(TAG, "ERROR_CODE_INTERNAL_ERROR - " +
//                            "Something happened internally; for instance, an invalid response was received from the ad server. " + errorCode);
//                } else if (errorCode == 1) {
//                    Log.d(TAG, "ERROR_CODE_INVALID_REQUEST - " +
//                            "The ad request was invalid; for instance, the ad unit ID was incorrect. " + errorCode);
//                } else if (errorCode == 2) {
//                    Log.d(TAG, "ERROR_CODE_NETWORK_ERROR - " +
//                            "The ad request was unsuccessful due to network connectivity. " + errorCode);
//                } else if (errorCode == 3) {
//                    Log.d(TAG, "ERROR_CODE_NO_FILL - " +
//                            "The ad request was successful, but no ad was returned due to lack of ad inventory. " + errorCode);
//                }
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//                Log.d(TAG, "User Clicked BannerAd");
//            }
//
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//                Log.d(TAG, "User Clicked BannerAd and Opened another App");
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when the user is about to return
//                // to the app after tapping on an ad.
//                Log.d(TAG, "User Closed BannerAd");
//            }
//        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mInterstitialAd.show();
                Log.d(TAG, "AdMob BannerAd Loaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                if (errorCode == 0) {
                    Log.d(TAG, "ERROR_CODE_INTERNAL_ERROR - " +
                            "Something happened internally; for instance, an invalid response was received from the ad server. " + errorCode);
                } else if (errorCode == 1) {
                    Log.d(TAG, "ERROR_CODE_INVALID_REQUEST - " +
                            "The ad request was invalid; for instance, the ad unit ID was incorrect. " + errorCode);
                } else if (errorCode == 2) {
                    Log.d(TAG, "ERROR_CODE_NETWORK_ERROR - " +
                            "The ad request was unsuccessful due to network connectivity. " + errorCode);
                } else if (errorCode == 3) {
                    Log.d(TAG, "ERROR_CODE_NO_FILL - " +
                            "The ad request was successful, but no ad was returned due to lack of ad inventory. " + errorCode);
                }
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.d(TAG, "User Clicked BannerAd");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d(TAG, "User Clicked BannerAd and Opened another App");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Log.d(TAG, "User Closed BannerAd");
            }
        });

    }

    @Override
    protected void onPause() {
//        if (masterAcAdBanner != null) {
//            masterAcAdBanner.pause();
//        }
        super.onPause();
    }

    @Override
    protected void onResume() {
//        if (masterAcAdBanner != null) {
//            masterAcAdBanner.resume();
//        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
//        if (masterAcAdBanner != null) {
//            masterAcAdBanner.destroy();
//        }
        super.onDestroy();
    }

    public void RequestPermissions() {
        PermissionsClass permissionsClass = new PermissionsClass(this, this);
        if (!permissionsClass.checkCAMERAPermission()) {
            permissionsClass.requestPermissionForCAMERA();
        }
        if (!permissionsClass.checkREADSTORAGEPermission()) {
            permissionsClass.requestPermissionForREADSTORAGE();
        }
        if (!permissionsClass.checkWRITESTORAGEPermission()) {
            permissionsClass.requestPermissionForWRITESTORAGE();
        }
        if (!permissionsClass.checkFINELOCATIONPermission()) {
            permissionsClass.requestPermissionForFINELOCATION();
        }
        if (!permissionsClass.checkCOARSELOCATIONPermission()) {
            permissionsClass.requestPermissionForCOARSELOCATION();
        }
        if (!permissionsClass.checkPHONESTATEPermission()) {
            permissionsClass.requestPermissionForPHONESTATE();
        }
        if (!permissionsClass.checkPHONENUMBERSPermission()) {
            permissionsClass.requestPermissionForPHONENUMBERS();
        }
    }

    public void checkAndRequestPermissions() {
        int camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int readpermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionFineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionPState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int permissionContacts = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int permissionPNumbers = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            permissionPNumbers = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS);
        }


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (readpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionFineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionPState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionPNumbers != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_NUMBERS);
        }
        if (permissionContacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUIRED_MULTIPLE_PERMISSIONS);
        }
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUIRED_MULTIPLE_PERMISSIONS) {
            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {
                    switch (permissions[i]) {
                        case Manifest.permission.CAMERA:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "Camera granted");
                            }
                            break;
                        case Manifest.permission.READ_EXTERNAL_STORAGE:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "Read Storage granted");
                            }
                            break;
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "Write Storage granted");
                            }
                            break;
                        case Manifest.permission.ACCESS_FINE_LOCATION:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "Fine Location granted");
                            }
                            break;
                        case Manifest.permission.ACCESS_COARSE_LOCATION:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "Coarse Location granted");
                            }
                            break;
                        case Manifest.permission.READ_PHONE_STATE:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "Phone State granted");
                            }
                            break;
                        case Manifest.permission.READ_PHONE_NUMBERS:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "Phone Numbers granted");
                            }
                            break;
                        case Manifest.permission.READ_CONTACTS:
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.e("msg", "Phone Contacs granted");
                            }
                            break;
                    }
                }
            }
        }
    }

    public void FindAllViews() {
        cardMainDevice = findViewById(R.id.cardMainDevice);
        cardMainTotaliser = findViewById(R.id.cardMainTotaliser);
        cardMainOriginal = findViewById(R.id.cardMainOriginal);
        ivCateDevice = findViewById(R.id.ivCateDevice);
        ivCateTotaliser = findViewById(R.id.ivCateTotaliser);
        ivCateOriginal = findViewById(R.id.ivCateOriginal);
        btnCateDevice = findViewById(R.id.btnCateDevice);
        btnCateTotaliser = findViewById(R.id.btnCateTotaliser);
        btnCateOriginal = findViewById(R.id.btnCateOriginal);
        userName = findViewById(R.id.userName);
    }

    public void createDirectory() {
        File dir = new File(CC_MyPhone_PATH);
        if (!dir.exists()) {
            if (dir.mkdir())
                Log.e("CreateDirectory", "Main Directory Created : " + dir);
        }
    }

}

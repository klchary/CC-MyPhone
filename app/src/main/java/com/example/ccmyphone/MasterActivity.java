package com.example.ccmyphone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ccmyphone.OtherClasses.PermissionsClass;

import java.util.ArrayList;
import java.util.List;

public class MasterActivity extends AppCompatActivity {

    CardView cardMainDevice, cardMainTotaliser, cardMainOriginal;
    ImageView ivCateDevice, ivCateTotaliser, ivCateOriginal;
    Button btnCateDevice, btnCateTotaliser, btnCateOriginal;

    public static final int REQUIRED_MULTIPLE_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        cardMainDevice = findViewById(R.id.cardMainDevice);
        cardMainTotaliser = findViewById(R.id.cardMainTotaliser);
        cardMainOriginal = findViewById(R.id.cardMainOriginal);
        ivCateDevice = findViewById(R.id.ivCateDevice);
        ivCateTotaliser = findViewById(R.id.ivCateTotaliser);
        ivCateOriginal = findViewById(R.id.ivCateOriginal);
        btnCateDevice = findViewById(R.id.btnCateDevice);
        btnCateTotaliser = findViewById(R.id.btnCateTotaliser);
        btnCateOriginal = findViewById(R.id.btnCateOriginal);


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

        checkAndRequestPermissions();

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
                    }
                }
            }
        }
    }

}

package com.example.ccmyphone.OriginalFragments;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ccmyphone.R;

import java.io.IOException;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public class TorchFragment extends Fragment {

    android.hardware.Camera camera;
    android.hardware.Camera.Parameters parameters;
    private CameraManager cameraManager;
    private String cameraID;
    private Boolean isTorchOn;

    Button btnFlash, bntFlashbg, bntBlink;
    GradientDrawable bgShape;
    String myString = "010101010101";
    long blinkDelay = 50; //Delay in ms

    public TorchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_torch, container, false);

        isTorchOn = false;
        Boolean isFlashAvailable = getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isFlashAvailable) {
            Toast.makeText(getActivity(), "Flash Not Available", Toast.LENGTH_LONG).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
            try {
                cameraID = cameraManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        btnFlash = view.findViewById(R.id.bntFlash);
        bntFlashbg = view.findViewById(R.id.bntFlashbg);
        bntBlink = view.findViewById(R.id.bntBlink);

        bgShape = (GradientDrawable) bntFlashbg.getBackground();

        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFlashLight();
            }
        });

        bntBlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlinkFlash();
            }
        });


        return view;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void turnOnFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraID, true);
            } else {
                try {
                    parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(parameters);
                    camera.startPreview();
                } catch (RuntimeException e) {
                    Toast.makeText(getActivity(), "Camera Permission is not granted", Toast.LENGTH_SHORT).show();
                }
            }
            btnFlash.setText("OFF");
            btnFlash.setTextColor(getResources().getColor(R.color.Red_Dark_Lite));
            bgShape.setColor(getResources().getColor(R.color.Red_Dark_Lite));
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void turnOffFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraID, false);
            } else {
                try {
                    parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                    camera.stopPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Exception throws in turning off flashlight.", Toast.LENGTH_SHORT).show();
                }
            }
            btnFlash.setText("ON");
            btnFlash.setTextColor(getResources().getColor(R.color.Green_Dark_Lite));
            bgShape.setColor(getResources().getColor(R.color.Green_Dark_Lite));
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void toggleFlashLight() {
        try {
            if (isTorchOn) {
                turnOffFlashLight();
                isTorchOn = false;
            } else {
                turnOnFlashLight();
                isTorchOn = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void BlinkFlash() {
        for (int i = 0; i < myString.length(); i++) {
            if (myString.charAt(i) == '0') {
                turnOnFlashLight();
                isTorchOn = true;
            } else {
                turnOffFlashLight();
                isTorchOn = false;
            }
            try {
                Thread.sleep(blinkDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

package com.example.ccmyphone;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ccmyphone.Adapters.AdminUsersListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.example.ccmyphone.ApplicationConstants.DATABASE_REF_USERS;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public class AdminPanelActivity extends AppCompatActivity {

    String TAG = "AdminPanelActivity";

    ListView usersList;
    TextView panelUsersCount;

    DatabaseReference databaseReference;
    long usersCount;
    Gson gson = new Gson();
    ArrayList<String> listOfValues;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        usersList = findViewById(R.id.usersList);
        panelUsersCount = findViewById(R.id.panelUsersCount);

        databaseReference = DATABASE_REF_USERS;
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    usersCount = dataSnapshot.getChildrenCount();
                    panelUsersCount.setText(String.valueOf(usersCount));
                    String jsonStringFull = gson.toJson(dataSnapshot.getValue());
                    Log.d(TAG, "Full Users JSon " + jsonStringFull);
                    try {
                        JSONObject jsonObject = new JSONObject(jsonStringFull);
                        Log.d(TAG, "jsonObject " + jsonObject);
//                        JSONObject object = jsonObject.getJSONObject(jsonStringFull);
//                        Log.d(TAG, "object " + object);
                        Map<String, Integer> map = new HashMap<String, Integer>();
                        Iterator<String> keysItr = jsonObject.keys();
                        while (keysItr.hasNext()) {
                            String key = keysItr.next();
                            int value = jsonObject.length();
                            map.put(key, value);
                        }
                        Log.d(TAG, "map " + map);
                        Collection<String> values = map.keySet();
                        Log.d(TAG, "values " + values);
                        listOfValues = new ArrayList<String>(values);
                        Log.d(TAG, "listOfValues " + listOfValues);
                        AdminUsersListAdapter adapter = new AdminUsersListAdapter(getApplicationContext(), listOfValues);
                        usersList.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference database = DATABASE_REF_USERS.child(listOfValues.get(position));
                Log.d(TAG, "databaseREference " + database);
                final Dialog alertLayout = new Dialog(AdminPanelActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                alertLayout.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertLayout.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertLayout.setContentView(R.layout.userview_dialoglayout);

                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String userMobileData = dataSnapshot.child("userMobile").getValue().toString();
                            String userPasswordData = dataSnapshot.child("password").getValue().toString();
                            String userNameData = dataSnapshot.child("userName").getValue().toString();
                            String userDeviceNameData = dataSnapshot.child("deviceName").getValue().toString();
                            String userRegTimeData = dataSnapshot.child("registrationTime").getValue().toString();
                            String userLoginTimeData = dataSnapshot.child("loginTime").getValue().toString();
                            String userIsActiveData = dataSnapshot.child("active").getValue().toString();
                            String userIsLoggedInData = dataSnapshot.child("loggedIn").getValue().toString();
                            Log.d(TAG, "userDetails " + userMobileData + userNameData + userPasswordData);

                            TextView userViewMobile = alertLayout.findViewById(R.id.userViewMobile);
                            TextView userViewName = alertLayout.findViewById(R.id.userViewName);
                            ImageView userViewIcon = alertLayout.findViewById(R.id.userViewIcon);
                            TextView userViewPassword = alertLayout.findViewById(R.id.userViewPassword);
                            TextView usberViewDeviceName = alertLayout.findViewById(R.id.userViewDeviceName);
                            TextView userViewRegTime = alertLayout.findViewById(R.id.userViewRegTime);
                            TextView userViewLoginTime = alertLayout.findViewById(R.id.userViewLoginTime);
                            TextView userViewIsActive = alertLayout.findViewById(R.id.userViewIsActive);
                            TextView userViewIsLoggedIn = alertLayout.findViewById(R.id.userViewIsLoggedIn);

                            userViewMobile.setText(userMobileData);
                            userViewName.setText(userNameData);
                            userViewPassword.setText("Password: " + userPasswordData);
                            usberViewDeviceName.setText("Device Name: " + userDeviceNameData);
                            userViewRegTime.setText("Reg Time: " + userRegTimeData);
                            userViewLoginTime.setText("Login Time: " + userLoginTimeData);
                            userViewIsActive.setText("Is Active: " + userIsActiveData);
                            userViewIsLoggedIn.setText("Is LoggedIn: " + userIsLoggedInData);
                        } else {
                            Log.d(TAG, "dataSnapshot Not Exists... " + dataSnapshot + dataSnapshot.exists());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                alertLayout.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MasterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

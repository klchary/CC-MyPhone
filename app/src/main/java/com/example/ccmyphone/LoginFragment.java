package com.example.ccmyphone;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Date;
import java.util.Objects;

import static com.example.ccmyphone.ApplicationConstants.SHARED_PERSISTENT_VALUES;
import static com.example.ccmyphone.ApplicationConstants.USER_DETAILS;
import static com.example.ccmyphone.ApplicationConstants.DATABASE_REF_USERS;
import static com.example.ccmyphone.ApplicationConstants.USER_DETAILS_ALL;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    String TAG = "LoginFragment";

    CardView logoImageCardLay;
    ImageView logoimage;
    TextInputLayout tilPassword, tilMobile;
    EditText etMobile, etPassword;
    Button btnLogin;
    TextView toRegister, continueGuest;

    private DatabaseReference mDatabase;

    SharedPreferences userPreferences;
    SharedPreferences.Editor editor;

    RelativeLayout loginFrag_layout;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        FindAllViews(view);

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.BL_TR,
                new int[]{ContextCompat.getColor(getActivity(), R.color.Black),
                        ContextCompat.getColor(getActivity(), R.color.Blue),
                        ContextCompat.getColor(getActivity(), R.color.Red_Dark),
                        ContextCompat.getColor(getActivity(), R.color.Silver),
                        ContextCompat.getColor(getActivity(), R.color.Gray_Dark),
                        ContextCompat.getColor(getActivity(), R.color.White)});
        loginFrag_layout = view.findViewById(R.id.loginFrag_layout);
//        loginFrag_layout.setBackground(gradientDrawable);

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                RegisterFragment registerFragment = new RegisterFragment();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.LoginRegisterLayout, registerFragment).addToBackStack(null).commit();
            }
        });

        continueGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MasterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        mDatabase = DATABASE_REF_USERS;
        final Date date = new Date();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userMobile = etMobile.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(userMobile)) {
                            Toast.makeText(getActivity(), "User Found " + userMobile, Toast.LENGTH_LONG).show();
                            final DatabaseReference userData = mDatabase.child(userMobile);

                            userData.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String userMobileData = dataSnapshot.child("userMobile").getValue().toString();
                                    String userPasswordData = dataSnapshot.child("password").getValue().toString();
                                    String userNameData = dataSnapshot.child("userName").getValue().toString();
                                    String userRegTimeData = dataSnapshot.child("registrationTime").getValue().toString();
                                    Log.d(TAG, "UserData " + userMobileData + " " + userPasswordData);
                                    if (userMobile.equalsIgnoreCase(userMobileData) && password.equalsIgnoreCase(userPasswordData)) {
                                        USER_DETAILS_ALL.setUserMobile(userMobileData);
                                        USER_DETAILS_ALL.setUserName(userNameData);
                                        USER_DETAILS_ALL.setPassword(userPasswordData);
                                        USER_DETAILS_ALL.setLoggedIn(true);
                                        USER_DETAILS_ALL.setActive(true);
                                        USER_DETAILS_ALL.setDeviceName((Build.MODEL));
                                        USER_DETAILS_ALL.setLoginTime(date.toString());
                                        USER_DETAILS_ALL.setRegistrationTime(userRegTimeData);
                                        userData.setValue(USER_DETAILS_ALL).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "Login Details Successfully Inserted");
                                                } else {
                                                    Log.d(TAG, "Login Details Not Inserted");
                                                }
                                            }
                                        });
                                        userPreferences = getActivity().getSharedPreferences(SHARED_PERSISTENT_VALUES, Context.MODE_PRIVATE);
                                        editor = userPreferences.edit();
                                        Gson gson = new Gson();
                                        String userDetailsStr = gson.toJson(USER_DETAILS_ALL);
                                        editor.putString(USER_DETAILS, userDetailsStr);
                                        editor.apply();
                                        Intent intent = new Intent(getActivity(), MasterActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else if (!password.equalsIgnoreCase(userPasswordData)) {
                                        Toast.makeText(getActivity(), "Entered Wrong Password Please Try Again", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "User Not Found " + userMobile, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });


        return view;
    }

    public void FindAllViews(View view) {
        logoImageCardLay = view.findViewById(R.id.logoImageCardLay);
        logoimage = view.findViewById(R.id.logoimage);
        tilPassword = view.findViewById(R.id.tilPassword);
        tilMobile = view.findViewById(R.id.tilMobile);
        etMobile = view.findViewById(R.id.etMobile);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        toRegister = view.findViewById(R.id.toRegister);
        continueGuest = view.findViewById(R.id.continueGuest);
    }

}

package com.example.ccmyphone;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccmyphone.Models.UserDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import static com.example.ccmyphone.ApplicationConstants.IS_LOGGED_IN;
import static com.example.ccmyphone.ApplicationConstants.SHARED_PERSISTENT_VALUES;
import static com.example.ccmyphone.ApplicationConstants.TRUE;
import static com.example.ccmyphone.ApplicationConstants.USER_DETAILS;


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
    TextView toRegister;

    private DatabaseReference mDatabase;

    SharedPreferences userPreferences;
    SharedPreferences.Editor editor;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        FindAllViews(view);

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                RegisterFragment registerFragment = new RegisterFragment();
                fragmentTransaction.replace(R.id.LoginRegisterLayout, registerFragment).addToBackStack(null).commit();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("USERS");

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
                                    Log.d(TAG, "UserData " + userMobileData + " " + userPasswordData);
                                    if (userMobile.equalsIgnoreCase(userMobileData) && password.equalsIgnoreCase(userPasswordData)) {
                                        Intent intent = new Intent(getActivity(), MasterActivity.class);
                                        UserDetails userDetails = new UserDetails();
                                        userDetails.setUserMobile(userPasswordData);
                                        userDetails.setUserName(userNameData);
                                        userPreferences = getActivity().getSharedPreferences(SHARED_PERSISTENT_VALUES, Context.MODE_PRIVATE);
                                        editor = userPreferences.edit();
                                        Gson gson = new Gson();
                                        String userDetailsStr = gson.toJson(userDetails);
                                        editor.putString(USER_DETAILS, userDetailsStr);
                                        editor.putBoolean(IS_LOGGED_IN, TRUE);
                                        editor.apply();
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

    public void FindAllViews(View view){
        logoImageCardLay = view.findViewById(R.id.logoImageCardLay);
        logoimage = view.findViewById(R.id.logoimage);
        tilPassword = view.findViewById(R.id.tilPassword);
        tilMobile = view.findViewById(R.id.tilMobile);
        etMobile = view.findViewById(R.id.etMobile);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        toRegister = view.findViewById(R.id.toRegister);
    }

}
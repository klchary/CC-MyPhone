package com.ccapps.ccmyphone.myphone.LoginRegActFrag;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccapps.ccmyphone.myphone.Models.UserDetails;
import com.ccapps.ccmyphone.myphone.OtherClasses.Utils;
import com.ccapps.ccmyphone.myphone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import static com.ccapps.ccmyphone.myphone.ApplicationConstants.ALREADY_REG_ERROR;
import static com.ccapps.ccmyphone.myphone.ApplicationConstants.REG_ERROR;
import static com.ccapps.ccmyphone.myphone.ApplicationConstants.REG_SUCCESS;
import static com.ccapps.ccmyphone.myphone.ApplicationConstants.REG_TO_LOGIN;
import static com.ccapps.ccmyphone.myphone.ApplicationConstants.DATABASE_REF_USERS;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.ccapps.ccmyphone.myphone
 * Project Name CCMyPhone
 **/

public class RegisterFragment extends Fragment {

    String TAG = "RegisterFragment";

    CardView logoImageCardLay;
    ImageView logoimage;
    TextInputLayout tilPassword, tilConfirmPassword, tilMobile, tilUserName;
    EditText etMobile, etConfirmPassword, etPassword, etUserName;
    Button btnRegister;
    TextView toLogin;

    private DatabaseReference mDatabase;
    UserDetails userDetails;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        FindAllViews(view);

        mDatabase = DATABASE_REF_USERS;

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.LoginRegisterLayout, loginFragment).addToBackStack(null).commit();
            }
        });

        Date date = new Date();
        final String dateandTime = date.toString();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidRegistrationDetails()) {
                    String UserNameStr = etUserName.getText().toString().trim();
                    final String MobileNoStr = etMobile.getText().toString().trim();
                    String PasswordStr = etPassword.getText().toString().trim();
                    String ConfirmPasswordStr = etConfirmPassword.getText().toString().trim();

                    userDetails = new UserDetails();
                    userDetails.setUserName(UserNameStr);
                    userDetails.setUserMobile(MobileNoStr);
                    userDetails.setPassword(PasswordStr);
                    userDetails.setRegistrationTime(dateandTime);
                    userDetails.setDeviceName(Build.MODEL);
                    userDetails.setActive(false);
                    userDetails.setLoggedIn(false);
                    userDetails.setLoginTime("");

//                HashMap<String, String> userDetailsMap = new HashMap<String, String>();
//                userDetailsMap.put("userName", UserNameStr);
//                userDetailsMap.put("userMobile", MobileNoStr);
//                userDetailsMap.put("userPassword", PasswordStr);

                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(MobileNoStr)) {
                                AlertDialogRegistration(ALREADY_REG_ERROR);
                            } else {
                                mDatabase.child(MobileNoStr).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            AlertDialogRegistration(REG_SUCCESS);
                                        } else {
                                            AlertDialogRegistration(REG_ERROR);
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        return view;
    }

    private void AlertDialogRegistration(String errorMsg) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setIcon(R.drawable.app_image);
        builder.setTitle(R.string.app_name);
        builder.setMessage(errorMsg);
        builder.setPositiveButton(REG_TO_LOGIN, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toLogin.performLongClick();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected boolean isValidRegistrationDetails() {
        Utils utils = new Utils();

        String UserNameStr = etUserName.getText().toString().trim();
        String MobileNoStr = etMobile.getText().toString().trim();
        String PasswordStr = etPassword.getText().toString().trim();
        String ConfirmPasswordStr = etConfirmPassword.getText().toString().trim();

        // Check for a valid UserName, if the user entered one.
        boolean isValidName;
        if (TextUtils.isEmpty(UserNameStr)) {
            tilUserName.setError("Please Enter Name");
            isValidName = false;
        } else if (!utils.isNameValid(UserNameStr)) {
            tilUserName.setError("Name Charecters must be 3 - 15");
            isValidName = false;
        } else {
            isValidName = true;
        }

        // Check for a valid Mobile No.
        boolean isValidMobile;
        if (TextUtils.isEmpty(MobileNoStr)) {
            tilMobile.setError("Please Enter Valid Mobile Number");
            isValidMobile = false;
        } else if (!utils.isMobileValid(MobileNoStr)) {
            tilMobile.setError("Please Enter Your 10 digit Mobile Number");
            isValidMobile = false;
        } else {
            isValidMobile = true;
        }

        // Check for a valid Password.
        boolean isValidPassword;
        if (TextUtils.isEmpty(PasswordStr)) {
            tilPassword.setError("Password should not be blank");
            isValidPassword = false;
        } else if (!utils.isPasswordValid(PasswordStr)) {
            tilPassword.setError("Password must be 6 to 12 Charecters");
            isValidPassword = false;
        } else if (!PasswordStr.equals(ConfirmPasswordStr)) {
            tilPassword.setError("Password not Matched");
            tilConfirmPassword.setError("Password not Matched");
            isValidPassword = false;
        } else {
            isValidPassword = true;
        }

        return isValidName && isValidMobile && isValidPassword;
    }

    public void FindAllViews(View view) {
        logoImageCardLay = view.findViewById(R.id.logoImageCardLay);
        logoimage = view.findViewById(R.id.logoimage);
        tilPassword = view.findViewById(R.id.tilPassword);
        tilConfirmPassword = view.findViewById(R.id.tilConfirmPassword);
        tilUserName = view.findViewById(R.id.tilUserName);
        tilMobile = view.findViewById(R.id.tilMobile);
        etMobile = view.findViewById(R.id.etMobile);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        etUserName = view.findViewById(R.id.etUserName);
        btnRegister = view.findViewById(R.id.btnRegister);
        toLogin = view.findViewById(R.id.toLogin);
    }

}

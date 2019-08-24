package com.ccapps.ccmyphone.myphone.LoginRegActFrag;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ccapps.ccmyphone.myphone.R;

import java.util.Objects;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.ccapps.ccmyphone.myphone
 * Project Name CCMyPhone
 **/

public class LoginRegisterActivity extends AppCompatActivity {


    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = Objects.requireNonNull(this.getWindow());
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.MainColor));
        }

        loadLoginFragment();

    }

    @Override
    public void onBackPressed() {

        Fragment currentFragement = getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.LoginFragment));
        if (currentFragement != null && currentFragement.isVisible()) {
            finish();
        } else {
            loadLoginFragment();
        }

    }

    public void loadRegistrtionFragment() {
        RegisterFragment registerFragment = new RegisterFragment();
        fragmentManager.beginTransaction().add(R.id.LoginRegisterLayout, registerFragment, getResources().getString(R.string.RegisterFragment)).commit();
        FragmentTransaction Ctransaction = fragmentManager.beginTransaction();
        Ctransaction.replace(R.id.LoginRegisterLayout, registerFragment, getResources().getString(R.string.RegisterFragment)).addToBackStack(null).commit();
    }

    public void loadLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        fragmentManager.beginTransaction().add(R.id.LoginRegisterLayout, loginFragment, getResources().getString(R.string.LoginFragment)).commit();
        FragmentTransaction Ctransaction = fragmentManager.beginTransaction();
        Ctransaction.replace(R.id.LoginRegisterLayout, loginFragment, getResources().getString(R.string.LoginFragment)).addToBackStack(null).commit();
    }

}

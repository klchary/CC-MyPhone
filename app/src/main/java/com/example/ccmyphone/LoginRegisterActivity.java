package com.example.ccmyphone;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class LoginRegisterActivity extends AppCompatActivity {


    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

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

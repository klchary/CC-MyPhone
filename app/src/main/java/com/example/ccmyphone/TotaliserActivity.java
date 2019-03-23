package com.example.ccmyphone;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TotaliserActivity extends AppCompatActivity {

    static boolean totaliserActive = false;
    public static DrawerLayout totaliserDrawerLayout;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totaliser);

        totaliserDrawerLayout = findViewById(R.id.totaliserDrawerLayout);
        LoadSimpleCalculatorFragment();

    }

    public void LoadSimpleCalculatorFragment() {
        fragmentManager = getSupportFragmentManager();
        GeneralCalculatorFragment calculatorFragment = new GeneralCalculatorFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.beginTransaction().add(R.id.fragmentLayout, calculatorFragment, "GeneralCalculatorFragment").commit();
        fragmentTransaction.replace(R.id.fragmentLayout, calculatorFragment, "GeneralCalculatorFragment").addToBackStack(calculatorFragment.getClass().getName()).commit();
    }



    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        totaliserActive = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        totaliserActive = false;
    }
}

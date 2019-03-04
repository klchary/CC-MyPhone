package com.example.ccmyphone;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TotaliserActivity extends AppCompatActivity {

    static boolean totaliserActive = false;
    public static DrawerLayout totaliserDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totaliser);

        totaliserDrawerLayout = findViewById(R.id.totaliserDrawerLayout);




    }

    @Override
    public void onBackPressed() {
        if (totaliserActive) {
            Intent intent = new Intent(TotaliserActivity.this, DeviceInfoActivity.class);
            startActivity(intent);
        }
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

package com.example.ccmyphone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MasterActivity extends AppCompatActivity {

    Button btnDeviceAct, btnTotaliserAct, btnOriginalAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        btnDeviceAct = findViewById(R.id.btnDeviceAct);
        btnTotaliserAct = findViewById(R.id.btnTotaliserAct);
        btnOriginalAct = findViewById(R.id.btnOriginalAct);

        btnDeviceAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MasterActivity.this, DeviceInfoActivity.class);
                startActivity(intent);

            }
        });

        btnTotaliserAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MasterActivity.this, TotaliserActivity.class);
                startActivity(intent);

            }
        });

        btnOriginalAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MasterActivity.this, OriginalActivity.class);
                startActivity(intent);

            }
        });

    }
}

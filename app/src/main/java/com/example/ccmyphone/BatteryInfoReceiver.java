package com.example.ccmyphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryInfoReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // Get the battery scale
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        // Display the battery scale in TextView
//        battery.setText("Battery Scale : " + scale);
//        // get the battery level
//        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//        // Display the battery level in TextView
//        battery.setText(battery.getText() + "\nBattery Level : " + level);
//        // Calculate the battery charged percentage
//        float percentage = level / (float) scale;
////        Update the progress bar to display current battery charged percentage
//        mProgressStatus = (int) ((percentage) * 100);
//        // Show the battery charged percentage text inside progress bar
//        mTextViewPercentage.setText("" + mProgressStatus + "%");
//        // Show the battery charged percentage in TextView
//        mTextViewInfo.setText(mTextViewInfo.getText() + "\nPercentage : " + mProgressStatus + "%");
//        // Display the battery charged percentage in progress bar
//        mProgressBar.setProgress(mProgressStatus);
    }


}

package com.example.ccmyphone;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import static android.content.Context.NOTIFICATION_SERVICE;

public class CustomNotification extends BroadcastReceiver {

    private final String TAG = "CustomNotification";

    @Override
    public void onReceive(Context context, Intent intent) {

        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification_view);
        contentView.setImageViewResource(R.id.app_logo, R.mipmap.ic_launcher);
        contentView.setImageViewResource(R.id.notiTorch, R.drawable.ic_flash_black);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContent(contentView);
//                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notify));
//                .setStyle(bigText);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);

//        Intent intent1 = new Intent(getApplicationContext(), MasterActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
//        stackBuilder.addNextIntent(intent1);/.getPendingIntent(001, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pendingIntent1);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());

    }
}
package com.example.batterent.FCMServices;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData() != null)
//            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
//                sendNotificationAPI26(remoteMessage);
//            else
            sendNotification(remoteMessage);


    }

    private void sendNotification(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String content = data.get("content");

        // Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "Hritik";
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            //Only Active for Android 0 and Higher
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                                "Hritik Notification",
                                    NotificationManager.IMPORTANCE_MAX);
            //Config the channel
            notificationChannel.setDescription("Hritik channel for this App");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);



        }

        NotificationCompat.Builder  notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(android.support.v4.R.drawable.notification_icon_background)
                            .setTicker("Hearty365")
                        .setContentTitle(title)
                        .setContentText(content)
                        .setContentInfo("info");
        notificationManager.notify(1,notificationBuilder.build());
    }
}

package com.samplw;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.gleantap.GleanTapInitialize;
import com.gleantap.extras.Keys;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by amanjham on 27/10/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.println("Push Data "+remoteMessage.getData().toString());
        showNotification(remoteMessage);
    }

    private void showNotification(RemoteMessage remoteMessage) {

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Keys.campaign_id,remoteMessage.getData().get(Keys.campaign_id));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, getRandomNumber(1,100), intent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(MyFirebaseMessagingService.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setSound(sound)
                        .setContentTitle(remoteMessage.getData().get(Keys.title))
                        .setContentText(remoteMessage.getData().get(Keys.descriptions))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setColor(getResources().getColor(R.color.colorPrimaryDark));
// Gets an instance of the NotificationManager service
        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(R.mipmap.ic_launcher, mBuilder.build());
    }

    public int getRandomNumber(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}

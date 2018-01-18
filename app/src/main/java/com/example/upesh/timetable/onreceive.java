package com.example.upesh.timetable;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by upesh on 27/9/17.
 */

public class onreceive extends WakefulBroadcastReceiver {
    AudioManager am;
    @Override
    public void onReceive(final Context context, Intent intent) {

        NotificationManager alarmNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
          // this will make the notification to open schedule when click
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,new Intent(context,MainActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(context).setContentTitle("TIMETABLE").setSmallIcon(R.mipmap.app)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Hey you have a class today"))
                .setContentText("be ready for it ");
        alamNotificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
        alamNotificationBuilder.setContentIntent(contentIntent);   //thi will open the activity on ntification click
        alamNotificationBuilder.setAutoCancel(true);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());




    }
}
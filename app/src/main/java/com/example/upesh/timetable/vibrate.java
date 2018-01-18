package com.example.upesh.timetable;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by upesh on 27/9/17.
 */
//putting the phone to vibrate mode five minutes before the class start
public class vibrate extends WakefulBroadcastReceiver{
    private AudioManager myAudioManager;
    @Override
    public void onReceive(final Context context, Intent intent) {

        myAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

    }
}

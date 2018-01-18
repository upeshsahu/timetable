package com.example.upesh.timetable;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by upesh on 27/9/17.
 */
//this receiver will put your phone back on normal mode
public class ringing_mode extends WakefulBroadcastReceiver {
    private AudioManager myAudioManager;
    @Override
    public void onReceive(Context context, Intent intent) {
            myAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }
}

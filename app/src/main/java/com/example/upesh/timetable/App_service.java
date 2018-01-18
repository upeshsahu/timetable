package com.example.upesh.timetable;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by upesh on 10/10/17.
 */

public class App_service extends Service {
    static final public String COPA_RESULT = "com.controlj.copame.backend.COPAService.REQUEST_PROCESSED";
    static final public String COPA_MESSAGE = "com.controlj.copame.backend.COPAService.COPA_MSG";
    LocalBroadcastManager broadcaster2;
    public int counter=0;
    public SQLitehelper sqlitehelp;
    @Override
    public void onCreate() {
        sqlitehelp = new SQLitehelper(getApplicationContext(), "TIMETABLEDB", null, 1);
        sqlitehelp.qweryData("CREATE TABLE IF NOT EXISTS TIMETABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT,sub VARCHAR ,time VARCHAR,name VARCHAR,day VARCHAR)");
        broadcaster2= LocalBroadcastManager.getInstance(this);
        super.onCreate();
    }

    @Override


    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent i = new Intent(this, Mywidgetprovider.class);
        i.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        sendBroadcast(i);
        startTimer();
        Context context = this;
        super.onStartCommand(intent, flags, startId);
    return START_STICKY;
    }



    public void sendResult(String message) {
        Intent intent = new Intent(COPA_RESULT);
        if(message != null)
            intent.putExtra(COPA_MESSAGE, message);
        broadcaster2.sendBroadcast(intent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 1000*60*30,1000*30*60);
        //service goint to update all the ui and notification ,widgets after every 30 mins
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Intent i = new Intent(getApplicationContext(), Mywidgetprovider.class);
                i.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                sendBroadcast(i);

                //to main activity
                sendResult(String.valueOf(counter++));
                Log.i("in timer", "in timer ++++  "+ (counter++));
            }
        };
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}

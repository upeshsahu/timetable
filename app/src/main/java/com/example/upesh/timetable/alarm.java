package com.example.upesh.timetable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;

import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.CompoundButton;
import android.widget.Switch;

import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by upesh on 27/9/17.
 */

public class alarm extends  AppCompatActivity {


    public Calendar cal;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent1, pendingIntentv, pendingIntentnm,pendingIntendaily,pendingIntentmemo;
    public static String weekDay;
   BroadcastReceiver receiver;
    public static SQLitehelper sqlitehelp;
    public static data_sql_helper sql;
    String time_first, timelast;
    Date date1=null,date2=null,date3=null,date4=null;
     Switch simpleSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        simpleSwitch = (Switch) findViewById(R.id.switch2);





        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
         cal = Calendar.getInstance();
        weekDay = dayFormat.format(cal.getTime());
       Log.i("from alarm",weekDay);

        sqlitehelp = new SQLitehelper(alarm.this, "TIMETABLEDB", null, 1);
        sqlitehelp.qweryData("CREATE TABLE IF NOT EXISTS TIMETABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT,sub VARCHAR ,time VARCHAR,name VARCHAR,day VARCHAR,data VARCHAR)");
        sql = new data_sql_helper(this, "MEMORECORDDB", null, 1);
        sql.qweryData("CREATE TABLE IF NOT EXISTS MEMORECORD(Id INTEGER PRIMARY KEY AUTOINCREMENT,date VARCHAR ,time VARCHAR,data VARCHAR,sub VARCHAR,day VARCHAR)");


        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    Toast.makeText(alarm.this,"yu put the notification onn!",Toast.LENGTH_SHORT).show();
                }
                else
                {
//                            alarmManager.cancel(pendingIntent1);
//                            alarmManager.cancel(pendingIntentv);
//                            alarmManager.cancel(pendingIntentnm);
//                            alarmManager.cancel(pendingIntendaily);
//                            alarmManager.cancel(pendingIntentmemo);

                    Toast.makeText(alarm.this,"yu put the notification off!",Toast.LENGTH_SHORT).show(); }
                Log.v("Switch State=", ""+isChecked);

            }

        });

        //making notification before one hour of the first class
//
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        Cursor cursor;
//        try {
//             cursor = sqlitehelp.query("day", weekDay);
//            if (cursor.getCount() > 0)
//            {
//
//                cursor.moveToFirst();
//                time_first= cursor.getString(2);
//                date1 = sdf.parse(time_first);
//                int hour=date1.getHours();
//                int min=date1.getMinutes();
//
//
//
//                cursor.moveToLast();
//                timelast = cursor.getString(2);
//                date2 = sdf.parse(timelast);
//                int hour1=date2.getHours();
//                int min1=date2.getMinutes();
//
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(Calendar.HOUR_OF_DAY, hour);
//                calendar.set(Calendar.MINUTE, min);
//
//                Calendar lastcal = Calendar.getInstance();
//                lastcal.set(Calendar.HOUR_OF_DAY, hour1);
//                lastcal.set(Calendar.MINUTE, min1);
//
//
//                if (calendar.getTime().after(cal.getTime())) {
//                    Intent vibintent = new Intent(alarm.this, vibrate.class);
//                    pendingIntentv = PendingIntent.getBroadcast(alarm.this, (int) System.currentTimeMillis(), vibintent, pendingIntentv.FLAG_UPDATE_CURRENT);
//                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - 1000 * 60 * 5, alarmManager.INTERVAL_DAY, pendingIntentv);
//
//                    Intent normintent = new Intent(alarm.this, ringing_mode.class);
//                    pendingIntentnm = PendingIntent.getBroadcast(alarm.this, (int) System.currentTimeMillis(), normintent, pendingIntentnm.FLAG_UPDATE_CURRENT);
//                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, lastcal.getTimeInMillis() + 1000 * 60 * 5, alarmManager.INTERVAL_DAY, pendingIntentnm);
//
//                    Intent myIntent = new Intent(alarm.this, onreceive.class);
//                    pendingIntent1 = PendingIntent.getBroadcast(alarm.this, 0, myIntent, 0);
//                    alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis() - 1000 * 60 * 60, alarmManager.INTERVAL_DAY, pendingIntent1);
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "sorry time got passed", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(alarm.this, "NO class today", Toast.LENGTH_SHORT).show();
//            }
//
//
//
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "SORRY FOR INCONVINENCE!", Toast.LENGTH_SHORT).show();
//        }



        //for notification of class 5 mins before
//        Cursor c;
//        c = sqlitehelp.query("day", weekDay);
//
//        while (c.moveToNext()) {
//            if (c.getCount() > 0)
//            {
//                Calendar calendar = Calendar.getInstance();
//                try{date3=sdf.parse(c.getString(2));}
//                catch(Exception e){e.printStackTrace();}
//                calendar.set(Calendar.HOUR_OF_DAY, date3.getHours());
//                calendar.set(Calendar.MINUTE,date3.getMinutes());
//                if (calendar.getTime().after(cal.getTime())) {
//                    Intent dailyint = new Intent(alarm.this,onreceive.class);
//                    pendingIntendaily = PendingIntent.getBroadcast(alarm.this, (int) System.currentTimeMillis(), dailyint,pendingIntendaily.FLAG_UPDATE_CURRENT);
//                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - 1000 * 60 * 6, alarmManager.INTERVAL_DAY, pendingIntendaily);
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "sorry time got passed", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(alarm.this, "NO class today", Toast.LENGTH_SHORT).show();
//            }
//        }

//
//        //this will automatically delete your assignment after there deadline
//        sql.delete(cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR),cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
//        //setting alarm for assignment
//        Cursor c2;
//        try {
//            c2 = sql.query2(cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR));
//
//
//            while (c2.moveToNext()) {
//                if (c2.getCount() > 0) {
//                    Calendar calendar = Calendar.getInstance();
//                    try {
//                        date4 = sdf.parse(c2.getString(1));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    calendar.set(Calendar.HOUR_OF_DAY, date4.getHours());
//                    calendar.set(Calendar.MINUTE, date4.getMinutes());
//
//                    if (calendar.getTime().after(cal.getTime())) {
//                        Intent memoint = new Intent(alarm.this, onreceive.class);
//                        pendingIntentmemo = PendingIntent.getBroadcast(alarm.this, (int) System.currentTimeMillis(), memoint, pendingIntentmemo.FLAG_UPDATE_CURRENT);
//                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - 23* 3600 * 1000, alarmManager.INTERVAL_DAY, pendingIntentmemo);
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "sorry time got passed", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(alarm.this, "NO Assignment", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }catch (Exception e){ Toast.makeText(getApplicationContext(),"SORRY FOR THE INCONVIENCE",Toast.LENGTH_SHORT).show();}
//
//
//
//
//






    }


}


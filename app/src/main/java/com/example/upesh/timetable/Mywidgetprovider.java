package com.example.upesh.timetable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import static android.R.attr.name;

/**
 * Created by upesh on 10/10/17.
 */

public class Mywidgetprovider extends AppWidgetProvider {

    public String sub="",name="";
    public String state="no class";






    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

            super.onUpdate(context,appWidgetManager,appWidgetIds);

        // Get all ids
        ComponentName thisWidget = new ComponentName(context, Mywidgetprovider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {
             CurrentClass(context);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            // Set the text
            remoteViews.setTextViewText(R.id.txtsub,sub);
            remoteViews.setTextViewText(R.id.txtname,name);

            // Register an onClickListener
            Intent clickIntent = new Intent(context,
                    Mywidgetprovider.class);

            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.txtsub, pendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.txtname, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

    }


    @Override
    public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisAppWidget = new ComponentName(context.getPackageName(), Mywidgetprovider.class.getName());
                int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
            onUpdate(context,appWidgetManager,allWidgetIds);
            }
        super.onReceive(context, intent);
    }



    private void CurrentClass(Context context) {
        String weekay = "";
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        Calendar cal = Calendar.getInstance();
        weekay = dayFormat.format(cal.getTime());
         SQLitehelper sqlitehelp = new SQLitehelper(context, "TIMETABLEDB", null, 1);
         sqlitehelp.qweryData("CREATE TABLE IF NOT EXISTS TIMETABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT,sub VARCHAR ,time VARCHAR,name VARCHAR,day VARCHAR)");


        Cursor cursor;
        try {
            cursor = sqlitehelp.query_main(weekay, cal.getTime().getHours() + ":0");
            if (cursor.getCount() > 0) {
                cursor.moveToNext();
                sub=cursor.getString(1);
                name=cursor.getString(2);
            } else {
                sub=" HURRAY!";
                name=" no class";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


package com.example.upesh.timetable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    public Calendar cal;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent1, pendingIntentv, pendingIntentnm, pendingIntendaily, pendingIntentmemo;
    public static String weekDay = "";
    public static data_sql_helper sql;
    String time_first, timelast;
    Date date1 = null, date2 = null, date3 = null, date4 = null, parse = null;
    Button display, tab, listbtn, not, showmemo, addmemo, noti, edit, btndel, btndelmemo, btnupdate, showday, btnshare, btnaddclass,uc1,uc2,uc3,uc4,uc5;
    TextView sub, name, time;
    private show_dialog dial;
    public static SQLitehelper sqlitehelp;
    private PendingIntent pendingIntent, p1, p2;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    Toolbar toolbar;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //will link all the view with activity
        init();

        sqlitehelp = new SQLitehelper(this, "TIMETABLEDB", null, 1);
        sqlitehelp.qweryData("CREATE TABLE IF NOT EXISTS TIMETABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT,sub VARCHAR ,time VARCHAR,name VARCHAR,day VARCHAR)");
        sql = new data_sql_helper(this, "MEMORECORDDB", null, 1);
        sql.qweryData("CREATE TABLE IF NOT EXISTS MEMORECORD(Id INTEGER PRIMARY KEY AUTOINCREMENT,date VARCHAR ,time VARCHAR,data VARCHAR,sub VARCHAR,day VARCHAR)");



        //will update the service that upadte the main ui and widget
        Intent i = new Intent(this, App_service.class);
        i.putExtra("KEY1", "Value to be used by the service");
        this.startService(i);

       //will upadte the textview showing current class detail
        CurrentClass();
        //will call the notification
        alarm();






     //setting the navigation drawer
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.items_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        DataModel2[] drawerItem = new DataModel2[3];
        drawerItem[1] = new DataModel2(R.drawable.ic_share_, "share");
        drawerItem[2] = new DataModel2(R.drawable.ic_developer, "Developer");
        drawerItem[0] = new DataModel2(R.drawable.ic_info_black_24dp, "App info");

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();





        //code for button
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Update_using_list.class);
                startActivity(i);
            }
        });
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, alarm.class);
                startActivity(i);
            }
        });
        addmemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, memo_record.class));
            }
        });

        tab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                Intent i = new Intent(MainActivity.this, TabActivity.class);
                startActivity(i);
            }
        });

        showmemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Memo_display.class);
                startActivity(i);
            }
        });
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Deleting_using_list.class);
                startActivity(i);
            }
        });
        btndelmemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, delete_memo_record.class));
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Update_memo.class));

            }
        });
        showday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, daylist.class));
            }
        });

        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Share.class));
            }
        });

        btnaddclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, adding_table.class);
                startActivity(in);
            }
        });

        uc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"this is under construction be calm", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        uc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"this is under construction be calm", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        uc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "this is under construction be calm", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        uc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "this is under construction be calm", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        uc5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "this is under construction be calm", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });


    }


    //click listener for items in drawer
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Intent i = new Intent(this, Tabactivity.class);
        switch (position) {
            case 0:

                break;
            case 1:
                Uri uri = Uri.parse("https://www.google.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, Developer.class));
                break;
            default:
                break;
        }
    }


    void setupDrawerToggle() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
    }

    //in the action bar button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (id == R.id.action_like) {
            dial = new show_dialog();
            dial.dialogbox(MainActivity.this, " for your opinion! keep supporting us", "Thank You");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }


    //linking the menu file to main activity for the menu in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findmenuitems = getMenuInflater();
        findmenuitems.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }






    private void init() {
        tab = (Button) findViewById(R.id.button4);
        showmemo = (Button) findViewById(R.id.button11);
        edit = (Button) findViewById(R.id.btnedit);
        noti = (Button) findViewById(R.id.btnnotify);
        addmemo = (Button) findViewById(R.id.button6);
        btndel = (Button) findViewById(R.id.btndel);
        btndelmemo = (Button) findViewById(R.id.btndelmemo);
        btnupdate = (Button) findViewById(R.id.updatememo);
        showday = (Button) findViewById(R.id.showday);
        btnshare = (Button) findViewById(R.id.btnshare);
        btnaddclass = (Button) findViewById(R.id.addclass);
         uc1=(Button)findViewById(R.id.button444);
        uc2=(Button)findViewById(R.id.button445);
        uc3=(Button)findViewById(R.id.button446);
        uc4=(Button)findViewById(R.id.button447);
        uc5=(Button)findViewById(R.id.button448);

        sub = (TextView) findViewById(R.id.textsub);
        time = (TextView) findViewById(R.id.texttime);
        name = (TextView) findViewById(R.id.textname);
    }





    private void CurrentClass() {
        String weekay = "";
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        Calendar cal = Calendar.getInstance();
        weekay = dayFormat.format(cal.getTime());
        Cursor cursor;
        try {
            cursor = sqlitehelp.query_main(weekay, cal.getTime().getHours() + ":0");
            if (cursor.getCount() > 0)
            {
                cursor.moveToNext();
                Log.i("cu", cursor.getString(0) + cursor.getString(1));
                time.setText(cursor.getString(0));
                sub.setText(cursor.getString(1));
                name.setText(cursor.getString(2));
            } else
            {
                sub.setText(" HURRAY!");
                time.setText("No");
                name.setText("class");
            }
            } catch (Exception e) {
                                        e.printStackTrace();
            //Toast.makeText(this, "no data in table", Toast.LENGTH_SHORT).show();
        }                        }


    private void alarm() {

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        cal = Calendar.getInstance();
        weekDay = dayFormat.format(cal.getTime());

        Cursor cursor;
        try {
            cursor = sqlitehelp.query(weekDay);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                time_first = cursor.getString(3);
                date1 = sdf.parse(time_first);
                int hour = date1.getHours();
                int min = date1.getMinutes();

                cursor.moveToLast();
                timelast = cursor.getString(3);
                date2 = sdf.parse(timelast);
                int hour1 = date2.getHours();
                int min1 = date2.getMinutes();

//
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, min);
               // Log.i("from main", calendar.getTime().toString());
                Calendar lastcal = Calendar.getInstance();
                lastcal.set(Calendar.HOUR_OF_DAY, hour1);
                lastcal.set(Calendar.MINUTE, min1);
               // Log.i("from main", lastcal.getTime().toString());
//
                if (calendar.getTime().after(cal.getTime())) {
                    Intent vibintent = new Intent(this, vibrate.class);
                    pendingIntentv = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), vibintent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - 1000 * 60 * 5, alarmManager.INTERVAL_DAY, pendingIntentv);

                    Intent myIntent = new Intent(this, onreceive.class);
                    pendingIntent1 = PendingIntent.getBroadcast(this, 0, myIntent, 0);
                    alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis() - 1000 * 60 * 60, alarmManager.INTERVAL_DAY, pendingIntent1);
                }
                //for five mins after putting back phone to normal mode
                if (lastcal.getTime().after(cal.getTime())) {

                    Intent normintent = new Intent(this, ringing_mode.class);
                    pendingIntentnm = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), normintent, pendingIntentnm.FLAG_UPDATE_CURRENT);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, lastcal.getTimeInMillis() + 1000 * 60 * 5, alarmManager.INTERVAL_DAY, pendingIntentnm);

                                                              }
            }
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "SORRY FOR INCONVINENCE!", Toast.LENGTH_SHORT).show();
        }

        try {

        //this will automatically delete your assignment after there deadline
            sql.delete(cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR), cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
        } catch (Exception e) {
            e.printStackTrace();
        }


        //setting alarm for assignment
        Cursor c2;
        try {
            c2 = sql.query2(cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR));
            while (c2.moveToNext()) {

                String sub = c2.getString(4);
                String time = c2.getString(2);
                String date = c2.getString(1);
                String data = c2.getString(3);
                if (c2.getCount() > 0) {
                    Calendar calendar = Calendar.getInstance();
                    try {
                        date4 = sdf.parse(c2.getString(1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    calendar.set(Calendar.HOUR_OF_DAY, date4.getHours());
                    calendar.set(Calendar.MINUTE, date4.getMinutes());
                    if (calendar.getTime().after(cal.getTime())) {
                        Intent memoint = new Intent(this, onreceive.class);
                        pendingIntentmemo = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), memoint, pendingIntentmemo.FLAG_UPDATE_CURRENT);
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - 23 * 3600 * 1000, alarmManager.INTERVAL_DAY, pendingIntentmemo);

                    }
                }
                 }
        } catch (Exception e) {  //Toast.makeText(getApplicationContext(),"SORRY FOR THE INCONVIENCE2",Toast.LENGTH_SHORT).show();
             }
    }













    // receiving data from service to update current class schdule
    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(App_service.COPA_RESULT)
        );
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String s = intent.getStringExtra(App_service.COPA_MESSAGE);
            CurrentClass();
            alarm();
        }
    };

}











// filling the navigation drawer
    class DataModel2 {
        public int icon;
        public String name;

        // Constructor.
        public DataModel2(int icon, String name) {

            this.icon = icon;
            this.name = name;
        }
    }


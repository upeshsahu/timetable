package com.example.upesh.timetable;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by upesh on 22/9/17.
 */

public class adding_table extends AppCompatActivity {


    private int mHour, mMinute;
    TextView sub,name,time;
    Button add,daybtn;
    Spinner spinner;
    private static String str,namesha="",subshame="",timeshame="";
    private static final String[]paths = {"","Monday", "Tuesday", "Wednesday","Thursday","Friday"};
    public static SQLitehelper sqlitehelp;
    String preference_file_key="shared_it";
    SharedPreferences sharedPref;


    //the restore tha data from shared preferences
    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name",namesha);
        editor.putString("sub",subshame);
        editor.putString("time",timeshame);
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        getSupportActionBar().setTitle("ADD SCHEDULE");
        init();
        sharedPref= getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        String defaultValue = "";
        name.setText(sharedPref.getString("name", defaultValue));
        sub.setText(sharedPref.getString("sub", defaultValue));
        time.setText(sharedPref.getString("time", defaultValue));


        sqlitehelp = new SQLitehelper(this, "TIMETABLEDB", null, 1);
        sqlitehelp.qweryData("CREATE TABLE IF NOT EXISTS TIMETABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT,sub VARCHAR ,time VARCHAR,name VARCHAR,day VARCHAR)");


        //making spinner function on button click
        daybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.performClick();
            }
        });

        ArrayAdapter<String> adapterday = new ArrayAdapter<String>(adding_table.this,
                android.R.layout.simple_spinner_item, paths);
        adapterday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterday);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    str = adapterView.getItemAtPosition(i).toString();
                    daybtn.setText(str);
                    //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                } else {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "you have to select", Toast.LENGTH_SHORT).show();
            }

        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sqlitehelp.insertData(
                            sub.getText().toString().trim(),
                            time.getText().toString().trim(),
                            name.getText().toString().trim(),
                            str.trim()
                    );
                    Toast.makeText(getApplicationContext(), "added succesfully", Toast.LENGTH_SHORT).show();
                    subshame=sub.getText().toString();
                    namesha=name.getText().toString();
                    timeshame=time.getText().toString();

                    sub.setText("");
                    name.setText("");
                    daybtn.setText(" Select Day");
                    time.setText("");
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SELECT ALL", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final java.util.Calendar c = java.util.Calendar.getInstance();
                mHour = c.get(java.util.Calendar.HOUR_OF_DAY);
                mMinute = c.get(java.util.Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(adding_table.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                time.setText(hourOfDay + ":" + minute);
                            }
                        }, 0, 0, false);
                timePickerDialog.show();
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("day",str);
        outState.putString("name",name.getText().toString());
        outState.putString("sub",sub.getText().toString());
        outState.putString("time", time.getText().toString());
        super.onSaveInstanceState(outState);
    }


    private void init()
    {   sub=(TextView)findViewById(R.id.txtsub);
        time=(TextView)findViewById(R.id.txttime);
        name=(TextView)findViewById(R.id.txtname);
        add=(Button)findViewById(R.id.button3);
       spinner=(Spinner)findViewById(R.id.spinner2);
        daybtn=(Button)findViewById(R.id.button7);

    }


}



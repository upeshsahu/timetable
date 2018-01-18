package com.example.upesh.timetable;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by upesh on 28/9/17.
 */
//this class carry the record of all the
public class memo_record extends AppCompatActivity implements   View.OnClickListener {
    TextView sub,txtdate,txttime,textdata,day;
    Button add,daybtn;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static String str="Monday";
    public static data_sql_helper sql;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_record);

        init();
        textdata.setSingleLine(false);
        textdata.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        txtdate.setOnClickListener(this);
        txttime.setOnClickListener(this);
        sql = new data_sql_helper(this, "MEMORECORDDB", null, 1);
        sql.qweryData("CREATE TABLE IF NOT EXISTS MEMORECORD(Id INTEGER PRIMARY KEY AUTOINCREMENT,date VARCHAR ,time VARCHAR,data VARCHAR,sub VARCHAR,day VARCHAR)");

    add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                sql.insertData(
                        txtdate.getText().toString().trim(),
                        txttime.getText().toString().trim(),
                        textdata.getText().toString(),sub.getText().toString(),
                        str.trim()
                );
                Toast.makeText(getApplicationContext(), "Added Succesfully", Toast.LENGTH_SHORT).show();
                sub.setText("");
                txtdate.setText("");
                daybtn.setText(" SET DAY");
                txttime.setText("");
                textdata.setText("");
            } catch (Exception e)
            {
                //Toast.makeText(getApplicationContext(),"OOps! something went wrong",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    });

    }
  public void onClick(View v) {
        if (v ==txtdate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v ==txttime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txttime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    public void init() {

        sub = (TextView) findViewById(R.id.txtsub);
        txtdate = (TextView) findViewById(R.id.date);
        txttime = (TextView) findViewById(R.id.time);
        textdata = (TextView) findViewById(R.id.editText);
        add = (Button) findViewById(R.id.btn_add);
    }

}


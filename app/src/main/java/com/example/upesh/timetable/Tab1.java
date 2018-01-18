package com.example.upesh.timetable;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by upesh on 25/9/17.
 */
//this fragment is dispaly under tab activity representing day by time table
public class Tab1 extends Fragment {

    public static final String TAG = "Tab1Fragment";
    Context thiscontext;
     SQLitehelper sqlitehelp;
    ArrayList<Timetable> list;
    ListView lv;
    TextView dayname;
    Listadapter adapter1=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thiscontext = container.getContext();


        //extracting data from tab activity
        Bundle bundle = this.getArguments();
        String myValue = bundle.getString("message");

        View view = inflater.inflate(R.layout.tab1, container, false);

        //setting the title
        dayname=(TextView)view.findViewById(R.id.textView2);
        dayname.setText(myValue);

        lv=(ListView)view.findViewById(R.id.list);
        list=new ArrayList<>();

        adapter1=new Listadapter(thiscontext,R.layout.tablistdisplay,list);
        lv.setAdapter(adapter1);
          //we can use an object refrence of main activity instead of these two line
         sqlitehelp = new SQLitehelper(getContext(), "TIMETABLEDB", null, 1);
        sqlitehelp.qweryData("CREATE TABLE IF NOT EXISTS TIMETABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT,sub VARCHAR ,time VARCHAR,name VARCHAR,day VARCHAR)");



        Cursor cursor;
        try{
                   cursor=sqlitehelp.query("day",myValue);
                    list.clear();
                    while (cursor.moveToNext()){
                        String sub=cursor.getString(0);
                        String time=cursor.getString(2);
                        String name=cursor.getString(3);
                        String day=cursor.getString(1);

                        list.add(new Timetable(sub,time,name));
                    }adapter1.notifyDataSetChanged();
        } catch(Exception e)
                {
                //Toast.makeText(getActivity(),"oops! something went wrong ",Toast.LENGTH_SHORT).show();
                }







        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(final AdapterView<?> arg0,View arg1,
                                           final int position, long arg3) {
               final TextView sub=(TextView)arg1.findViewById(R.id.txtsub);
                final TextView name=(TextView)arg1.findViewById(R.id.txtname);
                final TextView day=(TextView)arg1.findViewById(R.id.txtday);
                final TextView time=(TextView)arg1.findViewById(R.id.txttime);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("do you really want to delete");
                builder1.setTitle("Delete");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //unable to call this method from fragment?
                                //sqlitehelp.delete_list(day.getText().toString(),time.getText().toString(),sub.getText().toString(),name.getText().toString());

                                Toast.makeText(thiscontext,"deleted",Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                adapter1.notifyDataSetChanged();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder1.show();
                return true;
            }
        });

        return view;}


}




















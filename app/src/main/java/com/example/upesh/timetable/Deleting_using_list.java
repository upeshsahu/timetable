package com.example.upesh.timetable;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by upesh on 7/10/17.
 */
//this class is for diplaying data and delete it from the list when delete button is pressed
public class Deleting_using_list extends AppCompatActivity {

    ArrayList<Timetable> list;
    ListView lv;
    private static SQLitehelper sqlitehelp;
    Non_tablist_adapter_del adapter1=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        sqlitehelp = new SQLitehelper(this, "TIMETABLEDB", null, 1);
        sqlitehelp.qweryData("CREATE TABLE IF NOT EXISTS TIMETABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT,sub VARCHAR ,time VARCHAR,name VARCHAR,day VARCHAR)");

        lv=(ListView)findViewById(R.id.list);
        list=new ArrayList<>();
        adapter1=new Non_tablist_adapter_del(this,R.layout.delete_using_list,list);
        lv.setAdapter(adapter1);
        ;
        Cursor cursor=sqlitehelp.getdata("SELECT * FROM TIMETABLE");
        list.clear();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String sub=cursor.getString(1);
            String time=cursor.getString(2);
            String name=cursor.getString(3);
            String day=cursor.getString(4);
            list.add(new Timetable(id,sub,time,name,day));
        }adapter1.notifyDataSetChanged();

    }


public class Non_tablist_adapter_del extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Timetable> foodlists;

    public Non_tablist_adapter_del(Context context, int layout, ArrayList<Timetable> foodlists) {
        this.context = context;
        this.layout = layout;
        this.foodlists = foodlists;
    }

    @Override
    public Object getItem(int position) {
        return foodlists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {

        TextView txtname, sub, txtday, txttime;
         Button del;
    }

    @Override
    public View getView(final int position, final View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.txtname = (TextView) row.findViewById(R.id.txtname);
            holder.txtday = (TextView) row.findViewById(R.id.txtday);
            holder.txttime = (TextView) row.findViewById(R.id.txttime);
            holder.sub = (TextView) row.findViewById(R.id.txtsub);
            holder.del = (Button) row.findViewById(R.id.del);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        final Timetable food = foodlists.get(position);
        holder.txtname.setText(food.getName());
        holder.sub.setText(food.getSub());
        holder.txtday.setText(food.getDay());
        holder.txttime.setText(food.getTime());

         //setting onclick listener on delbutton


        row.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view2) {
                    final View view=view2;
                 AlertDialog.Builder builder1 = new AlertDialog.Builder(Deleting_using_list.this);
                 builder1.setMessage("do you really want to delete");
                 builder1.setTitle("Delete");
                 builder1.setCancelable(true);

                 builder1.setPositiveButton(
                         "Yes",
                         new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                                sqlitehelp.delete_list(food.getDay(),food.getTime(),food.getSub(),food.getName());
                               String str= (food.getDay()+food.getTime()+food.getSub()+food.getName());
                                 Toast.makeText(Deleting_using_list.this,"Deleted",Toast.LENGTH_SHORT).show();
                                 foodlists.remove(position);
                                 notifyDataSetChanged();
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
             }
         });

        return row;
    }

    @Override
    public int getCount() {
        return foodlists.size();

    }
}}
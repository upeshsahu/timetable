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
//this class is to delete the project submitted by the user
public class delete_memo_record extends AppCompatActivity {
    ArrayList<Memo> list;
    ListView lv;
    public static data_sql_helper sql;
    adapter_memo2 adapter1 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memolistview);
        sql = new data_sql_helper(this, "MEMORECORDDB", null, 1);
        sql.qweryData("CREATE TABLE IF NOT EXISTS MEMORECORD(Id INTEGER PRIMARY KEY AUTOINCREMENT,date VARCHAR ,time VARCHAR,data VARCHAR,sub VARCHAR,day VARCHAR)");

        lv = (ListView) findViewById(R.id.list);
        list = new ArrayList<>();
        adapter1 = new adapter_memo2(this, R.layout.memo_record_del, list);
        lv.setAdapter(adapter1);
        Cursor cursor = sql.getdata("SELECT * FROM MEMORECORD");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String sub = cursor.getString(4);
            String time = cursor.getString(2);
            String date = cursor.getString(1);
            String day = cursor.getString(5);
            String data = cursor.getString(3);


            list.add(new Memo(id, date, time, data, sub, day));
        }
        adapter1.notifyDataSetChanged();


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, final View arg1,
                                           final int position, long arg3) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(delete_memo_record.this);
                builder1.setMessage("do you really want to delete");
                builder1.setTitle("Delete");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sql.delete(((TextView)arg1.findViewById(R.id.txtdate)).getText().toString(),((TextView)arg1.findViewById(R.id.txttime)).getText().toString());
                                Toast.makeText(delete_memo_record.this,"deleted",Toast.LENGTH_SHORT).show();
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

    }


    public class adapter_memo2 extends BaseAdapter {


        private Context context;
        private int layout;
        private ArrayList<Memo> foodlists;

        public adapter_memo2(Context context, int layout, ArrayList<Memo> foodlists) {
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

         Button del;
            TextView txtdate, txtsub, txtday, txttime, txtdata;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            View row = view;
            ViewHolder holder = new ViewHolder();
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder.txtdata = (TextView) row.findViewById(R.id.txtdata);
                holder.del=(Button) row.findViewById(R.id.del);
                holder.txttime=(TextView)row.findViewById(R.id.txttime);
                holder.txtsub = (TextView) row.findViewById(R.id.txtsub);
                holder.txtdate = (TextView) row.findViewById(R.id.txtdate);
                holder.del=(Button)row.findViewById(R.id.del);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            final Memo food = foodlists.get(position);
            holder.txtdata.setText(food.getData());
            holder.txtsub.setText(food.getSub());
            holder.txttime.setText(food.getTime());
            holder.txtdate.setText(food.getDate());

            row.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view2) {
                    final View view=view2;
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(delete_memo_record.this);
                    builder1.setMessage("do you really want to delete");
                    builder1.setTitle("Delete");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    sql.delete(food.getDate(),food.getTime());
                                    Toast.makeText(delete_memo_record.this,"deleted",Toast.LENGTH_SHORT).show();
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


    }
}

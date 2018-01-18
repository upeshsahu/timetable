package com.example.upesh.timetable;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by upesh on 8/10/17.
 */
//this activty update the memo on particular column click
public class Update_memo extends AppCompatActivity{
    ArrayList<Memo> list;
    ListView lv;
    final Context context = this;
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
        adapter1 = new adapter_memo2(this, R.layout.memo_card, list);
        lv.setAdapter(adapter1);

         try {
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
         }catch(Exception e){e.printStackTrace();}



        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, final View arg1,
                                           final int position, long arg3) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Update_memo.this);
                builder1.setMessage("press the value to edit ");
                builder1.setTitle("HELP!");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
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
                holder.txttime=(TextView)row.findViewById(R.id.txttime);
                holder.txtsub = (TextView) row.findViewById(R.id.txtsub);
                holder.txtdate = (TextView) row.findViewById(R.id.txtdate);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            final Memo food = foodlists.get(position);
            holder.txtdata.setText(food.getData());
            holder.txtsub.setText(food.getSub());
            holder.txttime.setText(food.getTime());
            holder.txtdate.setText(food.getDate());



            holder.txtdata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_memo.this);
                    builder.setTitle("Update");
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.edittext_dialog, null);
                    builder.setView(promptsView);
                    final EditText userInput = (EditText) promptsView.findViewById(R.id.txtinput);

                    builder.setPositiveButton("update assignment", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sql.update2("data", userInput.getText().toString().trim(),food.getDate(),food.getTime());
                            list.remove(position);
                            list.add(new Memo(food.getDate().trim(),food.getTime().trim(),userInput.getText().toString().trim(),food.getSub().trim()));
                            adapter1.notifyDataSetChanged();
                            Toast.makeText(Update_memo.this, "assignment is updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });

            holder.txttime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_memo.this);
                    builder.setTitle("Update");
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.edittext_dialog, null);
                    builder.setView(promptsView);
                    final EditText userInput = (EditText) promptsView.findViewById(R.id.txtinput);

                    builder.setPositiveButton("update Time", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sql.update2("time", userInput.getText().toString().trim(),food.getDate(),food.getTime());
                            list.remove(position);
                            list.add(new Memo(food.getDate().trim(),userInput.getText().toString().trim(),food.getData().trim(),food.getSub().trim()));
                            adapter1.notifyDataSetChanged();
                            Toast.makeText(Update_memo.this, "Data is updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });

            holder.txtdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_memo.this);
                    builder.setTitle("Update");
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.edittext_dialog, null);
                    builder.setView(promptsView);
                    final EditText userInput = (EditText) promptsView.findViewById(R.id.txtinput);

                    builder.setPositiveButton("update date of submission", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sql.update2("date", userInput.getText().toString().trim(),food.getDate(),food.getTime());
                            list.remove(position);
                            list.add(new Memo(userInput.getText().toString().trim(),food.getTime().trim(),food.getData().trim(),food.getSub().trim()));
                            adapter1.notifyDataSetChanged();
                            Toast.makeText(Update_memo.this, "assignment is updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });


            holder.txtsub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_memo.this);
                    builder.setTitle("Update");
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.edittext_dialog, null);
                    builder.setView(promptsView);
                    final EditText userInput = (EditText) promptsView.findViewById(R.id.txtinput);

                    builder.setPositiveButton("update sub ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sql.update2("sub", userInput.getText().toString().trim(),food.getDate(),food.getTime());
                            list.remove(position);
                            list.add(new Memo(food.getDate().trim(),food.getTime().trim(),food.getData().trim(),userInput.getText().toString().trim()));
                            adapter1.notifyDataSetChanged();
                            Toast.makeText(Update_memo.this, "assignment subject is updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
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

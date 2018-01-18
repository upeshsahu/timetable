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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by upesh on 7/10/17.
 */
//this class update the schedule
public class Update_using_list extends AppCompatActivity {
    ArrayList<Timetable> list;
    ListView lv;
    final Context context = this;
    Non_tablist_adapter adapter1=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        lv=(ListView)findViewById(R.id.list);
        list=new ArrayList<>();
        adapter1=new Non_tablist_adapter(this,R.layout.listdisplay,list);
        lv.setAdapter(adapter1);
        ;
        Cursor cursor=MainActivity.sqlitehelp.getdata("SELECT * FROM TIMETABLE");
        list.clear();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String sub=cursor.getString(1);
            String time=cursor.getString(2);
            String name=cursor.getString(3);
            String day=cursor.getString(4);


            list.add(new Timetable(id,sub,time,name,day));
        }adapter1.notifyDataSetChanged();


     lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, final View arg1,
                                           final int position, long arg3) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Update_using_list.this);
                builder1.setTitle("HELP");
                builder1.setMessage("select the column to edit");
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





public class Non_tablist_adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Timetable> foodlists;

    public Non_tablist_adapter(Context context, int layout, ArrayList<Timetable> foodlists) {
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

    private class ViewHolder{

        TextView txtname,txtsub,txtday,txttime,txtdata;
         Button btn;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View row=view;
        ViewHolder holder=new ViewHolder();
        if(row==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.txtname=(TextView)row.findViewById(R.id.txtname);
            holder.txtday=(TextView)row.findViewById(R.id.txtday);
            holder.txttime=(TextView)row.findViewById(R.id.txttime);
            holder.txtsub=(TextView)row.findViewById(R.id.txtsub);
            row.setTag(holder);
        }
        else
        {     holder=(ViewHolder)row.getTag();
        }  final Timetable food;
        food=foodlists.get(position);
        holder.txtname.setText(food.getName());
        holder.txtsub.setText(food.getSub());
        holder.txtday.setText(food.getDay());
        //holder.txtdata.setText(food.getData());
        holder.txttime.setText(food.getTime());

        holder.txtday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Update_using_list.this);
                                builder.setTitle("Update");
                                LayoutInflater li = LayoutInflater.from(context);
                                View promptsView = li.inflate(R.layout.edittext_dialog, null);
                                builder.setView(promptsView);
                                final EditText userInput = (EditText) promptsView.findViewById(R.id.txtinput);

                                builder.setPositiveButton("update day", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        MainActivity.sqlitehelp.update("day", userInput.getText().toString().trim(),food.getDay(),food.getTime());

                                        foodlists.remove(position);
                                        foodlists.add(new Timetable(food.getSub(),food.getTime(),food.getName(),userInput.getText().toString().trim()));
                                        notifyDataSetChanged();
                                        Toast.makeText(Update_using_list.this, "day name is updated", Toast.LENGTH_SHORT).show();
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
                    public void onClick(View view2) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Update_using_list.this);
                        builder.setTitle("Update");
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.edittext_dialog, null);
                        builder.setView(promptsView);
                        final EditText userInput = (EditText) promptsView.findViewById(R.id.txtinput);

                        builder.setPositiveButton("update subject", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.dismiss();
                                MainActivity.sqlitehelp.update("sub", userInput.getText().toString().trim(), food.getDay(), food.getTime());
                                foodlists.remove(position);
                                foodlists.add(new Timetable(userInput.getText().toString().trim(), food.getTime(), food.getName(),food.getDay()));
                                notifyDataSetChanged();
                                Toast.makeText(Update_using_list.this, "subject is updated", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();
                    } });



                        holder.txttime.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view2) {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(Update_using_list.this);
                                                        builder.setTitle("Update");
                                                        LayoutInflater li = LayoutInflater.from(context);
                                                        View promptsView = li.inflate(R.layout.edittext_dialog, null);
                                                        builder.setView(promptsView);
                                                        final EditText userInput = (EditText) promptsView.findViewById(R.id.txtinput);

                                                        builder.setPositiveButton("update time", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                                MainActivity.sqlitehelp.update("time", userInput.getText().toString().trim(),food.getDay(),food.getTime().trim());
                                                                list.remove(position);
                                                                list.add(new Timetable(food.getSub().trim(),userInput.getText().toString().trim(),food.getName().trim(),food.getDay().trim()));
                                                                Toast.makeText(Update_using_list.this, "time is updated", Toast.LENGTH_SHORT).show();
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

        holder.txtname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Update_using_list.this);
                builder.setTitle("Update");
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.edittext_dialog, null);
                builder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.txtinput);

                builder.setPositiveButton("update Name", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        MainActivity.sqlitehelp.update("name", userInput.getText().toString().trim(),food.getDay(),food.getTime().trim());
                        list.remove(position);
                        list.add(new Timetable(food.getSub().trim(),food.getTime().trim(),userInput.getText().toString().trim(),food.getDay().trim()));
                        Toast.makeText(Update_using_list.this, "Name is updated", Toast.LENGTH_SHORT).show();
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

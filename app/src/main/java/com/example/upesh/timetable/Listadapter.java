package com.example.upesh.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by upesh on 25/9/17.
 */

public class Listadapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Timetable> foodlists;

    public Listadapter(Context context, int layout, ArrayList<Timetable> foodlists) {
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
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row=view;
        ViewHolder holder=new ViewHolder();
        if(row==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.txtname=(TextView)row.findViewById(R.id.txtname);
          // holder.txtday=(TextView)row.findViewById(R.id.txtday);
            holder.txttime=(TextView)row.findViewById(R.id.txttime);
            holder.txtsub=(TextView)row.findViewById(R.id.txtsub);
           // holder.txtdata=(TextView)row.findViewById(R.id.txtdata);
            row.setTag(holder);
        }
        else
        {     holder=(ViewHolder)row.getTag();
        }  Timetable food=foodlists.get(position);
        holder.txtname.setText(food.getName());
        holder.txtsub.setText(food.getSub());
        //holder.txtday.setText(food.getDay());
        //holder.txtdata.setText(food.getData());
        holder.txttime.setText(food.getTime());
        return row;
    }
    @Override
    public int getCount() {
        return foodlists.size();
    }


}

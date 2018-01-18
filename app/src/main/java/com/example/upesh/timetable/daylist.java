package com.example.upesh.timetable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by upesh on 8/10/17.
 */
//this class is to represent all day in list and call the fragment for each day on item selection
public class daylist extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> stringArrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_list);
        listView = (ListView) findViewById(R.id.list_item);



        setData();
        adapter = new ListViewAdapter2(daylist.this, R.layout.day_list_view, stringArrayList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                selectItem(position);
            }});


    }
    private void selectItem(int position) {
        Intent i=new Intent(this,Tabactivity.class);
        switch (position) {

            case 0:    i.putExtra("day","Monday");
                startActivity(i);
                break;
            case 1: i.putExtra("day","Tuesday");
                startActivity(i);
                break;
            case 2:    i.putExtra("day","Wednesday");
                startActivity(i);
                break;
            case 3:
                i.putExtra("day","Thursday");
                startActivity(i);
                break;
            case 4:
                i.putExtra("day","Friday");
                startActivity(i);
                break;

            default:
                break;
        }
    }

    private void setData() {
        stringArrayList = new ArrayList<>();
        stringArrayList.add("Monday");
        stringArrayList.add("Tuesday");
        stringArrayList.add("Wednesday");
        stringArrayList.add("Thursday");
        stringArrayList.add("Friday");


    }


    public class ListViewAdapter2 extends ArrayAdapter<String> {
          Context context;

        private List<String> friendList;

        public ListViewAdapter2(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            this.context = context;
            this.friendList = objects;
        }

        @Override
        public int getCount() {
            return friendList.size();
        }

        @Override
        public String getItem(int position) {
            return friendList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            // If holder not exist then locate all view from UI file.
            if (convertView == null) {
                // inflate UI from XML file
                convertView = inflater.inflate(R.layout.day_list_view, parent, false);
                // get all UI view
                holder = new ViewHolder(convertView);
                // set tag for holder
                convertView.setTag(holder);
            } else {
                // if holder created, get tag from view
                holder = (ViewHolder) convertView.getTag();
            }

            holder.friendName.setText(getItem(position));

            //get first letter of each String item
            String firstLetter = String.valueOf(getItem(position).charAt(0));

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            //int color = generator.getColor(getItem(position));
            int color = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, color); // radius in px

            holder.imageView.setImageDrawable(drawable);

            return convertView;
        }

        private class ViewHolder {
            private ImageView imageView;
            private TextView friendName;

            public ViewHolder(View v) {
                imageView = (ImageView) v.findViewById(R.id.image_view);
                friendName = (TextView) v.findViewById(R.id.text);
            }
        }
    }


}

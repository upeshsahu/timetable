package com.example.upesh.timetable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//this class share the screen sort of timetable ,this also display schedule in tab format with floating sharing buttomn
public class Share extends AppCompatActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mViewPager = (ViewPager) findViewById(R.id.container);
        //the part of the screen to be capture
        final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        SectionsPagerAdapter adapter= new SectionsPagerAdapter(getSupportFragmentManager());
        setupViewPager(mViewPager);
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        fab.setImageResource(R.drawable.ic_share_);





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap b = takeScreenshot(rootView);
                shareIt(saveBitmap(b));
                  Snackbar.make(view, "this will share things display on screen", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }
//to share the scren short
    private void shareIt(File im) {
        Uri uri = Uri.fromFile(im);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "this is screenshot of my busy schdule,look i m so busy";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Busy!!!!");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    public Bitmap takeScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache();
    }

    public File saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
        return imagePath;
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter= new SectionsPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();

        bundle.putString("message","Monday" );
        Fragment fragInfo = new Tab1();
        fragInfo.setArguments(bundle);

        Bundle bundle2 = new Bundle();
        bundle2.putString("message","Tuesday" );
        Fragment fragInfo2 = new Tab1();
        fragInfo2.setArguments(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putString("message","Wednesday" );
        Fragment fragInfo3 = new Tab1();
        fragInfo3.setArguments(bundle3);

        Bundle bundle4 = new Bundle();
        bundle4.putString("message","Thursday" );
        Fragment fragInfo4 = new Tab1();
        fragInfo4.setArguments(bundle4);

        Bundle bundle5 = new Bundle();
        bundle5.putString("message","Friday" );
        Fragment fragInfo5 = new Tab1();
        fragInfo5.setArguments(bundle5);

        adapter.addFragmant(fragInfo,"Mon");
        adapter.addFragmant(fragInfo2,"Tues");
        adapter.addFragmant(fragInfo3,"Wed");
        adapter.addFragmant(fragInfo4,"Thr");
        adapter.addFragmant(fragInfo5,"Fri");
        viewPager.setAdapter(adapter);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentlist = new ArrayList<>();
        private final List<String> mFragmenttitlelist = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragmentlist.get(position);
        }

        public void addFragmant(Fragment fragment, String title) {
            mFragmentlist.add(fragment);
            mFragmenttitlelist.add(title);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mFragmentlist.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmenttitlelist.get(position);
        }

    }
}

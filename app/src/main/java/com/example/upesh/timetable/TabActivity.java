package com.example.upesh.timetable;
import android.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
//this is tab activity carrying days tab in it
public class TabActivity extends AppCompatActivity {

    public static final String TAG="TabActivity";

    private SectionsPagerAdapter mSectionPageadapter;
    private  ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);



        Log.d(TAG,"oncreate:starting.");

        SectionsPagerAdapter adapter= new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager=(ViewPager)findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        
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
        private final List<Fragment> mFragmentlist=new ArrayList<>();
        private final List<String>mFragmenttitlelist=new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragmentlist.get(position);
        }
        public void addFragmant(Fragment fragment,String title)
        { mFragmentlist.add(fragment);
            mFragmenttitlelist.add(title);}
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

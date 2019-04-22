package com.example.mhaidersaleem.bottom;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class check_out extends AppCompatActivity {
    private TabLayout mTabLayout;

    private int[] mTabsIcons = {
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_favouries,
            R.drawable.ic_dashboard_black_24dp};
    LinearLayout pro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int position = 0;
        Bundle extras = getIntent().getExtras();


        pro =(LinearLayout) findViewById(R.id.progressBar1);
        new Task().execute();
        // Setup the viewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);


        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }

            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }
        if(extras != null) {
            position = extras.getInt("page");
            //Toast.makeText(getApplicationContext(),"value:" + position,Toast.LENGTH_SHORT).show();
            viewPager = findViewById(R.id.view_pager);
            viewPager.setCurrentItem(position);

        }
    }
    class Task extends AsyncTask<String, Integer, Boolean> {
        protected void onPreExecute() {

            pro.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Boolean result) {
            pro.setVisibility(View.GONE);
            super.onPostExecute(result);
        }


        @Override
        protected Boolean doInBackground(String... strings) {
            return null;
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 3;

        private final String[] mTabsTitle = {"Recents", "Favourites", "Cart"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(check_out.this).inflate(R.layout.custom_tab, null);
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(mTabsTitle[position]);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(mTabsIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return ScreenSlidePageFragment.newInstance();
                case 1:
                    return PageFragment.newInstance(1);
                case 2:
                    return Product_Cart.newInstance(2);

            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }
    @Override
    public void onBackPressed() {
        Intent i= new Intent(this,MainActivity.class);
        i.putExtra("page",0);
        startActivity(i);
    }
}
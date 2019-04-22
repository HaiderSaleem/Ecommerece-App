package com.example.mhaidersaleem.bottom;


import android.annotation.TargetApi;
import android.app.*;
import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.multidex.MultiDex;
import android.support.v4.app.*;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout mTabLayout;
    android.support.v7.app.ActionBarDrawerToggle toggle;
    DrawerLayout drawer;


    private int[] mTabsIcons = {
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_favouries,
            R.drawable.ic_dashboard_black_24dp};
    LinearLayout pro;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int position = 0;
        Bundle extras = getIntent().getExtras();
        getWindow().setExitTransition(null);
        getWindow().setEnterTransition(null);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        android.support.v7.widget.Toolbar toolbar= findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //android.support.v7.app.ActionBar actionBar =  getSupportActionBar();


        toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.open_drawer,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);


        pro =(LinearLayout) findViewById(R.id.progressBar1);
        new Task().execute();
        // Setup the viewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);


        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        if (viewPager != null) {

            viewPager.setAdapter(pagerAdapter);
            viewPager.requestTransparentRegion(viewPager);
        }

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
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
        protected Boolean doInBackground(String... strings)
        {
            int progressStatus = 0;
            while (progressStatus < 500) {
                progressStatus++;
                publishProgress(progressStatus);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
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
                    new Task().execute();
                    return All_product.newInstance();
                case 1:
                    new Task().execute();
                    return PageFragment.newInstance(1);
                case 2:
                    new Task().execute();
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent i= new Intent(this,MainActivity.class);
            i.putExtra("page",0);
            startActivity(i);
            super.onBackPressed();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences.Editor editor = getSharedPreferences("Userdata", MODE_PRIVATE).edit();
            editor.putString("email", "");
            editor.putString("password", "");
            editor.apply();
            drawer.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //if (id == R.id.nav_camera) {
            // Handle the camera action
        //} else
         if (id == R.id.nav_account) {

             Intent i= new Intent(this,user_account.class);
             startActivity(i);


        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.fb) {
             Intent facebookAppIntent;
             try {
                 facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/203450243613824"));
                 startActivity(facebookAppIntent);
             } catch (ActivityNotFoundException e) {
                 facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/theurbanfitter"));
                 startActivity(facebookAppIntent);

             }

        } else if (id == R.id.wa) {
             Uri uri = Uri.parse("smsto:" + "+923244204629");
             Intent i = new Intent(Intent.ACTION_SENDTO, uri);
             //i.putExtra("sms_body", smsText);
             i.setPackage("com.whatsapp");
             startActivity(i);
         }
         else if(id == R.id.insta)
         {
             Uri uri = Uri.parse("http://instagram.com/theurbanfitter/");
             Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

             likeIng.setPackage("com.instagram.android");

             try {
                 startActivity(likeIng);
             } catch (ActivityNotFoundException e) {
                 startActivity(new Intent(Intent.ACTION_VIEW,
                         Uri.parse("http://instagram.com/theurbanfitter/")));
             }
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    }
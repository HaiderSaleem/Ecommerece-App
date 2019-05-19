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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener {
    private TabLayout mTabLayout;
    android.support.v7.app.ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    ListView list;
    static List<String> names= new ArrayList<>();
    static  DatabaseReference rootRef;
    static com.google.firebase.database.DatabaseReference imagesRef;
    static int count=0;
    ArrayAdapter mAdapter;


    private int[] mTabsIcons = {
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_favouries,
            R.drawable.ic_dashboard_black_24dp};
    LinearLayout pro;
    LinearLayout pro_layer;
    ProgressBar pro2;
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
        pro_layer = findViewById(R.id.pro_layer);

        rootRef = FirebaseDatabase.getInstance().getReference();
        imagesRef = rootRef.child("info");

        imagesRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    count = (int) dataSnapshot.getChildrenCount();
                    Log.d("count", "Count des " + count);

                    Log.d("Modafkr", "Count des " + count);
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        //Data_Images obj = ds.getValue(Data_Images.class);

                        String name = ds.child("name").getValue().toString();


                        names.add(name);



                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("db",databaseError.getMessage());
            }
        });


       mAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                names);
        /*final ImageView s1 = findViewById(R.id.s1);
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1.setVisibility(View.GONE);
                SearchView s = findViewById(R.id.searchView);
                s.setVisibility(View.VISIBLE);

            }
        });*/

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


        pro = findViewById(R.id.progressBar1);
        pro2 = findViewById(R.id.progressBar2);
        new Task().execute();
        // Setup the viewPager
        ViewPager viewPager =  findViewById(R.id.view_pager);


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

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "Query Inserted", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    class Task extends AsyncTask<String, Integer, Boolean> {
        protected void onPreExecute() {

            pro.setVisibility(View.VISIBLE);
            pro2.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Boolean result) {
            pro.setVisibility(View.GONE);
            pro2.setVisibility(View.GONE);
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
                    Thread.sleep(1/2);

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
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);

       /* if (!searchView.isIconified()) {
            Log.i("searchview","Working");
            searchView.onActionViewCollapsed();
            list.setVisibility(View.GONE);
            pro_layer.setVisibility(View.VISIBLE);
            super.onBackPressed();

        }*/
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
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    list = findViewById(R.id.list);
                    if(!searchView.isIconified())
                    {
                        Log.i("searchview","DWorking");
                        if(newText.length()>0) {
                            list.setVisibility(View.VISIBLE);
                            pro_layer.setVisibility(View.GONE);
                        }
                        else
                        {
                            list.setVisibility(View.GONE);
                            pro_layer.setVisibility(View.VISIBLE);
                            Log.i("searchview","DDone");
                        }
                    }
                    else if(searchView.isIconified() )
                    {
                        list.setVisibility(View.GONE);
                        pro_layer.setVisibility(View.VISIBLE);
                        Log.i("searchview","Done");
                    }
                    mAdapter.getFilter().filter(newText);
                    list.setAdapter(mAdapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(MainActivity.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                            list.setVisibility(View.GONE);
                            pro_layer.setVisibility(View.VISIBLE);
                        }
                    });



                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    list.setVisibility(View.GONE);
                    pro_layer.setVisibility(View.VISIBLE);

                    return true;
                }


            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return super.onCreateOptionsMenu(menu);
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
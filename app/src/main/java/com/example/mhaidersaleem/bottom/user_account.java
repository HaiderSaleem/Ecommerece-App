package com.example.mhaidersaleem.bottom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by M.HAiDER Saleem on 16/08/2018.
 */

public class user_account extends AppCompatActivity {

    android.support.v7.app.ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.user_account);
        super.onCreate(savedInstanceState);
        DrawerLayout drawer= findViewById(R.id.drawers_lay);

        android.support.v7.widget.Toolbar toolbar= findViewById(R.id.toolbar);

         setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        TextView name= findViewById(R.id.fname);
        TextView email= findViewById(R.id.email);
        TextView address= findViewById(R.id.uaddress);
        TextView contact= findViewById(R.id.contact);
        DatabaseHandler db= new DatabaseHandler(getApplicationContext());
        SharedPreferences prefs = this.getSharedPreferences("Userdata", Context.MODE_PRIVATE);
        String mail= prefs.getString("email","abc@gmail.com");
        Data_user obj =  db.get_user_data(mail);

        name.setText(obj.getName());
        email.setText(obj.getEmail());
        address.setText(obj.getAddress());
        contact.setText(obj.getNumber());

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(this,MainActivity.class);
        i.putExtra("page",0);
        startActivity(i);
        super.onBackPressed();
    }
}

package com.example.mhaidersaleem.bottom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by M.HAiDER Saleem on 13/08/2018.
 */

public class Checkout extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       /* SharedPreferences.Editor editor = getSharedPreferences("Userdata", MODE_PRIVATE).edit();
        editor.putString("name", "Elena");
        editor.putInt("idName", 12);
        editor.apply();*/
        String name;
        String password;

        SharedPreferences prefs = getSharedPreferences("Userdata", MODE_PRIVATE);

        if (!prefs.getString("email","").equals("")) {
            Toast.makeText(getApplicationContext(),"Data "+ prefs.getString("email","").toString(),Toast.LENGTH_SHORT).show();
            name = prefs.getString("email", "abc");//"No name defined" is the default value.
            password = prefs.getString("password", "null"); //0 is the default value.
            Toast.makeText(getApplicationContext(),"Values: "+ name + password ,Toast.LENGTH_SHORT).show();
            if(name.equals(""))
            {
                Intent i= new Intent(this,SignUp.class);
                startActivity(i);
            }
            else
            {
                DatabaseHandler db= new DatabaseHandler(getApplicationContext());
                int id = db.check_user_data(name);
                if (id != -1 && id != -2) {
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("page", 2);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(this, user_address.class);

                    startActivity(i);
                }
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Sign_Up" ,Toast.LENGTH_SHORT).show();

            Intent i= new Intent(this,SignUp.class);
            startActivity(i);
        }
        super.onCreate(savedInstanceState);
    }
}

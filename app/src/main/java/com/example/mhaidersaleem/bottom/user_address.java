package com.example.mhaidersaleem.bottom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by M.HAiDER Saleem on 14/08/2018.
 */

public class user_address extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.user_data);
        super.onCreate(savedInstanceState);
        final DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        final SharedPreferences prefs = getSharedPreferences("Userdata", MODE_PRIVATE);
        String email = prefs.getString("email", "abc");

        int id = db.check_user_data(email);
        if (id != -1 && id != -2) {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("page", 2);
            startActivity(i);
        }
        else {
            //user data

            final EditText name = findViewById(R.id.user_name);
            final EditText address = findViewById(R.id.address);
            final EditText city = findViewById(R.id.city);
            final EditText state = findViewById(R.id.state);
            final EditText phone = findViewById(R.id.phone_no);

            Button submit = findViewById(R.id.user_submit);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String email = prefs.getString("email", "abc");//"No name defined" is the default value.
                    String password = prefs.getString("password", "null"); //0 is the default value.

                    db.add_user_data(email, name.getText().toString(), password, address.getText().toString(), city.getText().toString(), state.getText().toString(), phone.getText().toString());

                    Intent i = new Intent(user_address.this, MainActivity.class);
                    i.putExtra("page", 2);
                    startActivity(i);

                }
            });
        }
    }
}

package com.example.mhaidersaleem.bottom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by M.HAiDER Saleem on 14/08/2018.
 */

public class SignUp extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.sign_up);

        super.onCreate(savedInstanceState);

        final TextView email = findViewById(R.id.email);
        final TextView password = findViewById(R.id.password);
        final Button sign_up = findViewById(R.id.signup);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("Userdata", MODE_PRIVATE).edit();
                editor.putString("email", email.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.apply();
                DatabaseHandler db= new DatabaseHandler(getApplicationContext());
                db.update_email(email.getText().toString());
                Intent i= new Intent(SignUp.this,user_address.class);
                startActivity(i);

            }
        });
    }
}

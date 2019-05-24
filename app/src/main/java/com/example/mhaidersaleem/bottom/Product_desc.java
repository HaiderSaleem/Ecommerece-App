package com.example.mhaidersaleem.bottom;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import  com.google.firebase.database.ValueEventListener;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.core.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by M.HAiDER Saleem on 03/08/2018.
 */


public class Product_desc extends Activity {
    ImageView img;
    int count1=0;

    static List<String> imgurl = new ArrayList<>();
    static int count=0;
    String path;
    String check_intent;
    String price;
    int page_no=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.product_desc);
        super.onCreate(savedInstanceState);
        LinearLayout desc_pro = findViewById(R.id.desc_pro);
        //screen size

        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //end here

        //Favourite Button
        final Button favourite = new Button(getApplicationContext());
        String imageID = "favourite";
        int imagerID = getResources().getIdentifier(imageID, "id", "com.example.mhaidersaleem.bottom");
        favourite.setId(imagerID);
        LinearLayout.LayoutParams fv = new LinearLayout.LayoutParams(width/6,height/20);
        favourite.setBackground(Drawable.createFromPath("ic_favourite"));
        favourite.setLayoutParams(fv);
        //end here

        //Product Image

        
        desc_pro.addView(favourite);

        img = findViewById(R.id.product_image);
        final TextView name= findViewById(R.id.pro_name);
        TextView desc= findViewById(R.id.textView5);

        Intent rcv= getIntent();

        path = rcv.getStringExtra("path");
        final String pname = rcv.getStringExtra("name");
        final String description = rcv.getStringExtra("des");
        final String key = rcv.getStringExtra("key");
         check_intent = rcv.getStringExtra("activity_name");
         price= rcv.getStringExtra("price");
         page_no= rcv.getIntExtra("page",0);

       name.setText(pname);
       desc.setText(description);
       imgurl.add(path);

        Glide.with(Product_desc.this)
                .load(path)
                .into(img);
        if(key!="0")
            getRef(key);

         //final Button favourite= findViewById(R.id.favourite);


        final DatabaseHandler obj= new DatabaseHandler(getApplicationContext());
        final SharedPreferences prefs = getSharedPreferences("Userdata", MODE_PRIVATE);
        String email;
        if (!prefs.getString("email","").equals("")) {

            email = prefs.getString("email", "");//"No name defined" is the default value.
        }
        else
            email="abc@gmail.com";

        int check1=obj.check(email,key);
        if(check1!=-1 && check1!=-2 ) {
            favourite.setBackgroundResource(R.mipmap.ic_fav);
        }



        //add_to_favourite
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email;
                String password;
                int check;
                if (!prefs.getString("email","").equals("")) {

                   email = prefs.getString("email", "");//"No name defined" is the default value.
                   password = prefs.getString("password", "null"); //0 is the default value.
                    check =obj.check(email,key);
                    if (check == -1 || check == -2) {
                        obj.add_favourite(email, pname, path, description, key, price);
                        favourite.setBackgroundResource(R.mipmap.ic_fav);
                        Log.d("fav", "Working");
                    } else {
                        obj.delete_cv(key);
                        favourite.setBackgroundResource(R.drawable.ic_favourite);
                        Log.d("fav del", "Deleted");
                    }
                }
                else {
                    check = obj.check("abc@gmail.com", key);
                    if (check == -1 || check == -2) {
                        obj.add_favourite("abc@gmail.com", pname, path, description, key, price);
                        favourite.setBackgroundResource(R.mipmap.ic_fav);
                        Log.d("fav", "Working");
                    } else {
                        obj.delete_cv(key);
                        favourite.setBackgroundResource(R.drawable.ic_favourite);
                        Log.d("fav del", "Deleted");
                    }
                }

            }
        });

        //add_to_cart

        final Button cart = findViewById(R.id.add_cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("Userdata", MODE_PRIVATE);
                String email;
                int ret;
                if (!prefs.getString("email","").equals("")) {

                    email = prefs.getString("email", "");//"No name defined" is the default value.
                    //password = prefs.getString("password", "null"); //0 is the default value.
                    ret=obj.check_cart_count(email,key);
                    if (ret < 1) {
                        count1++;

                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                        obj.add_cart(email, pname, path, description, key, count1, price);
                    } else {
                        ret++;
                        int prices = ret * Integer.valueOf(price);
                        obj.update_cart(email, key, ret, String.valueOf(prices));
                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    ret = obj.check_cart_count("abc@gmail.com", key);
                    if (ret < 1) {
                        count1++;

                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                        obj.add_cart("abc@gmail.com", pname, path, description, key, count1, price);
                    } else {
                        ret++;
                        int prices = ret * Integer.valueOf(price);
                        obj.update_cart("abc@gmail.com", key, ret, String.valueOf(prices));
                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        //Toast.makeText(getApplicationContext(),"path "+ path,Toast.LENGTH_SHORT).show();


    }

    public void getRef(final String key) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query ref = firebaseDatabase.getReference().child("more_pic").orderByChild("key").equalTo(key);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                count = (int) dataSnapshot.getChildrenCount();
                Log.d("proc count",key );

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    more_image obj = ds.getValue(more_image.class);

                     String skey = obj.get_key();
                     String url = obj.get_path();
                    imgurl.add(url);


                }
                Log.d("pro count",String.valueOf(count));
                set_images();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void set_images() {
        try {

            int counter = 1;
            int c = 0;
            count=2;

            while (c < count) {

                final String buttonID = "img" + counter;

                int resID = getResources().getIdentifier(buttonID, "id", "com.example.mhaidersaleem.bottom");
                final ImageView im = findViewById(resID);
                final String spath = imgurl.get(c).toString();
                Glide.with(Product_desc.this)
                        .load(spath)
                        .into(im);
                im.setScaleType(ImageView.ScaleType.FIT_XY);
                Log.d("pro count1",imgurl.get(c).toString());
                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getActivity(),buttonID +"id: "+ index,Toast.LENGTH_SHORT).show();
                        Glide.with(Product_desc.this)
                                .load(spath)
                                .into(img);


                    }
                });
                c++;
                counter++;

                //Log.d("List Size", String.valueOf(imgurl.size()));

            }

        } catch (Exception ex) {

        }

    }

    @Override
    public void onBackPressed() {
        imgurl.clear();


            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("page",page_no);
            startActivity(i);
            this.finish();

        super.onBackPressed();
    }

}

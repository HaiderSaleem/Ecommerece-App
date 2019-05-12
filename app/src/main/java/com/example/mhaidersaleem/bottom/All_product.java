package com.example.mhaidersaleem.bottom;

/**
 * Created by M.HAiDER Saleem on 31/07/2018.
 */

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.FontsContract;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class All_product extends Fragment {

    //private ImageView img1;
    static List<String> imgurl= new ArrayList<>();
    static List<String> names= new ArrayList<>();
    static List<String> des= new ArrayList<>();
    static List<String> key= new ArrayList<>();
    static List<String> prices= new ArrayList<>();
    static int count=0;
    static  DatabaseReference rootRef;
    static  DatabaseReference imagesRef;



    public static All_product newInstance() {

        All_product fragment = new All_product();

        return fragment;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.all_product, container, false);

        rootRef = FirebaseDatabase.getInstance().getReference();
        imagesRef = rootRef.child("info");

        imagesRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    count = (int) dataSnapshot.getChildrenCount();
                    Log.d("count", "Count des " + String.valueOf(count));

                    Log.d("Modafkr", "Count des " + String.valueOf(count));
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        //Data_Images obj = ds.getValue(Data_Images.class);
                        String price = ds.child("price").getValue().toString();
                        String name = ds.child("name").getValue().toString();
                        String url = ds.child("path").getValue().toString();
                        String desc = ds.child("description").getValue().toString();
                        String keys = ds.child("key").getValue().toString();
                        imgurl.add(url);
                        names.add(name);
                        des.add(desc);
                        key.add(keys);
                        prices.add(price);

                        Log.d("count", url + String.valueOf(count));
                    }
                    set_images(rootView);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("db",databaseError.getMessage());
            }
        });




        return rootView;
    }

    public void set_images(View rootView )
    {
        try {

            int counter=2;
            int c=0;
            LinearLayout main= rootView.findViewById(R.id.main);
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;


            while(c<count) {
                Log.d("counter",String.valueOf(c));
                //Dynamically LinearLayout
                LinearLayout sub_liner = new LinearLayout(getContext());
                LinearLayout.LayoutParams sub_params = new LinearLayout.LayoutParams(width, height/3);

                sub_liner.setLayoutParams(sub_params);
                sub_liner.setOrientation(LinearLayout.HORIZONTAL);

                //Dynamically sub Layoutfor 2 pics at once
                CardView sub_1_liner = new CardView(getContext());
                LinearLayout.LayoutParams sub_1_params = new LinearLayout.LayoutParams(width/2, LinearLayout.LayoutParams.WRAP_CONTENT);
                sub_1_params.rightMargin=5;
                sub_1_params.leftMargin=5;
                sub_1_liner.setLayoutParams(sub_1_params);

                //sub_1_liner.setOrientation(LinearLayout.VERTICAL);
                CardView sub_2_liner = new CardView(getContext());
                LinearLayout.LayoutParams sub_2_params = new LinearLayout.LayoutParams(width/2, LinearLayout.LayoutParams.WRAP_CONTENT);
                sub_2_params.leftMargin=5;
                sub_2_params.rightMargin=5;
                sub_2_liner.setLayoutParams(sub_2_params);

                //sub_2_liner.setOrientation(LinearLayout.VERTICAL);


                //Dynamically image1
                ImageView img1= new ImageView(getContext());

                LinearLayout.LayoutParams img_1_params = new LinearLayout.LayoutParams(width/2, height/4);
                img1.setLayoutParams(img_1_params);

                //Price+Name TEXT1
                TextView txt1= new TextView(getContext());
                LinearLayout.LayoutParams txt_1_params = new LinearLayout.LayoutParams(width/2, height/15);
                txt_1_params.leftMargin=40;

                txt1.setLayoutParams(txt_1_params);

                TextView name1= new TextView(getContext());
                LinearLayout.LayoutParams name_1_params = new LinearLayout.LayoutParams(width/2, height/18);
                name_1_params.leftMargin=40;
                name_1_params.topMargin=(height/4)-10;
                name1.setLayoutParams(name_1_params);



                //Dynamically image2
                ImageView img2= new ImageView(getContext());

                LinearLayout.LayoutParams img_2_params = new LinearLayout.LayoutParams(width/2, height/4);

                img2.setLayoutParams(img_2_params);

                //Price+ NAME TEXT2

                TextView txt2= new TextView(getContext());
                LinearLayout.LayoutParams txt_2_params = new LinearLayout.LayoutParams(width/2, height/15);
                txt_2_params.leftMargin=40;
                txt2.setLayoutParams(txt_2_params);

                final TextView name2= new TextView(getContext());
                LinearLayout.LayoutParams name_2_params = new LinearLayout.LayoutParams(width/2, height/18);
                name_2_params.leftMargin=40;
                name_2_params.topMargin=(height/4)-10;
                name2.setLayoutParams(name_2_params);


                //image1 view

                final String buttonID = "imageView"+counter ;


                int resID = getResources().getIdentifier(buttonID, "id","com.example.mhaidersaleem.bottom");
                img1.setId(resID);
                final String spath = imgurl.get(c);
                final String sname = names.get(c);
                final String sdes  = des.get(c);
                final String keyss = key.get(c);
                final String price = prices.get(c);
                 Glide.with(All_product.this)
                        .load(spath)
                        .into(img1);
                img1.setScaleType(ImageView.ScaleType.FIT_XY);
                txt1.setText("RS: "+price);
                name1.setText(sname);
                name1.setTypeface(Typeface.create("sans-serif-medium",Typeface.NORMAL));



                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getActivity(),buttonID +"id: "+ index,Toast.LENGTH_SHORT).show();
                        Intent pro_des= new Intent(view.getContext(),Product_desc.class);
                        pro_des.putExtra("path",spath);
                        pro_des.putExtra("name",sname);
                        pro_des.putExtra("des",sdes);
                        pro_des.putExtra("key",keyss);
                        pro_des.putExtra("activity_name","home");
                        pro_des.putExtra("price",price);
                        pro_des.putExtra("page",0);
                        Log.d("count1",keyss);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(All_product.this).attach(All_product.this).commit();
                        startActivity(pro_des);
                    }
                });

                sub_1_liner.addView(img1);
                sub_1_liner.addView(name1);
                sub_1_liner.addView(txt1);

                //image2 view
                c++;
                if(c<count) {



                    final String buttonID2 = "imageView2" + counter;


                    int resID2 = getResources().getIdentifier(buttonID2, "id", "com.example.mhaidersaleem.bottom");
                    img2.setId(resID2);
                    final String spath2 = imgurl.get(c);
                    final String sname2 = names.get(c);
                    final String sdes2 = des.get(c);
                    final String keyss2 = key.get(c);
                    final String price2 = prices.get(c);
                    Glide.with(All_product.this)
                            .load(spath2)
                            .into(img2);
                    img2.setScaleType(ImageView.ScaleType.FIT_XY);
                    txt2.setText("RS: "+price2);
                    name2.setText(sname2);
                    name2.setTypeface(Typeface.create("sans-serif-medium",Typeface.NORMAL));


                    img2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(getActivity(),buttonID +"id: "+ index,Toast.LENGTH_SHORT).show();
                            Intent pro_des = new Intent(view.getContext(), Product_desc.class);
                            pro_des.putExtra("path", spath2);
                            pro_des.putExtra("name", sname2);
                            pro_des.putExtra("des", sdes2);
                            pro_des.putExtra("key", keyss2);
                            pro_des.putExtra("activity_name", "home");
                            pro_des.putExtra("price",price2);
                            pro_des.putExtra("page", 0);
                            Log.d("count1", keyss2);
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(All_product.this).attach(All_product.this).commit();
                            startActivity(pro_des);

                        }
                    });

                    sub_2_liner.addView(img2);
                    sub_2_liner.addView(name2);
                    sub_2_liner.addView(txt2);
                    sub_liner.addView(sub_2_liner);
                }

                sub_liner.addView(sub_1_liner);
                main.addView(sub_liner);



                counter+=2;
                c++;


            }



        }
        catch (Exception ex)
        {
            Log.d("Loop",ex.toString());
        }

    }
}
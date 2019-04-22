package com.example.mhaidersaleem.bottom;

/**
 * Created by M.HAiDER Saleem on 31/07/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ScreenSlidePageFragment extends Fragment {

    private ImageView img1;
    static List<String> imgurl= new ArrayList<>();
    static List<String> names= new ArrayList<>();
    static List<String> des= new ArrayList<>();
    static List<String> key= new ArrayList<>();
    //static List<Integer> prices= new ArrayList<>();
    static int count;



    public static ScreenSlidePageFragment newInstance() {

        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.home_page, container, false);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference imagesRef = rootRef.child("info");

        imagesRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = (int) dataSnapshot.getChildrenCount();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Data_Images obj = ds.getValue(Data_Images.class);
                    //int price= obj.get_price();
                    String name= obj.get_name().toString();
                    String url = obj.get_path().toString();
                    String desc= obj.get_des().toString();
                    String keys = obj.get_key().toString();
                    imgurl.add(url);
                    names.add(name);
                    des.add(desc);
                    key.add(keys);
                    //prices.add(price);

                    Log.d("count",url+String.valueOf(count));
                }
                set_images(rootView);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });




        return rootView;
    }

    public void set_images(View rootView )
    {
        try {

            int counter=2;
            int c=0;


            while(c<count) {

                final String buttonID = "imageView"+counter ;

                int resID = getResources().getIdentifier(buttonID, "id","com.example.mhaidersaleem.bottom");
                img1 = rootView.findViewById(resID);
                final String spath = imgurl.get(c).toString();
                final String sname = names.get(c).toString();
                final String sdes  = des.get(c).toString();
                final String keyss = key.get(c).toString();
                Glide.with(ScreenSlidePageFragment.this)
                        .load(spath)
                        .into(img1);
                img1.setScaleType(ImageView.ScaleType.FIT_XY);


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
                        pro_des.putExtra("page",0);
                        Log.d("count1",keyss);
                        getActivity().getFragmentManager().popBackStack();
                        startActivity(pro_des);
                    }
                });


                counter++;
                c++;


            }



        }
        catch (Exception ex)
        {
            Log.d("Loop",ex.toString());
        }

    }
}
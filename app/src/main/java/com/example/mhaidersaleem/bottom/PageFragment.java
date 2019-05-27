package com.example.mhaidersaleem.bottom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by M.HAiDER Saleem on 04/08/2018.
 */
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public static PageFragment newInstance(int pageNo) {


        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);

            /*FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();*/

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.favourite, container, false);


        DatabaseHandler db = new DatabaseHandler(getContext());
        SharedPreferences prefs = getActivity().getSharedPreferences("Userdata", Context.MODE_PRIVATE);
        String email;
        if (!prefs.getString("email","").equals("")) {

            email = prefs.getString("email", "");//"No name defined" is the default value.
            //password = prefs.getString("password", "null"); //0 is the default value.
        }
         else
             email="abc@gmail.com";
        final List<Data_Images> obj = db.get_data(email);
        LinearLayout lay = view.findViewById(R.id.gparent);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int counter = 0;
        if(obj!=null) {
            for (final Data_Images obj1 : obj) {

                LinearLayout parent = new LinearLayout(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height/8);
                parent.setLayoutParams(params);
                     params=   (LinearLayout.LayoutParams)parent.getLayoutParams();
                params.topMargin= 30;

                parent.setBackgroundResource(R.color.cardview_light_background);
                parent.setOrientation(LinearLayout.HORIZONTAL);

                ImageView imgView = new ImageView(getContext());
                Button btn =  new Button(getContext());
                TextView name = new TextView(getContext());

                //layout params for child
                //image
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width/5, height/8);
                //btn
                LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(width/5, height/18);
                bp.setMargins(width/5,height/24,0,0);
                //name
                LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(width/3, height/17);
                tp.leftMargin=30;
                tp.topMargin= height/24;


                imgView.setLayoutParams(lp);
                btn.setLayoutParams(bp);
                name.setLayoutParams(tp);

                final String imageID = "image" + counter;
                final String buttonID = "button" + counter;
                final String nameID = "name" + counter;

                int imagerID = getResources().getIdentifier(imageID, "id", "com.example.mhaidersaleem.bottom");
                int namerID = getResources().getIdentifier(nameID, "id", "com.example.mhaidersaleem.bottom");
                int buttonrID = getResources().getIdentifier(buttonID, "id", "com.example.mhaidersaleem.bottom");

                imgView.setId(imagerID);
                name.setId(namerID);
                btn.setId(buttonrID);

                btn.setText("Buy Now");
                name.setTextSize(20);
                name.setTextAppearance(R.style.TextAppearance_AppCompat_Body2);
                name.setText(obj1.get_name().toString());



               // final ImageView im = view.findViewById(resID);
                final String spath = obj1.get_path().toString();
                Glide.with(PageFragment.this)
                        .load(spath)
                        .into(imgView);
                //im.setScaleType(ImageView.ScaleType.FIT_XY);
                Log.d("PATH Fav",spath);
                //Log.d("PATH Counter", String.valueOf(resID));
                parent.addView(imgView);
                parent.addView(name);
                parent.addView(btn);

                lay.addView(parent);
                counter++;


                parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pro_des= new Intent(v.getContext(),Product_desc.class);
                        pro_des.putExtra("path",spath);
                        pro_des.putExtra("name",obj1.get_name());
                        pro_des.putExtra("des",obj1.get_des());
                        pro_des.putExtra("key",obj1.get_key());
                        pro_des.putExtra("activity_name","favourite");
                        pro_des.putExtra("price",obj1.get_price());
                        pro_des.putExtra("page",1);
                        //Log.d("count1",keyss);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(PageFragment.this).attach(PageFragment.this).commit();

                        startActivity(pro_des);



                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pro_des= new Intent(v.getContext(),Product_desc.class);
                        pro_des.putExtra("path",spath);
                        pro_des.putExtra("name",obj1.get_name());
                        pro_des.putExtra("des",obj1.get_des());
                        pro_des.putExtra("key",obj1.get_key());
                        pro_des.putExtra("activity_name","favourite");
                        pro_des.putExtra("price",obj1.get_price());
                        pro_des.putExtra("page",1);
                        //Log.d("count1",keyss);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(PageFragment.this).attach(PageFragment.this).commit();

                        startActivity(pro_des);
                    }
                });
            }

        }
        else
        {
            TextView textView = view.findViewById(R.id.txt);
            textView.setVisibility(view.VISIBLE);
            textView.setText("No Favourite");
        }


        return view;
    }


}

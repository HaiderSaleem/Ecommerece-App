package com.example.mhaidersaleem.bottom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by M.HAiDER Saleem on 04/08/2018.
 */
public class Product_Cart extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    LinearLayout pro;
    Double total_p=0.0;
    int counter = 0;

    public static Product_Cart newInstance(int pageNo) {


        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        args.putInt("count", 0);
        Product_Cart fragment = new Product_Cart();
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            counter=0;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
        counter=getArguments().getInt("count");


    }
    class Task extends AsyncTask<String, Integer, Boolean> {
        @Override
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
                    Thread.sleep(1/2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_cart, container, false);
        pro = view.findViewById(R.id.progressBar);
        DatabaseHandler db = new DatabaseHandler(getContext());
        SharedPreferences prefs = getActivity().getSharedPreferences("Userdata", Context.MODE_PRIVATE);
        String email;
        if (!prefs.getString("email","").equals("")) {

            email = prefs.getString("email", "abc");//"No name defined" is the default value.
            //password = prefs.getString("password", "null"); //0 is the default value.
             }
             else
                 email="abc@gmail.com";
        final List<Data_Images> obj = db.get_cart(email);
        LinearLayout lay = view.findViewById(R.id.pro_cart);


        //Check to Proceed

        Button btn_proceed= view.findViewById(R.id.proceed);
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),Checkout.class);
                startActivity(i);
            }
        });

        //end here procees
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        Double total=0.0;
        if(obj!=null) {
            for (final Data_Images obj1 : obj) {

                LinearLayout parent = new LinearLayout(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height/5);
                parent.setLayoutParams(params);
                params=   (LinearLayout.LayoutParams)parent.getLayoutParams();
                params.topMargin= 30;

                parent.setBackgroundResource(R.color.cardview_light_background);


                parent.setLayoutParams(new LinearLayout.LayoutParams(width, height/7));
                parent.setOrientation(LinearLayout.HORIZONTAL);

                ImageView imgView = new ImageView(getContext());
                Button btn =  new Button(getContext());
                TextView name = new TextView(getContext());
                final TextView price = new TextView(getContext());
                final EditText quantity = new EditText(getContext());

                quantity.setInputType(InputType.TYPE_CLASS_NUMBER);




                //layout params for child
                //image
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width/4, height/8);
                //cross
                LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(width/11, height/18);
                bp.setMargins(width/6,height/21,0,0);
                //price
                LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(width/10, height/18);
                pp.topMargin=height/22;
                //name
                LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(width/4, height/10);
                tp.leftMargin=width/25;
                tp.topMargin=height/22;
                //quantity
                LinearLayout.LayoutParams ep = new LinearLayout.LayoutParams(width/10, height/15);
                ep.topMargin=height/22;




                imgView.setLayoutParams(lp);
                btn.setLayoutParams(bp);
                name.setLayoutParams(tp);
                quantity.setLayoutParams(ep);
                price.setLayoutParams(pp);


                //creating ids
                final String imageID = "image" + counter;
                final String buttonID = "button" + counter;
                final String nameID = "name" + counter;
                final String QID = "quantity" + counter;
                final String PID= "price"+ counter;



                int imagerID = getResources().getIdentifier(imageID, "id", "com.example.mhaidersaleem.bottom");
                int namerID = getResources().getIdentifier(nameID, "id", "com.example.mhaidersaleem.bottom");
                int buttonrID = getResources().getIdentifier(buttonID, "id", "com.example.mhaidersaleem.bottom");
                int QrID = getResources().getIdentifier(QID, "id", "com.example.mhaidersaleem.bottom");
                int PrID = getResources().getIdentifier(PID, "id", "com.example.mhaidersaleem.bottom");
                //end here

                //assigning id to dynamically created attributes
                imgView.setId(imagerID);
                name.setId(namerID);
                btn.setId(buttonrID);
                quantity.setId(QrID);
                price.setId(PrID);
                quantity.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "10")});

                btn.setBackgroundResource(R.mipmap.ic_cross);
                name.setTextSize(20);
                name.setTextAppearance(R.style.TextAppearance_AppCompat_Body2);
                name.setText(obj1.get_name().toString());

                //price
                price.setText(obj1.get_price());

                //quantity input method
                quantity.setText( Integer.toString(obj1.get_count()));
                quantity.setImeOptions(EditorInfo.IME_ACTION_DONE);

                //total Price

                TextView total_price = view.findViewById(R.id.total_amount);
                total+= Double.valueOf(obj1.get_price());
                total_p=total;

                total_price.setText("Total: "+String.valueOf(total));



                //end here
                // final ImageView im = view.findViewById(resID);
                final String spath = obj1.get_path().toString();
                Glide.with(Product_Cart.this)
                        .load(spath)
                        .into(imgView);
                //im.setScaleType(ImageView.ScaleType.FIT_XY);
                Log.d("PATH Fav",spath);
                //Log.d("PATH Counter", String.valueOf(resID));
                parent.addView(imgView);
                parent.addView(name);
                parent.addView(price);
                parent.addView(quantity);
                parent.addView(btn);

                lay.addView(parent);
                parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pro_des= new Intent(v.getContext(),Product_desc.class);
                        pro_des.putExtra("path",spath);
                        pro_des.putExtra("name",obj1.get_name());
                        pro_des.putExtra("des",obj1.get_des());
                        pro_des.putExtra("key",obj1.get_key());
                        pro_des.putExtra("activity_name","Cart");
                        pro_des.putExtra("price",obj1.get_price());
                        pro_des.putExtra("page",2);
                        //Log.d("count1",keyss);
                        getActivity().getFragmentManager().popBackStack();
                        startActivity(pro_des);


                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Value "+ quantity.getText(),Toast.LENGTH_SHORT).show();

                        DatabaseHandler db= new DatabaseHandler(getContext());
                        db.delete_cart(obj1.get_key());
                        new Task().execute();
                        Intent i= new Intent(getContext(),MainActivity.class);
                        i.putExtra("page",2);
                        startActivity(i);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(Product_Cart.this).attach(Product_Cart.this).commit();


                    }
                });




                                quantity.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                                    @Override
                                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                        //Toast.makeText(getContext(),"Counter"+ String.valueOf(counter),Toast.LENGTH_SHORT).show();
                                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                                            // Add the code for submitting here
                                            int counts=obj1.get_count();
                                            int pri=Integer.valueOf(obj1.get_price());

                                            if(counts>1)
                                            {
                                                pri =pri/counts;

                                                total_p-=pri;
                                            }

                                            String qu= quantity.getText().toString();
                                            if(!qu.equals("")) {
                                                DatabaseHandler db = new DatabaseHandler(getContext());
                                                total_p += Double.valueOf(pri * Integer.valueOf(qu));

                                                String prices = String.valueOf(pri * Integer.valueOf(qu));
                                                price.setText(prices);
                                                SharedPreferences prefs = getActivity().getSharedPreferences("Userdata", Context.MODE_PRIVATE);
                                                String email;

                                                if (!prefs.getString("email", "").equals("")) {

                                                    email = prefs.getString("email", "abc");//"No name defined" is the default value.
                                                    //password = prefs.getString("password", "null"); //0 is the default value.
                                                } else
                                                    email = "abc@gmail.com";
                                                db.update_cart(email, obj1.get_key(), Integer.valueOf(qu), prices);

                                                Intent i = new Intent(getContext(), MainActivity.class);
                                                i.putExtra("page", 2);
                                                //new Task().execute();
                                                startActivity(i);
                                            /*FragmentTransaction ft = getFragmentManager().beginTransaction();
                                            ft.detach(Product_Cart.this).attach(Product_Cart.this).commit();*/
                                                return false;
                                            }


                                        }
                                        return false;
                                    }
                                });


                counter++;




            }
            LinearLayout proceed =  view.findViewById(R.id.proceed_layout);
            proceed.setVisibility(View.VISIBLE);

        }
        else
        {
            TextView textView = view.findViewById(R.id.txt1);
            textView.setVisibility(view.VISIBLE);
            textView.setText("Empty");
            ImageView cart = view.findViewById(R.id.imageView10);
            cart.setImageResource(R.mipmap.ic_cart);
            cart.setVisibility(View.VISIBLE);
            LinearLayout proceed =  view.findViewById(R.id.proceed_layout);
            proceed.setVisibility(View.GONE);
        }


        return view;
    }



}

package com.example.mhaidersaleem.bottom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by M.HAiDER Saleem on 04/08/2018.
 */

public class FragmentWithOneImage extends Fragment {
    private String title;
    private int image;

    public static FragmentWithOneImage newInstance() {
        FragmentWithOneImage fragment = new FragmentWithOneImage();
        /*Bundle args = new Bundle();
        args.putInt("image", resImage);
        args.putString("title", title);*/
        //fragment.setArguments(args);
        return fragment;
    }


    //@Override
    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       *//* image = getArguments().getInt("image", 0);
        title = getArguments().getString("title");*//*
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_one_img, container, false);
        return inflater.inflate(R.layout.fragment_one_img, container, false);
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_img, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.txtMain);
        tvLabel.setText(title);

        ImageView imageView = (ImageView) view.findViewById(R.id.imgMain);
        imageView.setImageResource(image);
        return view;
    }*/
}
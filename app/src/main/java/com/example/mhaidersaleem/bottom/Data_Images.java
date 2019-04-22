package com.example.mhaidersaleem.bottom;

/**
 * Created by M.HAiDER Saleem on 02/08/2018.
 */

public class Data_Images {
    String price;
    String name;
    String path;
    String description;
    String key;
    int count;

    public Data_Images()
    {

    }
    public Data_Images(String prices,String nam,String pat,String descriptio,String ke)
    {
        name=nam;
        path=pat;
        description= descriptio;
        key=ke;
        price=prices;
    }
    public Data_Images(String nam,String pat,String descriptio,String ke)
    {
        name=nam;
        path=pat;
        description= descriptio;
        key=ke;
    }

    public Data_Images(String pr,String nam,String pat,String descriptio,String ke,int co)
    {
        name=nam;
        path=pat;
        description= descriptio;
        key=ke;
        count=co;
        price= pr;
    }

    public String get_name()
    {
        return name;
    }
    public String get_path()
    {
        return path;
    }
    public String get_des() { return description; }
    public String get_key()
    {
        return key;
    }
    public int get_count(){ return count; }
    public String get_price(){ return price; }
}

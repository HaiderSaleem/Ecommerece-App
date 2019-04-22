package com.example.mhaidersaleem.bottom;

/**
 * Created by M.HAiDER Saleem on 16/08/2018.
 */

public class Data_user {

    String name;
    String address;
    String email;
    String number;

    public Data_user()
    {

    }
    public Data_user(String nam,String emai,String add,String num)
    {
        name= nam;
        address=add;
        email=emai;
        number=num;
    }
    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

}

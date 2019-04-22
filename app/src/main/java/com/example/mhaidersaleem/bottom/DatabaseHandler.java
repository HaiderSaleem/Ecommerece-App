package com.example.mhaidersaleem.bottom;

/**
 * Created by M.HAiDER Saleem on 25/04/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 9;
    // Database Name
    private static final String DATABASE_NAME = "db_ecom";
    // table name
    private static final String TABLE_FAVOURITE = "My_Favourite";
    private static final String TABLE_CART = "My_Cart";
    private static final String TABLE_USER_DATA = "User_Data";
    //private static final String TABLE_CV_DATA = "CVS";

    //Favourite
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PATH = "path";
    private static final String KEY_DESCRIPTION = "DESCRIPTION";
    private static final String KEY_KEY = "KEY";

    //cart
    private static final String KEY_COUNT = "COUNT";
    private static final String KEY_PRICE = "PRICE";

    //user

    private static final String KEY_ADDRESS = "address";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_PASSWORD = "password";




    //private static final String KEY_PASS = "password";

  /*  //CV COLUMNS
    private static final String KEY_UID = "User_id";
    private static final String KEY_CNAME = "NAME";
    private static final String KEY_FNAME = "FNAME";
    private static final String KEY_AGE = "AGE";
    private static final String KEY_DOB = "DOB";
    private static final String KEY_SCHOOL = "SCHOOL";
    private static final String KEY_CLG = "COLLEGE";
    private static final String KEY_UNI = "UNI";
    private static final String KEY_EDU = "EDUCATION";
    private static final String KEY_PJOB = "PREVIOUS_JOB";
    private static final String KEY_EXPY = "YEARS_OF_EXP";
    private static final String KEY_JOB = "JOB";
    private static final String KEY_FIELD = "FIELD";
    private static final String KEY_ACHIEVMENTS = "ACHIEVMENTS";
    private static final String KEY_AWARDS = "AWARDS";
    private static final String KEY_CERTFIFICATES = "CERTIFICATES";
*/

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_FAVOURITE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  KEY_NAME + " TEXT," +KEY_EMAIL + " TEXT,"
                 + KEY_PATH + " TEXT," + KEY_DESCRIPTION + " TEXT,"+ KEY_PRICE + " TEXT," + KEY_KEY + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        String CREATE_CART = "CREATE TABLE " + TABLE_CART + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  KEY_NAME + " TEXT," +KEY_EMAIL + " TEXT,"
                + KEY_PATH + " TEXT," + KEY_DESCRIPTION + " TEXT,"+ KEY_PRICE + " TEXT," + KEY_COUNT + " INTEGER," + KEY_KEY + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CART);

        String CREATE_USER = "CREATE TABLE " + TABLE_USER_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  KEY_NAME + " TEXT," +  KEY_PASSWORD + " TEXT," +KEY_EMAIL + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_CITY + " TEXT,"+ KEY_STATE + " TEXT," + KEY_NUMBER + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_USER);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DATA);
        // Create tables again
        onCreate(sqLiteDatabase);

    }

    // Adding new contact
    Long add_favourite( String email, String name ,String path, String des,String key,String price) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email); // email
        values.put(KEY_NAME,name);
        values.put(KEY_PATH, path); // pass
        values.put(KEY_DESCRIPTION, des);
        values.put(KEY_KEY, key);
        values.put(KEY_PRICE, price);
        // Inserting Row
        long id = db.insert(TABLE_FAVOURITE, null, values);
        db.close(); // Closing database connection
        return id;
    }
    Long add_cart( String email, String name ,String path, String des,String key,int count,String price) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email); // email
        values.put(KEY_NAME,name);
        values.put(KEY_PATH, path); // pass
        values.put(KEY_DESCRIPTION, des);
        values.put(KEY_COUNT, count);
        values.put(KEY_KEY, key);
        values.put(KEY_PRICE, price);
        // Inserting Row
        long id = db.insert(TABLE_CART, null, values);
        db.close(); // Closing database connection
        return id;
    }
    Long add_user_data( String email, String name, String password ,String address, String city,String state,String number) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email); // email
        values.put(KEY_NAME,name);
        values.put(KEY_PASSWORD,password);
        values.put(KEY_CITY, city); // pass
        values.put(KEY_ADDRESS, address);
        values.put(KEY_STATE, state);
        values.put(KEY_NUMBER, number);
        // Inserting Row
        long id = db.insert(TABLE_USER_DATA, null, values);
        db.close(); // Closing database connection
        return id;
    }

    Long update_cart(String email, String key, int count,String price)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COUNT, count);
        values.put(KEY_PRICE,price);
        long id = db.update(TABLE_CART, values,"email=? and KEY=?", new String[]{email, key});
        db.close();
        return id;


    }
    Long update_email(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);

        long id = db.update(TABLE_CART, values,"email=?", new String[]{"abc@gmail.com"});
        long id1 = db.update(TABLE_FAVOURITE, values,"email=?", new String[]{"abc@gmail.com"});
        db.close();
        return id;


    }


    int check(String email, String key) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor query = db.query(TABLE_FAVOURITE, new String[]{KEY_EMAIL, KEY_KEY}, "email=? and KEY=?", new String[]{email, key}, null, null, null, null);
        int id = -1;

        if (query.getCount() > 0) {
            if (query.moveToFirst()) {
                do {
                    id = query.getInt(query.getColumnIndex(KEY_KEY));
                    db.close();

                    return id;
                } while (query.moveToNext());

            } else {

                db.close();
                id = -2;
                return id;
            }


        } else
            db.close();
            return id;


    }
    int check_cart_count(String email, String key) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor query = db.query(TABLE_CART, new String[]{KEY_EMAIL, KEY_COUNT}, "email=? and KEY=?", new String[]{email, key}, null, null, null, null);
        int id = -1;

        if (query.getCount() > 0) {
            if (query.moveToFirst()) {
                do {
                    id = query.getInt(query.getColumnIndex(KEY_COUNT));
                    db.close();

                    return id;
                } while (query.moveToNext());

            } else {

                db.close();
                id = -2;
                return id;
            }


        } else
            return id;


    }
    int check_user_data(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor query = db.query(TABLE_USER_DATA, new String[]{KEY_ID}, "email=?", new String[]{email}, null, null, null, null);
        int id = -1;

        if (query.getCount() > 0) {
            if (query.moveToFirst()) {
                do {
                    id = query.getInt(query.getColumnIndex(KEY_ID));
                    db.close();

                    return id;
                } while (query.moveToNext());

            } else {

                db.close();
                id = -2;
                return id;
            }


        } else
            return id;


    }

   /* public List<CV_attributes> get_cvdata(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  id FROM " + TABLE_CV_DATA + " WHERE "
                + KEY_UID + " = " + id;

        Cursor ex = db.rawQuery(query, null);

        if (ex.moveToFirst()) {
            List<CV_attributes> cvs = new ArrayList<CV_attributes>();
            do {
                CV_attributes cv;
                cv = new CV_attributes();
                cv.set_cv_id(ex.getString((ex.getColumnIndex(KEY_ID))).toString());
                cvs.add(cv);
            } while (ex.moveToNext());
            return cvs;
        } else
            return null;

    }
*/
    public List<Data_Images> get_data(String email) {

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_FAVOURITE + " WHERE "
                + KEY_EMAIL + " = '"+email+"'";
        Cursor ex = db.rawQuery(query, null);
        if (ex.moveToFirst()) {
            List<Data_Images> obj= new ArrayList<>();

            do {

                Data_Images obj1= new Data_Images(ex.getString((ex.getColumnIndex(KEY_PRICE))).toString(),ex.getString((ex.getColumnIndex(KEY_NAME))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_PATH))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_DESCRIPTION))).toString(), ex.getString((ex.getColumnIndex(KEY_KEY))).toString());

                obj.add(obj1);
            } while (ex.moveToNext());
            db.close();
            return obj;
        } else
            return null;


    }

    public List<Data_Images> get_cart(String email) {

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CART + " WHERE "
                + KEY_EMAIL + " = '"+email+"'";
        Cursor ex = db.rawQuery(query, null);
        if (ex.moveToFirst()) {
            List<Data_Images> obj= new ArrayList<>();

            do {

                Data_Images obj1= new Data_Images(ex.getString((ex.getColumnIndex(KEY_PRICE))).toString(),ex.getString((ex.getColumnIndex(KEY_NAME))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_PATH))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_DESCRIPTION))).toString(),ex.getString((ex.getColumnIndex(KEY_KEY))).toString(),ex.getInt((ex.getColumnIndex(KEY_COUNT))));

                obj.add(obj1);
            } while (ex.moveToNext());
            db.close();
            return obj;
        } else {
            db.close();
            return null;
        }


    }

    public Data_user get_user_data(String email) {

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_USER_DATA + " WHERE "
                + KEY_EMAIL + " = '"+email+"'";
        Cursor ex = db.rawQuery(query, null);
        if (ex.moveToFirst()) {
            Data_user obj1;

            do {

                obj1= new Data_user(ex.getString((ex.getColumnIndex(KEY_NAME))).toString(),ex.getString((ex.getColumnIndex(KEY_EMAIL))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_ADDRESS))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_NUMBER))).toString());


            } while (ex.moveToNext());
            db.close();
            return obj1;
        } else {
            db.close();
            return null;
        }


    }

    public void delete_cv(String key) {

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_FAVOURITE+" WHERE "+KEY_KEY+"='"+key+"'");
        db.close();

    }
    public void delete_cart(String key) {

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_CART+" WHERE "+KEY_KEY+"='"+key+"'");
        db.close();

    }
}

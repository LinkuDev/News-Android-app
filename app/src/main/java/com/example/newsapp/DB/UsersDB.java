package com.example.newsapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UsersDB extends SQLiteOpenHelper {
    private static int DB_VERSION = 1;
    private static final String DATABASE_NAME = "NewsDB";
    private static final String TABLE_NAME ="UsersNews";
    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String SDT = "Sdt";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("+NAME +" TEXT,"+SDT +" TEXT," +EMAIL +" TEXT)";

    public UsersDB(Context context){super(context,DATABASE_NAME, null, DB_VERSION);}

    public void onCreate(SQLiteDatabase dbb) {
        dbb.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void AddUser(String name, String sdt, String email){

        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME,name);
        cv.put(SDT,sdt);
        cv.put(EMAIL,email);
        db.insert(TABLE_NAME,null,cv);
        Log.d("Add User status",""+NAME);
    }
}

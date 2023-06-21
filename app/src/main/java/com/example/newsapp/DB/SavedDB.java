package com.example.newsapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SavedDB extends SQLiteOpenHelper {
    private static int DB_VERSION = 1;
    private static final String DATABASE_NAME = "NewsDB";
    private static final String TABLE_NAME ="SavedNews";
    private static final String TABLE_NAME_USER ="UsersNews";
    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String SDT = "Sdt";
    public static final String PASSWORD = "Password";

    public static final String KEY_ID = "Id";
    public static final String NEWS_TITTLE = "Tittle";
    public static final String NEWS_SOURCE = "Source";
    public static final String NEWS_TIME = "Time";
    public static final String NEWS_DESC = "Des";
    public static final  String NEWS_IMG_URL = "Img_Url";
    public static final  String NEWS_URL = "Url";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +" ("+ KEY_ID +" TEXT,"+
              NEWS_TITTLE +" TEXT," + NEWS_SOURCE +" TEXT," + NEWS_TIME
            +" TEXT," + NEWS_DESC +" TEXT,"+ NEWS_IMG_URL
            +" TEXT," + NEWS_URL +" TEXT)";
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER +" ("+NAME +" TEXT,"+SDT +" TEXT," +EMAIL+" TEXT,"+ PASSWORD +" TEXT)";


    public SavedDB(Context context){super(context,DATABASE_NAME, null, DB_VERSION);}


    @Override
    public void onCreate(SQLiteDatabase dbb) {
        dbb.execSQL(CREATE_TABLE);
        dbb.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_USER);

        onCreate(db);
    }

    public void InsertEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    public void insertIntoDatabase(String id, String tittle,String source, String time,String desc, String img_url, String url){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NEWS_TITTLE,tittle);
        cv.put(NEWS_SOURCE,source);
        cv.put(NEWS_TIME,time);
        cv.put(NEWS_DESC,desc);
        cv.put(NEWS_IMG_URL,img_url);
        cv.put(NEWS_URL,url);
        db.insert(TABLE_NAME,null,cv);
        Log.d("Saved News status",""+tittle+cv);
    }

    public Cursor ReadAllData(){

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from "+TABLE_NAME+"";
        return db.rawQuery(sql,null,null);

    }
    public void deleteAll()
    {
        SQLiteDatabase dele = this.getWritableDatabase();
        dele.execSQL("delete from "+ TABLE_NAME);
        dele.close();
    }
    public void AddUser(String name, String sdt, String email,String pass){

        SQLiteDatabase dbb;
        dbb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SDT,sdt);

        cv.put(EMAIL,email);
        cv.put(NAME,name);
        cv.put(PASSWORD,pass);

        dbb.insert(TABLE_NAME_USER,null,cv);
        Log.d("Add User status",""+NAME);
    }
    public Cursor ReadUser(String email){

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from "+TABLE_NAME_USER+" where Email="+"'"+email+"'";
        return db.rawQuery(sql,null,null);

    }
}

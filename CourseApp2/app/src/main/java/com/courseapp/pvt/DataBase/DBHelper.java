package com.courseapp.pvt.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "Users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Users(UserName TEXT primary key, Password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Users");

    }

    public boolean register(String username, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserName", username);
        contentValues.put("Password", password);
        long resalt = DB.insert("Users", null, contentValues);
        if (resalt== 1){
            return false;
        }else {
            return true;
        }
    }

    public boolean login(String username, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from Users where UserName = ? and Password = ?    ",new String[]{username,password});
        if (cursor.getCount()>0){
            return  true;
        }else{
            return false;
        }
    }


}

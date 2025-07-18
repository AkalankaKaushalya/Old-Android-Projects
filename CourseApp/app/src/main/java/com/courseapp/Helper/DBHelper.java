package com.courseapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.courseapp.RegisterActivity;
import com.courseapp.loginActivity;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "Users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Users(UserName TEXT primary key, Password TEXT, UserType TEXT )");
        db.execSQL("create Table Data(Subject TEXT primary key, Message TEXT , User TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Users");
        db.execSQL("drop Table if exists Data");
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

    //--------------------------------------------------------------------------------------------------------------------------------------------------------//

    public boolean send(String user, String subject, String message){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("User", user);
        contentValues.put("Subject", subject);
        contentValues.put("Message", message);
        long resalt = DB.insert("Data", null, contentValues);
        if (resalt== 1){
            return false;
        }else {
            return true;
        }
    }

    //View මෙතඩ් එක මෙය් වේ
    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Data",null);
        return cursor;
    }




    /*public boolean login(String username, String password, String utype){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from Users where UserName = ? and Password = ? and UserType = ?   ",new String[]{username,password,utype});
        if (cursor.getCount()>0){
            return  false;
        }else{
            return true;
        }

    }*/



}

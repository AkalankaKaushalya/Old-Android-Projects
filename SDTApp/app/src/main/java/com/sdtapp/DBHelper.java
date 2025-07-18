package com.sdtapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "Users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Usersdetails(UserName TEXT primary key, Password TEXT , UserType TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Usersdetails");
    }

    //Register Qur මෙය ඉන්සර්ට් කුවරි එක
    public Boolean registerdata(String username, String password, String utype){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserName", username);
        contentValues.put("Password",password);
        contentValues.put("UserType",utype);
        long result = DB.insert("Usersdetails", null, contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

}

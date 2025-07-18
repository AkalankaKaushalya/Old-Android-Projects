package com.courseapp.pvt.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SubDBHelper extends SQLiteOpenHelper {
    public SubDBHelper(@Nullable Context context) {
        super(context, "Subjet.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Messages(User TEXT,  Subject TEXT primary key, Message TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Messages");

    }

    public boolean addMessage(String user, String subject, String message){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("User", user);
        contentValues.put("Subject", subject);
        contentValues.put("Message", message);
        long resalt = DB.insert("Messages", null, contentValues);
        if (resalt== 1){
            return false;
        }else {
            return true;
        }
    }

    //View මෙතඩ් එක මෙය් වේ
    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Messages",null);
        return cursor;
    }


}

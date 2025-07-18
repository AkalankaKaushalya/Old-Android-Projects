package com.studentregistationapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "Note.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table nots(Title TEXT, Subje TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists nots");

    }

    public boolean addnote(String title, String subjet) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", title);
        contentValues.put("Subje", subjet);
        //contentValues.put("Password", password);
        long resalt = DB.insert("nots", null, contentValues);
        if (resalt == 1) {
            return false;
        } else {
            return true;
        }
    }



    //View මෙතඩ් එක මෙය් වේ
    public Cursor getNote() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from nots", null);
        return cursor;
    }

}

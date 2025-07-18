package com.studentworknotes.vta.DataBase;

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
        DB.execSQL("create Table Users(UserName TEXT, Email TEXT primary key, Password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Users");

    }

    public boolean register(String username, String email, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserName", username);
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        long resalt = DB.insert("Users", null, contentValues);
        if (resalt == 1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean login(String email, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from Users where Email = ? and Password = ?    ", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //View මෙතඩ් එක මෙය් වේ
    public Cursor getUsers() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Users", null);
        return cursor;
    }

}

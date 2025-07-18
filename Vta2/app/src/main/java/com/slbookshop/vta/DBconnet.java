package com.slbookshop.vta;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBconnet extends SQLiteOpenHelper {
    public DBconnet(@Nullable Context context) {
        super(context, "vta.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table vta(Name TEXT, Age TEXT, NIC TEXT primary key)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists vta");
    }

    public boolean add(String name, String age, String nic){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Age", age);
        contentValues.put("NIC",nic);
        long rs = database.insert("vta", null, contentValues);
        if (rs==1){
            return false;
        }else {
            return true;
        }
    }
}

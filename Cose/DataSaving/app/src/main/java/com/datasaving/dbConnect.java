package com.datasaving;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbConnect extends SQLiteOpenHelper {

    //db connection code
    public dbConnect(@Nullable Context context) {
        super(context, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table datatb(Bid TEXT primary key, nc TEXT,fn TEXT,ln TEXT,ma TEXT,cn TEXT,ad TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists datatb");
    }

    //insert code

    //method
    public boolean addData(String lm, String tm, String lmu, String tmu, String oup, String uu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nc", lm);
        cv.put("fn", tm);
        cv.put("ln", lmu);
        cv.put("ma", tmu);
        cv.put("cn", oup);
        cv.put("ad", uu);

        long result = db.insert("datatb", null, cv);
        if (result == 1) {
            return false;
        } else {
            return true;
        }


    }
    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from datatb",null);
        return cursor;
    }

}



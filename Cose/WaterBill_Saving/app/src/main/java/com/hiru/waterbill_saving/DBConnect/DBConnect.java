package com.hiru.waterbill_saving.DBConnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConnect extends SQLiteOpenHelper {
    public DBConnect(@Nullable Context context) {
        super(context, "water.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table watertb(Bid TEXT PRIMARY KEY, TMoun TEXT, TMun TEXT, Useunit TEXT, Totle TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists watertb");
    }

    public boolean addData(String id, String this_month, String tm_unit, String use_unit, String total_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Bid", id);
        cv.put("TMoun", this_month);
        cv.put("TMun", tm_unit);
        cv.put("Useunit", use_unit);
        cv.put("Totle", total_price);
        long resalt = db.insert("watertb", null, cv);
        if ((resalt == 1)) {
            return false;
        } else {
            return true;
        }
    }


    public boolean delete(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from datatb where Bid = ?", new String[]{id});//
        if (cursor.getCount() > 0) {
            long resalt = DB.delete("watertb", "Bid = ?", new String[]{id});//
            if (resalt == -1) {
                return false;

            } else {
                return true;
            }
        }else{
            return false;
        }

    }

    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from watertb", null);
        return cursor;
    }





}

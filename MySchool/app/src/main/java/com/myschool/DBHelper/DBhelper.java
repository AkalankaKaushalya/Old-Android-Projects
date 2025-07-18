package com.myschool.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    public DBhelper(@Nullable Context context) {
        super(context, "Mynotes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Notestb(ID INTEGER PRIMARY KEY AUTOINCREMENT,  Title TEXT, Masseage TEXT, NDate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Notestb");
    }

    public boolean addNote(int id, String title, String masseage, String date) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put("ID", id);
        contentValues.put("Title", title);
        contentValues.put("Masseage", masseage);
        contentValues.put("NDate", date);
        long resalt = DB.insert("Notestb", null, contentValues);
        if (resalt == 1) {
            return false;
        } else {
            return true;
        }
    }

    //Delete මෙතඩ් එක මෙය් වේ
    public boolean notesdelete(int id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Notestb where id = ?", new String[]{String.valueOf(id)});// ඩිලිට් මෙතඩ් එකදී කසර් ලයිබ්‍රි එක ඉන්පොර්ට් කරගන්නවා
        if (cursor.getCount() > 0) {
            long resalt = DB.delete("Notestb", "ID=?", new String[]{String.valueOf(id)});// අපි මෙකෙදී ඩිලිට් කරන්නෙ අපි යොදගත් ප්‍රිමරි කී එකයි
            if (resalt == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    //Update මෙතඩ් එක මෙය් වේ
    public boolean noteupdate(int id, String title, String masseage) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("Title", title);
        contentValues.put("Masseage", masseage);
        Cursor cursor = DB.rawQuery("Select * from Notestb where id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long resalt = DB.update("Notestb", contentValues, "ID=?", new String[]{String.valueOf(id)});
            if (resalt == -1) {
                return false;  // මෙම රිසල්ට් එක තිබෙනම් මෙය ටෘ වෙනවා එසෙ නොමැතිනම් මෙය ෆොල්ස් වේ
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public int getCount() {
        SQLiteDatabase DB = getReadableDatabase();
        String query = "SELECT * FROM Notestb";
        Cursor cursor = DB.rawQuery(query, null); // get Count methad in SQLite
        return cursor.getCount();
    }

    //View මෙතඩ් එක මෙය් වේ
    public Cursor getNotes() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Notestb", null);
        return cursor;
    }
}

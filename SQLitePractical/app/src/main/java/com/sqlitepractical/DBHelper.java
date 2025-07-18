package com.sqlitepractical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "Users.db", null, 1);// අපට අදාල ඩෙටා බෙස් එක නිරමාණය කරගනිම සිදු කරන්නෙ මෙතනින්
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userinfo(UserName TEXT, Age TEXT, NIC TEXT primary key, Email TEXT)");// අපට අදාල ටෙබල් එක වැලියුස් යොදා ටෙබල් එක නිර්මානය කර ගැනිම සිදු කරගනී
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int il) {
            DB.execSQL("drop Table if exists Userinfo");// ඩෙටා බෙස් එකට එම් ටෙබල් එක සෙට් කිරිම ඒ මගින් සිදූ කරයි..
    }

    //Insert මෙතඩ් එක මෙය් වේ
    public boolean insert(String username, String age, String nic, String Email){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserName", username);
        contentValues.put("Age", age);
        contentValues.put("NIC", nic);
        contentValues.put("Email", Email);
        long resalt = DB.insert("Userinfo", null, contentValues);
        if (resalt==1){
            return false ;
        }else {
            return true;
        }
    }


    //Update මෙතඩ් එක මෙය් වේ
    public boolean update(String username, String age, String nic, String Email){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserName", username);
        contentValues.put("Age", age);
        contentValues.put("Email", Email);
        Cursor cursor = DB.rawQuery("Select * from Userinfo where NIC = ?", new String[]{nic});
        if (cursor.getCount()>0){
         long resalt = DB.update("Userinfo", contentValues, "NIC=?", new String[]{nic});
            if (resalt== -1){
                return false;  // මෙම රිසල්ට් එක තිබෙනම් මෙය ටෘ වෙනවා එසෙ නොමැතිනම් මෙය ෆොල්ස් වේ
             }else {
                return true;
             }
        }else {
            return false;
        }
    }

    //Delete මෙතඩ් එක මෙය් වේ
    public boolean delete(String nic){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userinfo where nic = ?", new String[]{nic});// ඩිලිට් මෙතඩ් එකදී කසර් ලයිබ්‍රි එක ඉන්පොර්ට් කරගන්නවා
        if (cursor.getCount()>0){
            long resalt = DB.delete("Userinfo","NIC=?", new String[]{nic});// අපි මෙකෙදී ඩිලිට් කරන්නෙ අපි යොදගත් ප්‍රිමරි කී එකයි
            if (resalt== -1){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }

    //View මෙතඩ් එක මෙය් වේ
    public  Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userinfo",null);
        return cursor;
    }
}

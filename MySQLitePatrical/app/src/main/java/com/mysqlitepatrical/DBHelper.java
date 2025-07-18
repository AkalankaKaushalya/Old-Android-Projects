package com.mysqlitepatrical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1); //මෙය ඩෙටබෙස් කනෙක්ට් ෆයිල් එක වේ මුලින් ඇත්තෙ ඩෙටබෙස් නම පසුව ෆක්ට්‍රි එක සහා එහි වර්ශන් එක
    }

    @Override
    public void onCreate(SQLiteDatabase db) {// මෙම කොටස තුල මුලින් අපි අපේ SQlite Tabale එක නිර්මානය කරගත යුතූයී ඒ තුල ප්‍රයිමරි කී එකක් සහා ඩෙට ටයිප්ස් එක පෙන්විම සිදුකලයුතුය
        db.execSQL("create Table Userdetails(name TEXT, nic NUMBER primary key, phone NUMBER, email TEXT)"); // SQLite ටෙබල් එක නිර්මානය කිරිම

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL("drop Table if exists Userdetails");
    }
    //Insert Qur මෙය ඉන්සර්ට් කුවරි එක
    public Boolean insertdata(String name, String nic, String phone, String email){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("nic",nic);
        contentValues.put("phone",phone);
        contentValues.put("email",email);
        long result = DB.insert("Userdetails", null, contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }


    //Update Qur මෙය ඉන්සර්ට් කුවරි එක
    public Boolean updatedata(String name, String nic, String phone, String email){
        SQLiteDatabase DB = this.getWritableDatabase(); // මෙය අප්ඩෙට් කුවරියයි එම නිසා අපි ප්ර්‍යිමරි කී එක ඩිලිට් කරගන්නවා මොකද අපී අප්ඩෙට් කරන්නෙ ප්‍රයිමරි කී එක මගින් නිසා
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone",phone);
        contentValues.put("email",email);
        Cursor cursor = DB.rawQuery("Select * from Userdetails where nic = ?", new String[]{nic});
        if (cursor.getCount()>0) {
            long result = DB.update("Userdetails", contentValues, "nic=?", new String[]{nic}); //මෙය අප්ඩෙට් කුවරි එක වේ මෙහිදී අපි ලබාගත්තේ ප්‍රයිමරි කී එකයි එම නිස අපී සොයාගැනිමට නැත්ශොත් වෙයා කොල්ස් එක ලෙස nic එක භාවිතා කරනවා
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }


    //Delet Qur මෙය ඉන්සර්ට් කුවරි එක
    public Boolean deletdata( String nic){
        SQLiteDatabase DB = this.getWritableDatabase(); // මෙය අප්ඩෙට් කුවරියයි එම නිසා අපි ප්ර්‍යිමරි කී එක ඩිලිට් කරගන්නවා මොකද අපී අප්ඩෙට් කරන්නෙ ප්‍රයිමරි කී එක මගින් නිසා
        Cursor cursor = DB.rawQuery("Select * from Userdetails where nic = ?", new String[]{nic});
        if (cursor.getCount()>0) {
            long result = DB.delete("Userdetails", "nic=?", new String[]{nic}); //මෙය අප්ඩෙට් කුවරි එක වේ මෙහිදී අපි ලබාගත්තේ ප්‍රයිමරි කී එකයි එම නිස අපී සොයාගැනිමට නැත්ශොත් වෙයා කොල්ස් එක ලෙස nic එක භාවිතා කරනවා
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }


    //View Qur මෙය ඉන්සර්ට් කුවරි එක
    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase(); // මෙය අප්ඩෙට් කුවරියයි එම නිසා අපි ප්ර්‍යිමරි කී එක ඩිලිට් කරගන්නවා මොකද අපී අප්ඩෙට් කරන්නෙ ප්‍රයිමරි කී එක මගින් නිසා
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
        return cursor;  // මෙතනින් පසූ නැවත මෙයින් ඇක්ටිවිටි එකෙ මෙම මෙතර්ඩ් ක්‍රියා කිරිමට සලස්වන්න
    }

}

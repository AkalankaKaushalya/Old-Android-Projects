package com.electricalmeterbile.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "Electic.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Eltb(BilNo TEXT PRIMARY KEY,  lbdate TEXT, nbdate TEXT, lunit TEXT, nunit TEXT, Nuseunit TEXT, oneUnitP TEXT, ThisMonPris TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Eltb");

    }

    public boolean saveData(String blno, String Lbdate, String Nbdate, String Lunit, String Nunit, String nowUunit, String Oneupris, String tiseMpris) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put("ID", id);
        contentValues.put("BilNo", blno);
        contentValues.put("lbdate", Lbdate);
        contentValues.put("nbdate", Nbdate);
        contentValues.put("lunit", Lunit);
        contentValues.put("nunit", Nunit);
        contentValues.put("Nuseunit", nowUunit);
        contentValues.put("oneUnitP", Oneupris);
        contentValues.put("ThisMonPris", tiseMpris);
        long resalt = DB.insert("Eltb", null, contentValues);
        if (resalt == 1) {
            return false;
        } else {
            return true;
        }
    }

    //Delete මෙතඩ් එක මෙය් වේ
    public boolean datadelete(String bilno) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Eltb where bilno = ?", new String[]{bilno});// ඩිලිට් මෙතඩ් එකදී කසර් ලයිබ්‍රි එක ඉන්පොර්ට් කරගන්නවා
        if (cursor.getCount() > 0) {
            long resalt = DB.delete("Eltb", "BilNo=?", new String[]{bilno});// අපි මෙකෙදී ඩිලිට් කරන්නෙ අපි යොදගත් ප්‍රිමරි කී එකයි
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
    public boolean dataupdate(String blno, String Lbdate, String Nbdate, String Lunit, String Nunit, String nowUunit, String Oneupris, String tiseMpris) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BilNo", blno);
        contentValues.put("lbdate", Lbdate);
        contentValues.put("nbdate", Nbdate);
        contentValues.put("lunit", Lunit);
        contentValues.put("nunit", Nunit);
        contentValues.put("Nuseunit", nowUunit);
        contentValues.put("oneUnitP", Oneupris);
        contentValues.put("ThisMonPris", tiseMpris);
        Cursor cursor = DB.rawQuery("Select * from Eltb where BilNo = ?", new String[]{blno});
        if (cursor.getCount() > 0) {
            long resalt = DB.update("Eltb", contentValues, "BilNo=?", new String[]{blno});
            if (resalt == -1) {
                return false;  // මෙම රිසල්ට් එක තිබෙනම් මෙය ටෘ වෙනවා එසෙ නොමැතිනම් මෙය ෆොල්ස් වේ
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /*public boolean login(String email, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from Users where Email = ? and Password = ?    ", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }*/

    //View මෙතඩ් එක මෙය් වේ
    public Cursor getNotes() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Eltb", null);
        return cursor;
    }


}

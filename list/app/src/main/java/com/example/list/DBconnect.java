package com.example.list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBconnect extends SQLiteOpenHelper {
    private static final int VERSION =3;
    private static final String DBname = "Todo";
    private static final String Tname = "task";

    //Colums Name
    private static final String ID = "id";
    private static final String Title = "title";
    private static final String Description = "description";
    private static final String Started = "started";
    private static final String Finished = "finished";


    public DBconnect(@Nullable Context context) {
        super(context,DBname,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE_QUERY = " CREATE TABLE "+Tname+" " +
                "("
                +ID+" INTEGER PRIMARY KEY "+"AUTOINCREMENT,"
                +Title+ " TEXT,"
                +Description+ " TEXT, "
                +Started+ " TEXT,"
                +Finished+ " TEXT "+");";

        db.execSQL(TABLE_CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+Tname;
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);

    }

    public void addToDo(ToDoModelclz toDoModelclz){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Title,toDoModelclz.getTitle());
        contentValues.put(Description,toDoModelclz.getDescription());
        contentValues.put(Started,toDoModelclz.getStarted());
        contentValues.put(Finished,toDoModelclz.getFinished());

        sqLiteDatabase.insert(Tname,null,contentValues);
        sqLiteDatabase.close();
    }

    public int countToDo(){
        SQLiteDatabase db =getReadableDatabase();
        String query = "SELECT * FROM "+ Tname;
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }

    public List<ToDoModelclz> getAllToDos(){
        List<ToDoModelclz> toDoModelclzs = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query ="SELECT * FROM "+Tname;

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                ToDoModelclz toDoModelclz = new ToDoModelclz();

                toDoModelclz.setId(cursor.getInt(0));
                toDoModelclz.setTitle(cursor.getString(1));
                toDoModelclz.setDescription(cursor.getString(2));
                toDoModelclz.setStarted(cursor.getLong(3));
                toDoModelclz.setFinished(cursor.getLong(4));

                toDoModelclzs.add(toDoModelclz);
            }while (cursor.moveToNext());
        }
        return toDoModelclzs;
    }

    public void deletetodo(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Tname,ID +" =?",new String[]{String.valueOf(id)});
        db.close();
    }
    public ToDoModelclz getSingleToDo(int id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(Tname,new String[]{ID,Title,Description,Started,Finished},ID + " = ?",new String[]{String.valueOf(id)},null,null,null,null);

        ToDoModelclz toDoModelclz;
        if(cursor != null){
            cursor.moveToFirst();
            int taskid = cursor.getInt(0);
            String title = cursor.getString(1);
            String desc = cursor.getString(2);
            long Start = cursor.getLong(3);
            long finish = cursor.getLong(4);

            toDoModelclz = new ToDoModelclz(taskid,title,desc,Start,finish);

            return toDoModelclz;
        }
        return null;
    }

    public int updateSingleToDo(ToDoModelclz toDoModelclz){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Title,toDoModelclz.getTitle());
        contentValues.put(Description,toDoModelclz.getDescription());
        contentValues.put(Started,toDoModelclz.getStarted());
        contentValues.put(Finished,toDoModelclz.getFinished());

        int status = db.update(Tname,contentValues,ID +" =?",new String[]{String.valueOf(toDoModelclz.getId())});

        db.close();
        return status;
    }
}

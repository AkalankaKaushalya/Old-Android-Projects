package com.sliit.madlab11.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Database.db";
    private static final String TAG = "DBHandler";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_USER =
                "CREATE TABLE " + User.Users.TABLE_NAME + " (" +
                        User.Users.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        User.Users.COLUMN_NAME + " TEXT," +
                        User.Users.COLUMN_PASSWORD + " TEXT," +
                        User.Users.COLUMN_TYPE + " TEXT)";
        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_MESSAGE =
                "CREATE TABLE " + Message.Messages.TABLE_NAME + " (" +
                        Message.Messages.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Message.Messages.COLUMN_USER + " TEXT," +
                        Message.Messages.COLUMN_SUBJECT + " TEXT," +
                        Message.Messages.COLUMN_MESSAGE + " TEXT)";
        db.execSQL(CREATE_TABLE_MESSAGE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String SQL_DELETE_ENTRIES_USER = "DROP TABLE IF EXISTS " + User.Users.TABLE_NAME;
        String SQL_DELETE_ENTRIES_MESSAGE = "DROP TABLE IF EXISTS " + Message.Messages.TABLE_NAME;

        db.execSQL(SQL_DELETE_ENTRIES_USER);
        db.execSQL(SQL_DELETE_ENTRIES_MESSAGE);
        onCreate(db);
    }

    public long addUser (String username, String password, String type) {

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        // Put parameter values to baseColumns
        values.put(User.Users.COLUMN_NAME, username);
        values.put(User.Users.COLUMN_PASSWORD, password);
        values.put(User.Users.COLUMN_TYPE, type);

        // Insert the new row, returning the primary key value of the new row
        long result = db.insert(User.Users.TABLE_NAME, null, values);

        // Return result of inserting data to database
        return result;

        // End of addUser function
    }

    public long addMessage (String user, String subject, String message) {

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        // Put parameter values to baseColumns
        values.put(Message.Messages.COLUMN_USER, user);
        values.put(Message.Messages.COLUMN_SUBJECT, subject);
        values.put(Message.Messages.COLUMN_MESSAGE, message);

        // Insert the new row, returning the primary key value of the new row
        long result = db.insert(Message.Messages.TABLE_NAME, null, values);

        // Return result of inserting data to database
        return result;

        // End of addMessage function
    }

    public List readUserInfo(String request) {

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                User.Users.COLUMN_ID,
                User.Users.COLUMN_NAME,
                User.Users.COLUMN_PASSWORD,
                User.Users.COLUMN_TYPE
        };

        String sortOrder = User.Users.COLUMN_ID + " DESC";

        Cursor cursor = db.query(
                User.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List USERNAME = new ArrayList<>();
        List PASSWORD = new ArrayList<>();
        List TYPE = new ArrayList<>();

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(User.Users.COLUMN_NAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(User.Users.COLUMN_PASSWORD));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(User.Users.COLUMN_TYPE));
            USERNAME.add(username);
            PASSWORD.add(password);
            TYPE.add(type);
        }

        cursor.close();

        if (request == "User" || request == "user") {
            return USERNAME;
        }
        else if (request == "Password" || request == "password") {
            return PASSWORD;
        }
        else if (request == "Type" || request == "type") {
            return TYPE;
        }
        else {
            return null;
        }
        // End of readUserInfo function
    }

    public List readAllMessage(String request) {

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                Message.Messages.COLUMN_ID,
                Message.Messages.COLUMN_USER,
                Message.Messages.COLUMN_SUBJECT,
                Message.Messages.COLUMN_MESSAGE
        };

        String sortOrder = Message.Messages.COLUMN_ID + " DESC";

        Cursor cursor = db.query(
                Message.Messages.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List USER = new ArrayList<>();
        List SUBJECT = new ArrayList<>();
        List MESSAGE = new ArrayList<>();

        while (cursor.moveToNext()) {
            String users = cursor.getString(cursor.getColumnIndexOrThrow(Message.Messages.COLUMN_USER));
            String subjects = cursor.getString(cursor.getColumnIndexOrThrow(Message.Messages.COLUMN_SUBJECT));
            String messages = cursor.getString(cursor.getColumnIndexOrThrow(Message.Messages.COLUMN_MESSAGE));
            USER.add(users);
            SUBJECT.add(subjects);
            MESSAGE.add(messages);
        }

        cursor.close();

        if (request == "User" || request == "user") {
            return USER;
        }
        else if (request == "Subject" || request == "subject") {
            return SUBJECT;
        }
        else if (request == "Message" || request == "message") {
            return MESSAGE;
        }
        else {
            return null;
        }
        // End of readAllMessage function
    }

    public Cursor readAllMessages(){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                Message.Messages.COLUMN_ID,
                Message.Messages.COLUMN_USER,
                Message.Messages.COLUMN_SUBJECT,
                Message.Messages.COLUMN_MESSAGE
        };

        String sortOrder = Message.Messages.COLUMN_ID + " DESC";

        Cursor cursor = db.query(
                Message.Messages.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return cursor;
    }



}

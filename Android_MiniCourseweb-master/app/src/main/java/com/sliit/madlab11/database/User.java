package com.sliit.madlab11.database;

import android.provider.BaseColumns;

public final class User {

    private User() {}

    public static final class Users implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_TYPE = "type";
    }
}

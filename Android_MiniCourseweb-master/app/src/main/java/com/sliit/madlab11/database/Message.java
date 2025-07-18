package com.sliit.madlab11.database;

import android.provider.BaseColumns;

public final class Message {

    private Message() {}

    public static final class Messages implements BaseColumns {
        public static final String TABLE_NAME = "message";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_MESSAGE = "msg";
    }
}

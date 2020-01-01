package com.xxm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseHelper extends SQLiteOpenHelper {

        static final String TABLE_NAME = "USERS";

        public static final String COL_NAME_USERNAME = "username";
        public static final String COL_NAME_PASSWORD = "password";
        public static final String COL_NAME_LAST_FAILED = "lastFailed";
        public static final String COL_NAME_FAILED_ATTEMPTS = "failedAttempts";
        public static final String COL_NAME_LAST_LOGIN = "lastLogin";
        public static final String _ID = "_id";

        static final String DB_NAME = "NETEASE_DB";
        static final int DB_VERSION = 1;

        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME_USERNAME + " TEXT NOT NULL, " + COL_NAME_PASSWORD + " TEXT, "+
                COL_NAME_LAST_LOGIN + " TEXT, " + COL_NAME_FAILED_ATTEMPTS + " INTEGER, " + COL_NAME_LAST_FAILED+" TEXT);";

        public LocalDatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
}

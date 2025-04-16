package com.zybooks.cs360_project2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "calendarApp.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_EVENT_ID = "event_id";
    public static final String COLUMN_EVENT_NAME = "event_name";
    public static final String COLUMN_EVENT_DATE = "event_date";
    public static final String COLUMN_EVENT_TIME = "event_time";
    public static final String COLUMN_EVENT_DESCRIPTION = "event_description";

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";

    private static final String TABLE_CREATE_EVENTS =
            "CREATE TABLE " + TABLE_EVENTS + " (" +
                    COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EVENT_NAME + " TEXT, " +
                    COLUMN_EVENT_DATE + " TEXT, " +
                    COLUMN_EVENT_TIME + " TEXT, " +
                    COLUMN_EVENT_DESCRIPTION + " TEXT, " +
                    COLUMN_USERNAME + " TEXT" + ");";

    private static final String TABLE_CREATE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_PHONE_NUMBER + " TEXT" + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_EVENTS);
        db.execSQL(TABLE_CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    //Method to get events with specific date
    public Cursor getEventsByDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_EVENT_ID, COLUMN_EVENT_NAME, COLUMN_EVENT_DATE, COLUMN_EVENT_TIME, COLUMN_EVENT_DESCRIPTION};
        String selection = COLUMN_EVENT_DATE + " = ?";
        String[] selectionArgs = {date};

        return db.query(TABLE_EVENTS, columns, selection, selectionArgs, null, null, null);
    }
}
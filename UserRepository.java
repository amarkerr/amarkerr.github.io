package com.zybooks.cs360_project2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserRepository {
    private final DatabaseHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS,
                new String[]{DatabaseHelper.COLUMN_USER_ID},
                DatabaseHelper.COLUMN_USERNAME + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        boolean isValid = cursor != null && cursor.moveToFirst();
        if (cursor != null) cursor.close();
        return isValid;
    }

    public boolean registerUser(String username, String password, String phone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS,
                new String[]{DatabaseHelper.COLUMN_USER_ID},
                DatabaseHelper.COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        boolean exists = cursor != null && cursor.moveToFirst();
        if (exists) {
            cursor.close();
            return false;
        }

        db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_USERS + " (" +
                        DatabaseHelper.COLUMN_USERNAME + ", " +
                        DatabaseHelper.COLUMN_PASSWORD + ", " +
                        DatabaseHelper.COLUMN_PHONE_NUMBER + ") VALUES (?, ?, ?)",
                new Object[]{username, password, phone});
        if (cursor != null) cursor.close();
        return true;
    }
}


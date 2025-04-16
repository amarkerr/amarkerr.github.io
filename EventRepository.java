package com.zybooks.cs360_project2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    private final DatabaseHelper dbHelper;

    public EventRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Event> getEventsByDate(String date) {
        List<Event> events = new ArrayList<>();
        Cursor cursor = dbHelper.getEventsByDate(date);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_NAME));
                String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_TIME));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_DESCRIPTION));
                events.add(new Event(id, name, date, time, description));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return events;
    }

    public void deleteEventById(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_EVENTS, DatabaseHelper.COLUMN_EVENT_ID + "=?", new String[]{String.valueOf(id)});
    }

    public Event getEventById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS, null,
                DatabaseHelper.COLUMN_EVENT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_NAME));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_DATE));
            String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_TIME));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_DESCRIPTION));
            cursor.close();
            return new Event(id, name, date, time, description);
        }
        return null;
    }

    public void addEvent(Event event, String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_EVENTS + " (" +
                        DatabaseHelper.COLUMN_EVENT_NAME + ", " +
                        DatabaseHelper.COLUMN_EVENT_DATE + ", " +
                        DatabaseHelper.COLUMN_EVENT_TIME + ", " +
                        DatabaseHelper.COLUMN_EVENT_DESCRIPTION + ", " +
                        DatabaseHelper.COLUMN_USERNAME + ") VALUES (?, ?, ?, ?, ?)",
                new Object[]{event.getName(), event.getDate(), event.getTime(), event.getDescription(), username});
    }

    public void updateEvent(Event event) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE " + DatabaseHelper.TABLE_EVENTS + " SET " +
                        DatabaseHelper.COLUMN_EVENT_NAME + " = ?, " +
                        DatabaseHelper.COLUMN_EVENT_DATE + " = ?, " +
                        DatabaseHelper.COLUMN_EVENT_TIME + " = ?, " +
                        DatabaseHelper.COLUMN_EVENT_DESCRIPTION + " = ? WHERE " +
                        DatabaseHelper.COLUMN_EVENT_ID + " = ?",
                new Object[]{event.getName(), event.getDate(), event.getTime(), event.getDescription(), event.getId()});
    }
}

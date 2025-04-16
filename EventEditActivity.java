package com.zybooks.cs360_project2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameEditText;
    private EditText eventDateEditText;
    private EditText eventTimeEditText;
    private EditText eventDescriptionEditText;
    private DatabaseHelper dbHelper;
    private int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_edit);

        dbHelper = new DatabaseHelper(this);

        eventNameEditText = findViewById(R.id.event_name);
        eventDateEditText = findViewById(R.id.event_date);
        eventTimeEditText = findViewById(R.id.event_time);
        eventDescriptionEditText = findViewById(R.id.event_description);
        Button saveEventButton = findViewById(R.id.save_event_button);

        eventId = getIntent().getIntExtra("event_id", -1);

        if (eventId != -1) {
            loadEventDetails();
        }

        saveEventButton.setOnClickListener(v -> saveEvent());
    }

    // Method to load information
    private void loadEventDetails() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS,
                null,
                DatabaseHelper.COLUMN_EVENT_ID + "=?",
                new String[]{String.valueOf(eventId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_NAME));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_DATE));
            String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_TIME));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_DESCRIPTION));
            eventNameEditText.setText(name);
            eventDateEditText.setText(date);
            eventTimeEditText.setText(time);
            eventDescriptionEditText.setText(description);
            cursor.close();
        }
    }

    //Method to save information
    private void saveEvent() {
        String name = eventNameEditText.getText().toString();
        String date = eventDateEditText.getText().toString();
        String time = eventTimeEditText.getText().toString();
        String description = eventDescriptionEditText.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE " + DatabaseHelper.TABLE_EVENTS + " SET " +
                        DatabaseHelper.COLUMN_EVENT_NAME + " = ?, " +
                        DatabaseHelper.COLUMN_EVENT_DATE + " = ?, " +
                        DatabaseHelper.COLUMN_EVENT_TIME + " = ?, " +
                        DatabaseHelper.COLUMN_EVENT_DESCRIPTION + " = ? WHERE " +
                        DatabaseHelper.COLUMN_EVENT_ID + " = ?",
                new Object[]{name, date, time, description, eventId});
        Toast.makeText(this, "Event updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}

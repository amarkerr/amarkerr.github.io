package com.zybooks.cs360_project2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EventViewActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);

        dbHelper = new DatabaseHelper(this);

        fillEventDetails();

        Button editEventButton = findViewById(R.id.edit_event_button);
        Button deleteEventButton = findViewById(R.id.delete_event_button);

        eventId = getIntent().getIntExtra("event_id", -1);


        editEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventViewActivity.this, EventEditActivity.class);
            intent.putExtra("event_id", eventId);
            startActivity(intent);
        });

        deleteEventButton.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DatabaseHelper.TABLE_EVENTS,
                    DatabaseHelper.COLUMN_EVENT_ID + "=?",
                    new String[]{String.valueOf(eventId)});
            Toast.makeText(this, "Event deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    // Reload events when the activity resumes
    @Override
    protected void onResume() {
        super.onResume();
        fillEventDetails();
    }

    // Method to provide view with info
    private void fillEventDetails() {
        TextView eventNameTextView = findViewById(R.id.view_event_name);
        TextView eventDateTextView = findViewById(R.id.view_event_date);
        TextView eventTimeTextView = findViewById(R.id.view_event_time);
        TextView eventDescriptionTextView = findViewById(R.id.view_event_description);
        if (eventId != -1) {
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
                eventNameTextView.setText(name);
                eventDateTextView.setText(date);
                eventTimeTextView.setText(time);
                eventDescriptionTextView.setText(description);
                cursor.close();
            }
        }
    }
}
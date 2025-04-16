package com.zybooks.cs360_project2;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EventAddActivity extends AppCompatActivity {
    private EditText eventNameEditText;
    private EditText eventDateEditText;
    private EditText eventTimeEditText;
    private EditText eventDescriptionEditText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_add);

        dbHelper = new DatabaseHelper(this);

        eventNameEditText = findViewById(R.id.event_name);
        eventDateEditText = findViewById(R.id.event_date);
        eventTimeEditText = findViewById(R.id.event_time);
        eventDescriptionEditText = findViewById(R.id.event_description);
        Button saveEventButton = findViewById(R.id.save_event_button);

        saveEventButton.setOnClickListener(v -> saveEvent());
    }

    // Method to save information
    private void saveEvent() {
        String name = eventNameEditText.getText().toString();
        String date = eventDateEditText.getText().toString();
        String time = eventTimeEditText.getText().toString();
        String description = eventDescriptionEditText.getText().toString();

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = prefs.getString("username", "");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_EVENTS + " (" +
                        DatabaseHelper.COLUMN_EVENT_NAME + ", " +
                        DatabaseHelper.COLUMN_EVENT_DATE + ", " +
                        DatabaseHelper.COLUMN_EVENT_TIME + ", " +
                        DatabaseHelper.COLUMN_EVENT_DESCRIPTION + ", " +
                        DatabaseHelper.COLUMN_USERNAME + ") VALUES (?, ?, ?, ?, ?)",
                new Object[]{name, date, time, description, username});

        Toast.makeText(this, "Event added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

}
package com.zybooks.cs360_project2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Locale;

public class EventsListActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private EventsAdapter eventsAdapter;
    private final List<Event> eventList = new ArrayList<>();
    private static final String PREF_SMS_APPROVAL = "sms_approval";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_list);

        dbHelper = new DatabaseHelper(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsAdapter = new EventsAdapter(eventList);
        recyclerView.setAdapter(eventsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(EventsListActivity.this, EventAddActivity.class);
            startActivity(intent);
        });

        loadEvents();
        sendSMSAlert();

        ImageButton settingsButton = findViewById(R.id.SettingsButton);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventsListActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    // Reload events when the activity resumes
    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }

    //Method to load events from the database
    private void loadEvents() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS,
                null, null, null, null, null, DatabaseHelper.COLUMN_EVENT_DATE);

        eventList.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_NAME));
                String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_DATE));
                String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_TIME));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_DESCRIPTION));
                eventList.add(new Event(id, name, date, time, description));
            } while (cursor.moveToNext());
            cursor.close();
        }

        eventsAdapter.notifyDataSetChanged();
    }


    // Method to check SMS permissions and send text
    private void sendSMSAlert() {
        SmsManager smsManager = SmsManager.getDefault();
        String phoneNo; //Replace with user phone number
        String message = "You have an event today! Log in to check it out!";
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_SMS_APPROVAL, MODE_PRIVATE);
        boolean switchState = sharedPreferences.getBoolean("switch_state", false);

        if (ContextCompat.checkSelfPermission(EventsListActivity.this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            if (!switchState) {
                DatabaseHelper dbHelper = new DatabaseHelper(this);

                if (dbHelper.getEventsByDate(currentDate) != null) {
                    //Get SMS to send
                    //smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(this, "There's an event today! Expect a text!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "No events today!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}


package com.zybooks.cs360_project2;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.util.Set;

public class EventsListActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "settings_prefs";
    private static final String PREF_NOTIFICATIONS_ENABLED = "notifications_enabled";
    private static final String CHANNEL_ID = "event_reminders";
    private static final int NOTIFICATION_ID = 1001;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private DatabaseHelper dbHelper;
    private EventsAdapter eventsAdapter;
    private final List<Event> eventList = new ArrayList<>();
    private MaterialCalendarView calendarView;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);

        dbHelper = new DatabaseHelper(this);
        calendarView = findViewById(R.id.calendar_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        ImageButton settingsButton = findViewById(R.id.SettingsButton);

        createNotificationChannel();
        requestNotificationPermission();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsAdapter = new EventsAdapter(eventList);
        recyclerView.setAdapter(eventsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        loadEventsByDate(selectedDate);
        decorateCalendar();
        sendPushNotificationIfEventToday();

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", date.getYear(), date.getMonth() + 1, date.getDay());
            loadEventsByDate(selectedDate);
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(EventsListActivity.this, EventAddActivity.class);
            intent.putExtra("selected_date", selectedDate);
            startActivity(intent);
        });

        //TO DO: RE-IMPLENENT SETTINGS w/ day/night changes to calendar and SMS/Push changes
        /*settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventsListActivity.this, SettingsActivity.class);
            startActivity(intent);
        }); */
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEventsByDate(selectedDate);
        decorateCalendar();
        sendPushNotificationIfEventToday();
    }

    private void loadEventsByDate(String date) {
        EventViewModel viewModel = new ViewModelProvider(this).get(EventViewModel.class);
        viewModel.getEventsByDate(selectedDate).observe(this, events -> {
            eventList.clear();
            eventList.addAll(events);
            eventsAdapter.notifyDataSetChanged();
        });

    }

    private void decorateCalendar() {
        calendarView.removeDecorators();

        Set<CalendarDay> eventDates = new HashSet<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS,
                new String[]{DatabaseHelper.COLUMN_EVENT_DATE},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String dateStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENT_DATE));
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = sdf.parse(dateStr);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    eventDates.add(CalendarDay.from(cal));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        calendarView.addDecorator(new EventDateDecorator(eventDates, this));
        calendarView.addDecorator(new TodayDecorator(CalendarDay.today(),
                ContextCompat.getDrawable(this, R.drawable.today_circle)));
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Event Reminders";
            String description = "Notifications for today's events";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void sendPushNotificationIfEventToday() {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean notificationsEnabled = sharedPreferences.getBoolean(PREF_NOTIFICATIONS_ENABLED, false);

        Cursor cursor = dbHelper.getEventsByDate(today);

        if (notificationsEnabled && cursor != null && cursor.moveToFirst()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.calendar_icon)
                    .setContentTitle("Events Today")
                    .setContentText("You have one or more events scheduled today!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(NOTIFICATION_ID, builder.build());

            cursor.close();
        }
    }
}

package com.zybooks.cs360_project2;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import java.util.*;

public class EventsListActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "settings_prefs";
    private static final String PREF_NOTIFICATIONS_ENABLED = "notifications_enabled";
    private static final String CHANNEL_ID = "event_reminders";
    private static final int NOTIFICATION_ID = 1001;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private EventsAdapter eventsAdapter;
    private final List<Event> eventList = new ArrayList<>();
    private final Set<CalendarDay> eventDates = new HashSet<>();
    private MaterialCalendarView calendarView;
    private String selectedDate;
    private String username;
    private EventViewModel eventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        username = prefs.getString("username", "");

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

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        loadEventsByDate(selectedDate);

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", date.getYear(), date.getMonth() + 1, date.getDay());
            loadEventsByDate(selectedDate);
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(EventsListActivity.this, EventAddActivity.class);
            intent.putExtra("selected_date", selectedDate);
            startActivity(intent);
        });

        //Settings button
        settingsButton.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEventsByDate(selectedDate);
    }

    private void loadEventsByDate(String date) {
        eventViewModel.getEventsByDate(date, username).observe(this, events -> {
            eventList.clear();
            eventList.addAll(events);
            eventsAdapter.notifyDataSetChanged();

            // Update calendar decoration based on events
            updateCalendarDecorations(events);
            sendPushNotificationIfEventToday(events);
        });
    }

    private void updateCalendarDecorations(List<Event> eventsForDate) {
        eventDates.clear();

        // Add current date's CalendarDay for decoration
        for (Event e : eventsForDate) {
            try {
                Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(e.getDate());
                if (d != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    eventDates.add(CalendarDay.from(cal));
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        calendarView.removeDecorators();
        calendarView.addDecorator(new EventDateDecorator(eventDates, this));
        calendarView.addDecorator(new TodayDecorator(CalendarDay.today(),
                ContextCompat.getDrawable(this, R.drawable.today_circle)));
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Event Reminders", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notifications for today's events");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
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

    private void sendPushNotificationIfEventToday(List<Event> todayEvents) {
        boolean notificationsEnabled = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .getBoolean(PREF_NOTIFICATIONS_ENABLED, false);

        if (notificationsEnabled && !todayEvents.isEmpty()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.calendar_icon)
                    .setContentTitle("Events Today")
                    .setContentText("You have one or more events scheduled today!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}

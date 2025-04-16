package com.zybooks.cs360_project2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Locale;

public class EventAddActivity extends AppCompatActivity {
    private EditText eventNameEditText, eventDateEditText, eventTimeEditText, eventDescriptionEditText;
    private EventViewModel eventViewModel;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_add);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        calendar = Calendar.getInstance();

        eventNameEditText = findViewById(R.id.event_name);
        eventDateEditText = findViewById(R.id.event_date);
        eventTimeEditText = findViewById(R.id.event_time);
        eventDescriptionEditText = findViewById(R.id.event_description);
        Button saveEventButton = findViewById(R.id.save_event_button);

        eventDateEditText.setFocusable(false);
        eventTimeEditText.setFocusable(false);
        eventDateEditText.setOnClickListener(v -> showDatePicker());
        eventTimeEditText.setOnClickListener(v -> showTimePicker());

        String selectedDate = getIntent().getStringExtra("selected_date");
        if (selectedDate != null) eventDateEditText.setText(selectedDate);

        saveEventButton.setOnClickListener(v -> saveEvent());
    }

    private void saveEvent() {
        String name = eventNameEditText.getText().toString().trim();
        String date = eventDateEditText.getText().toString().trim();
        String time = eventTimeEditText.getText().toString().trim();
        String description = eventDescriptionEditText.getText().toString().trim();

        if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!time.matches("^([01]\\d|2[0-3]):[0-5]\\d$")) {
            Toast.makeText(this, "Invalid time format. Please use HH:mm", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = prefs.getString("username", "");

        Event event = new Event(0, name, date, time, description);
        eventViewModel.addEvent(event, username);

        Toast.makeText(this, "Event added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    String formatted = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
                    eventDateEditText.setText(formatted);
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog dialog = new TimePickerDialog(this,
                (view, hour, minute) -> {
                    String formatted = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                    eventTimeEditText.setText(formatted);
                },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        dialog.show();
    }
}


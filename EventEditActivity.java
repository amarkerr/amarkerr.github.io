package com.zybooks.cs360_project2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Locale;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameEditText, eventDateEditText, eventTimeEditText, eventDescriptionEditText;
    private EventViewModel eventViewModel;
    private String eventId;
    private Calendar calendar;
    private Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_edit);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        calendar = Calendar.getInstance();

        eventNameEditText = findViewById(R.id.event_name);
        eventDateEditText = findViewById(R.id.event_date);
        eventTimeEditText = findViewById(R.id.event_time);
        eventDescriptionEditText = findViewById(R.id.event_description);
        Button saveEventButton = findViewById(R.id.save_event_button);

        eventDateEditText.setFocusable(false);
        eventDateEditText.setClickable(true);
        eventTimeEditText.setFocusable(false);
        eventTimeEditText.setClickable(true);

        eventDateEditText.setOnClickListener(v -> showDatePicker());
        eventTimeEditText.setOnClickListener(v -> showTimePicker());

        eventId = getIntent().getStringExtra("event_id");
        if (eventId != null) {
            eventViewModel.getEvent(eventId).observe(this, event -> {
                if (event != null) {
                    currentEvent = event;
                    eventNameEditText.setText(event.getName());
                    eventDateEditText.setText(event.getDate());
                    eventTimeEditText.setText(event.getTime());
                    eventDescriptionEditText.setText(event.getDescription());
                }
            });
        }

        saveEventButton.setOnClickListener(v -> saveEvent());
    }

    private void saveEvent() {
        if (currentEvent == null) return;

        String name = eventNameEditText.getText().toString().trim();
        String date = eventDateEditText.getText().toString().trim();
        String time = eventTimeEditText.getText().toString().trim();
        String description = eventDescriptionEditText.getText().toString().trim();

        if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!time.matches("^([01]\\d|2[0-3]):[0-5]\\d$")) {
            Toast.makeText(this, "Invalid time format. Use HH:mm", Toast.LENGTH_SHORT).show();
            return;
        }

        currentEvent.setName(name);
        currentEvent.setDate(date);
        currentEvent.setTime(time);
        currentEvent.setDescription(description);

        eventViewModel.updateEvent(currentEvent);
        Toast.makeText(this, "Event updated successfully", Toast.LENGTH_SHORT).show();
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

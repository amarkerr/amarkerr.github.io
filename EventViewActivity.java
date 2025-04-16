package com.zybooks.cs360_project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class EventViewActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    private String eventId;
    private Event currentEvent;

    private TextView eventNameTextView;
    private TextView eventDateTextView;
    private TextView eventTimeTextView;
    private TextView eventDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        eventNameTextView = findViewById(R.id.view_event_name);
        eventDateTextView = findViewById(R.id.view_event_date);
        eventTimeTextView = findViewById(R.id.view_event_time);
        eventDescriptionTextView = findViewById(R.id.view_event_description);
        Button editEventButton = findViewById(R.id.edit_event_button);
        Button deleteEventButton = findViewById(R.id.delete_event_button);

        eventId = getIntent().getStringExtra("event_id");
        if (eventId != null) {
            eventViewModel.getEvent(eventId).observe(this, event -> {
                if (event != null) {
                    currentEvent = event;
                    eventNameTextView.setText(event.getName());
                    eventDateTextView.setText(event.getDate());
                    eventTimeTextView.setText(event.getTime());
                    eventDescriptionTextView.setText(event.getDescription());
                }
            });
        }

        editEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventViewActivity.this, EventEditActivity.class);
            intent.putExtra("event_id", eventId);
            startActivity(intent);
        });

        deleteEventButton.setOnClickListener(v -> {
            if (currentEvent != null) {
                eventViewModel.deleteEvent(currentEvent.getId());
                Toast.makeText(this, "Event deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

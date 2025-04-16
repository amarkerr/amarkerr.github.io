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
    private int eventId;

    private TextView eventNameTextView;
    private TextView eventDateTextView;
    private TextView eventTimeTextView;
    private TextView eventDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        eventId = getIntent().getIntExtra("event_id", -1);

        eventNameTextView = findViewById(R.id.view_event_name);
        eventDateTextView = findViewById(R.id.view_event_date);
        eventTimeTextView = findViewById(R.id.view_event_time);
        eventDescriptionTextView = findViewById(R.id.view_event_description);
        Button editEventButton = findViewById(R.id.edit_event_button);
        Button deleteEventButton = findViewById(R.id.delete_event_button);

        if (eventId != -1) {
            loadEventDetails(eventId);
        }

        editEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventViewActivity.this, EventEditActivity.class);
            intent.putExtra("event_id", eventId);
            startActivity(intent);
        });

        deleteEventButton.setOnClickListener(v -> {
            eventViewModel.deleteEvent(eventId);
            Toast.makeText(this, "Event deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (eventId != -1) {
            loadEventDetails(eventId);
        }
    }

    private void loadEventDetails(int id) {
        Event event = eventViewModel.getEvent(id);
        if (event != null) {
            eventNameTextView.setText(event.getName());
            eventDateTextView.setText(event.getDate());
            eventTimeTextView.setText(event.getTime());
            eventDescriptionTextView.setText(event.getDescription());
        } else {
            Toast.makeText(this, "Unable to load event", Toast.LENGTH_SHORT).show();
        }
    }
}

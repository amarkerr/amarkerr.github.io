package com.zybooks.cs360_project2;

import com.google.firebase.firestore.*;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference eventsRef = db.collection("events");

    public interface EventCallback {
        void onSuccess(List<Event> events);
    }

    public interface SingleEventCallback {
        void onSuccess(Event event);
    }

    // 🔍 Get all events on a specific date for a user
    public void getEventsByDate(String date, String username, @NonNull EventCallback callback) {
        eventsRef
                .whereEqualTo("date", date)
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Event> events = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        Event event = doc.toObject(Event.class);
                        event.setId(doc.getId()); // Set the doc ID
                        events.add(event);
                    }
                    callback.onSuccess(events);
                });
    }

    // ➕ Add new event
    public void addEvent(Event event) {
        eventsRef.add(event);
    }

    // ❌ Delete event
    public void deleteEventById(String eventId) {
        eventsRef.document(eventId).delete();
    }

    // 🔎 Get event by ID
    public void getEventById(String eventId, @NonNull SingleEventCallback callback) {
        eventsRef.document(eventId)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        Event event = doc.toObject(Event.class);
                        event.setId(doc.getId());
                        callback.onSuccess(event);
                    }
                });
    }

    // 🔄 Update event
    public void updateEvent(Event event) {
        eventsRef.document(event.getId()).set(event);
    }
}

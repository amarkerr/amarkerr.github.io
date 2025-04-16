package com.zybooks.cs360_project2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private final EventRepository repository;
    private final MutableLiveData<List<Event>> eventsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Event> singleEventLiveData = new MutableLiveData<>();

    public EventViewModel(@NonNull Application application) {
        super(application);
        repository = new EventRepository();
    }

    // 🔹 Fetch events by date & user
    public LiveData<List<Event>> getEventsByDate(String date, String username) {
        repository.getEventsByDate(date, username, events -> eventsLiveData.setValue(events));
        return eventsLiveData;
    }

    // 🔹 Add new event
    public void addEvent(Event event) {
        repository.addEvent(event);
    }

    // 🔹 Update existing event
    public void updateEvent(Event event) {
        repository.updateEvent(event);
    }

    // 🔹 Delete event by ID
    public void deleteEvent(String eventId) {
        repository.deleteEventById(eventId);
    }

    // 🔹 Fetch a single event
    public LiveData<Event> getEvent(String eventId) {
        repository.getEventById(eventId, event -> singleEventLiveData.setValue(event));
        return singleEventLiveData;
    }
}

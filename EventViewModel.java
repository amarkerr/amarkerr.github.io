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

    public EventViewModel(@NonNull Application application) {
        super(application);
        repository = new EventRepository(application);
    }

    public LiveData<List<Event>> getEventsByDate(String date) {
        List<Event> events = repository.getEventsByDate(date);
        eventsLiveData.setValue(events);
        return eventsLiveData;
    }

    public void deleteEvent(int eventId) {
        repository.deleteEventById(eventId);
    }

    public Event getEvent(int eventId) {
        return repository.getEventById(eventId);
    }

    public void addEvent(Event event, String username) {
        repository.addEvent(event, username);
    }

    public void updateEvent(Event event) {
        repository.updateEvent(event);
    }
}


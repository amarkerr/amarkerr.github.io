package com.zybooks.cs360_project2;

public class Event {
    private String id;           // Firestore document ID
    private String name;
    private String date;
    private String time;
    private String description;
    private String username;

    // 🔹 Required by Firebase
    public Event() {}

    public Event(String id, String name, String date, String time, String description, String username) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
        this.username = username;
    }

    // 🔹 Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

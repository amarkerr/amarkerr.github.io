package com.zybooks.cs360_project2;

public class Event {
    private int id;
    private String name;
    private String date;
    private String time;
    private String description;

    public Event(int id, String name, String date, String time, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}

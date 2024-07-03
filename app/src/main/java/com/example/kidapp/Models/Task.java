package com.example.kidapp.Models;

public class Task {
    private String id;
    private String description;
    private boolean completed;

    public Task() {
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Task setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }
}

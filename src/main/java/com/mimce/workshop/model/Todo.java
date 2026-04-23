package com.mimce.workshop.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Todo {

    private final String id;
    private String title;
    private boolean completed;
    private final LocalDateTime createdAt;

    public Todo(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}

package com.example.todolistwithsqlite;

public class Task {
    private int id;
    private String name;
    private boolean completed;

    // Constructor có id
    public Task(int id, String name, boolean completed) {
        this.id = id;
        this.name = name;
        this.completed = completed;
    }

    // Constructor không id
    public Task(String name) {
        this.name = name;
        this.completed = false;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

package com.universalyoga.admin;
import java.io.Serializable;

public class ClassModel implements Serializable {
    public int id;
    public String day, time, type, description;
    public int capacity, duration;
    public double price;

    // Constructor dùng khi tạo mới (không có id)
    public ClassModel(String day, String time, int capacity, int duration, double price, String type, String description) {
        this.day = day;
        this.time = time;
        this.capacity = capacity;
        this.duration = duration;
        this.price = price;
        this.type = type;
        this.description = description;
    }

    // Constructor dùng khi lấy từ database (có id)
    public ClassModel(int id, String day, String time, int capacity, int duration, double price, String type, String description) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.capacity = capacity;
        this.duration = duration;
        this.price = price;
        this.type = type;
        this.description = description;
    }
}

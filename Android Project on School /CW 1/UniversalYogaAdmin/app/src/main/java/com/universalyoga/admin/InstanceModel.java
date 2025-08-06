package com.universalyoga.admin;

import java.io.Serializable;

public class InstanceModel implements Serializable {
    public int id;
    public int classId;
    public String date;
    public String teacher;
    public String comment;

    public InstanceModel(int classId, String date, String teacher, String comment) {
        this.classId = classId;
        this.date = date;
        this.teacher = teacher;
        this.comment = comment;
    }
}

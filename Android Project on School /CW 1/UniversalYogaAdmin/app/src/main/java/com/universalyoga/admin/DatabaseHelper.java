package com.universalyoga.admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "YogaDB";
    public static final int DB_VERSION = 1;

    public static final String TABLE_CLASSES = "classes";
    public static final String TABLE_INSTANCES = "instances";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng lớp học
        String CREATE_CLASSES = "CREATE TABLE " + TABLE_CLASSES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "day TEXT, time TEXT, capacity INTEGER, duration INTEGER," +
                "price REAL, type TEXT, description TEXT)";
        db.execSQL(CREATE_CLASSES);

        // Bảng instance
        String CREATE_INSTANCES = "CREATE TABLE " + TABLE_INSTANCES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "class_id INTEGER," +
                "date TEXT," +
                "teacher TEXT," +
                "comment TEXT)";
        db.execSQL(CREATE_INSTANCES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTANCES);
        onCreate(db);
    }

    // ============================= CLASS CRUD =============================

    public void insertClass(ClassModel c) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("day", c.day);
        v.put("time", c.time);
        v.put("capacity", c.capacity);
        v.put("duration", c.duration);
        v.put("price", c.price);
        v.put("type", c.type);
        v.put("description", c.description);
        db.insert(TABLE_CLASSES, null, v);
        db.close();
    }

    public void updateClass(ClassModel c) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("day", c.day);
        v.put("time", c.time);
        v.put("capacity", c.capacity);
        v.put("duration", c.duration);
        v.put("price", c.price);
        v.put("type", c.type);
        v.put("description", c.description);
        db.update(TABLE_CLASSES, v, "id=?", new String[]{String.valueOf(c.id)});
        db.close();
    }

    public List<ClassModel> getAllClasses() {
        List<ClassModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CLASSES, null);
        while (c.moveToNext()) {
            ClassModel m = new ClassModel(
                    c.getString(1), c.getString(2), c.getInt(3),
                    c.getInt(4), c.getDouble(5),
                    c.getString(6), c.getString(7)
            );
            m.id = c.getInt(0);
            list.add(m);
        }
        c.close();
        return list;
    }

    public void deleteClass(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CLASSES, "id=?", new String[]{String.valueOf(id)});
    }

    // ============================= INSTANCE CRUD =============================

    public void insertInstance(InstanceModel ins) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("class_id", ins.classId);
        v.put("date", ins.date);
        v.put("teacher", ins.teacher);
        v.put("comment", ins.comment);
        db.insert(TABLE_INSTANCES, null, v);
        db.close();
    }

    public List<InstanceModel> getInstancesForClass(int classId) {
        List<InstanceModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_INSTANCES + " WHERE class_id=?", new String[]{String.valueOf(classId)});
        while (c.moveToNext()) {
            InstanceModel i = new InstanceModel(
                    c.getInt(1), // class_id
                    c.getString(2), // date
                    c.getString(3), // teacher
                    c.getString(4)  // comment
            );
            i.id = c.getInt(0); // id của instance
            list.add(i);
        }
        c.close();
        return list;
    }

    public void deleteInstance(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_INSTANCES, "id=?", new String[]{String.valueOf(id)});
    }
}

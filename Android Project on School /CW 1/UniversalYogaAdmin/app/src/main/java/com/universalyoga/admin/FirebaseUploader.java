package com.universalyoga.admin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseUploader {

    private final Context context;
    private final DatabaseHelper db;
    private final FirebaseFirestore firestore;

    public FirebaseUploader(Context context, DatabaseHelper db) {
        this.context = context;
        this.db = db;
        this.firestore = FirebaseFirestore.getInstance();
    }

    public void uploadAllClasses() {
        if (!isNetworkAvailable()) {
            Toast.makeText(context, "No internet connection!", Toast.LENGTH_SHORT).show();
            return;
        }

        List<ClassModel> classes = db.getAllClasses();

        for (ClassModel c : classes) {
            Map<String, Object> data = new HashMap<>();
            data.put("day", c.day);
            data.put("time", c.time);
            data.put("capacity", c.capacity);
            data.put("duration", c.duration);
            data.put("price", c.price);
            data.put("type", c.type);
            data.put("description", c.description);

            firestore.collection("yoga_classes")
                    .add(data)
                    .addOnSuccessListener(docRef ->
                            Toast.makeText(context, "Uploaded: " + c.day, Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Failed: " + c.day, Toast.LENGTH_SHORT).show());
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}

package com.universalyoga.admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.universalyoga.admin.DatabaseHelper;
import com.universalyoga.admin.ClassModel;
import com.universalyoga.admin.R;

public class AddClassActivity extends AppCompatActivity {

    EditText edtDay, edtTime, edtCapacity, edtDuration, edtPrice, edtType, edtDescription;
    Button btnPreview;
    DatabaseHelper db;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        db = new DatabaseHelper(this);
        firestore = FirebaseFirestore.getInstance();

        edtDay = findViewById(R.id.edtDay);
        edtTime = findViewById(R.id.edtTime);
        edtCapacity = findViewById(R.id.edtCapacity);
        edtDuration = findViewById(R.id.edtDuration);
        edtPrice = findViewById(R.id.edtPrice);
        edtType = findViewById(R.id.edtType);
        edtDescription = findViewById(R.id.edtDescription);
        btnPreview = findViewById(R.id.btnPreview);

        btnPreview.setOnClickListener(v -> {
            if (validateInputs()) {
                ClassModel c = collectData();
                showPreviewDialog(c);
            }
        });
    }

    private boolean validateInputs() {
        if (TextUtils.isEmpty(edtDay.getText()) || TextUtils.isEmpty(edtTime.getText()) ||
                TextUtils.isEmpty(edtCapacity.getText()) || TextUtils.isEmpty(edtDuration.getText()) ||
                TextUtils.isEmpty(edtPrice.getText()) || TextUtils.isEmpty(edtType.getText())) {

            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private ClassModel collectData() {
        return new ClassModel(
                edtDay.getText().toString(),
                edtTime.getText().toString(),
                Integer.parseInt(edtCapacity.getText().toString()),
                Integer.parseInt(edtDuration.getText().toString()),
                Double.parseDouble(edtPrice.getText().toString()),
                edtType.getText().toString(),
                edtDescription.getText().toString()
        );
    }

    private void showPreviewDialog(ClassModel c) {
        String msg = "Day: " + c.day + "\nTime: " + c.time +
                "\nCapacity: " + c.capacity + "\nDuration: " + c.duration + " mins" +
                "\nPrice: £" + c.price + "\nType: " + c.type +
                "\nDescription: " + (c.description.isEmpty() ? "(none)" : c.description);

        new AlertDialog.Builder(this)
                .setTitle("Confirm Class Info")
                .setMessage(msg)
                .setPositiveButton("Save", (dialog, which) -> saveData(c))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveData(ClassModel c) {
        // 1. Lưu SQLite
        db.insertClass(c);

        // 2. Lưu Firebase
        firestore.collection("yoga_classes").add(toMap(c))
                .addOnSuccessListener(doc -> Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Firebase failed", Toast.LENGTH_SHORT).show());
    }

    private java.util.Map<String, Object> toMap(ClassModel c) {
        java.util.Map<String, Object> m = new java.util.HashMap<>();
        m.put("day", c.day);
        m.put("time", c.time);
        m.put("capacity", c.capacity);
        m.put("duration", c.duration);
        m.put("price", c.price);
        m.put("type", c.type);
        m.put("description", c.description);
        return m;
    }
}

// EditClassActivity.java
package com.universalyoga.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class EditClassActivity extends AppCompatActivity {

    EditText edtDay, edtTime, edtCapacity, edtDuration, edtPrice, edtType, edtDescription;
    Button btnUpdate;
    DatabaseHelper db;
    ClassModel c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class); // Dùng lại layout thêm lớp

        db = new DatabaseHelper(this);
        c = (ClassModel) getIntent().getSerializableExtra("class");

        edtDay = findViewById(R.id.edtDay);
        edtTime = findViewById(R.id.edtTime);
        edtCapacity = findViewById(R.id.edtCapacity);
        edtDuration = findViewById(R.id.edtDuration);
        edtPrice = findViewById(R.id.edtPrice);
        edtType = findViewById(R.id.edtType);
        edtDescription = findViewById(R.id.edtDescription);
        btnUpdate = findViewById(R.id.btnPreview);

        btnUpdate.setText("Update");

        // Set dữ liệu cũ vào form
        edtDay.setText(c.day);
        edtTime.setText(c.time);
        edtCapacity.setText(String.valueOf(c.capacity));
        edtDuration.setText(String.valueOf(c.duration));
        edtPrice.setText(String.valueOf(c.price));
        edtType.setText(c.type);
        edtDescription.setText(c.description);

        btnUpdate.setOnClickListener(v -> {
            if (validateInputs()) {
                c.day = edtDay.getText().toString();
                c.time = edtTime.getText().toString();
                c.capacity = Integer.parseInt(edtCapacity.getText().toString());
                c.duration = Integer.parseInt(edtDuration.getText().toString());
                c.price = Double.parseDouble(edtPrice.getText().toString());
                c.type = edtType.getText().toString();
                c.description = edtDescription.getText().toString();

                db.updateClass(c);
                Toast.makeText(this, "Class updated", Toast.LENGTH_SHORT).show();
                finish();
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
}

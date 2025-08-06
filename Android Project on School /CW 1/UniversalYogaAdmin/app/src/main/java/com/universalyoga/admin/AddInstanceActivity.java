package com.universalyoga.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AddInstanceActivity extends AppCompatActivity {

    EditText edtDate, edtTeacher, edtComment;
    Button btnAddInstance;
    DatabaseHelper db;
    int classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instance);

        edtDate = findViewById(R.id.edtDate);
        edtTeacher = findViewById(R.id.edtTeacher);
        edtComment = findViewById(R.id.edtComment);
        btnAddInstance = findViewById(R.id.btnAddInstance);
        db = new DatabaseHelper(this);

        classId = getIntent().getIntExtra("class_id", -1);

        btnAddInstance.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtDate.getText()) || TextUtils.isEmpty(edtTeacher.getText())) {
                Toast.makeText(this, "Date & Teacher are required", Toast.LENGTH_SHORT).show();
                return;
            }

            InstanceModel ins = new InstanceModel(
                    classId,
                    edtDate.getText().toString(),
                    edtTeacher.getText().toString(),
                    edtComment.getText().toString()
            );

            db.insertInstance(ins);
            Toast.makeText(this, "Instance added", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

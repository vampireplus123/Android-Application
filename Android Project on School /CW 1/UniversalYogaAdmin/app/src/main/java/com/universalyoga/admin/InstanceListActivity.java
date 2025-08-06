package com.universalyoga.admin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InstanceListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper db;
    int classId;
    List<InstanceModel> instances;
    InstanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);
        classId = getIntent().getIntExtra("class_id", -1);

        instances = db.getInstancesForClass(classId);
        adapter = new InstanceAdapter(this, instances, db);
        recyclerView.setAdapter(adapter);
    }
}

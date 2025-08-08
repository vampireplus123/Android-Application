package com.universalyoga.admin;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InstanceListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton btnAddInstance;
    DatabaseHelper db;
    int classId;
    List<InstanceModel> instances;
    InstanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance_list);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddInstance = findViewById(R.id.btnAddInstance);

        db = new DatabaseHelper(this);
        classId = getIntent().getIntExtra("class_id", -1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        instances = new ArrayList<>();
        adapter = new InstanceAdapter(this, instances, db);
        recyclerView.setAdapter(adapter);

        btnAddInstance.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddInstanceActivity.class);
            intent.putExtra("class_id", classId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        instances.clear();
        instances.addAll(db.getInstancesForClass(classId));
        adapter.notifyDataSetChanged();
    }
}

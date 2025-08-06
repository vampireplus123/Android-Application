package com.universalyoga.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton btnAdd, btnSearch, btnUpload;
    DatabaseHelper db;
    List<ClassModel> classList;
    ClassAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        classList.clear();
        classList.addAll(db.getAllClasses());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classList = db.getAllClasses();

        adapter = new ClassAdapter(this, classList, db, classModel -> {
            Intent i = new Intent(MainActivity.this, InstanceListActivity.class);
            i.putExtra("class_id", classModel.id);
            startActivity(i);
        });
        recyclerView.setAdapter(adapter);

        btnAdd = findViewById(R.id.btnAdd);
        btnSearch = findViewById(R.id.btnSearch);
        btnUpload = findViewById(R.id.btnUpload);

        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(this, AddClassActivity.class);
            startActivity(i);
        });

        btnSearch.setOnClickListener(v -> {
            startActivity(new Intent(this, SearchActivity.class));
        });

        btnUpload.setOnClickListener(v -> {
            FirebaseUploader uploader = new FirebaseUploader(this, db);
            uploader.uploadAllClasses();
        });
    }
}

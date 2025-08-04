package com.example.todolistwithsqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private EditText editTask;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        editTask = findViewById(R.id.editTask);
        listView = findViewById(R.id.listViewTasks);
        Button btnAdd = findViewById(R.id.btnAdd);

        taskList = db.getAllTasks();
        adapter = new TaskAdapter(this, taskList, db);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String taskName = editTask.getText().toString().trim();
            if (!taskName.isEmpty()) {
                Task task = new Task(taskName);
                db.addTask(task);
                taskList.clear();
                taskList.addAll(db.getAllTasks());
                adapter.notifyDataSetChanged();
                editTask.setText("");
            }
        });
    }
}

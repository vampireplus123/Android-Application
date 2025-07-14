package com.example.todolist_logbook;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTask;
    private Button btnAdd;
    private ListView listView;

    private List<Task> taskList;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Mapping view
        editTask = findViewById(R.id.editTask);
        btnAdd = findViewById(R.id.btnAdd);
        listView = findViewById(R.id.listView);

        // Khởi tạo danh sách và adapter
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, taskList);
        listView.setAdapter(taskAdapter);

        // Sự kiện khi bấm nút "Add"
        btnAdd.setOnClickListener(v -> {
            String taskName = editTask.getText().toString().trim();
            if (!taskName.isEmpty()) {
                taskList.add(new Task(taskName));
                taskAdapter.notifyDataSetChanged();
                editTask.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Please enter task name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.todolist_logbook;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private List<Task> tasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        }

        TextView textTask = convertView.findViewById(R.id.textTask);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        textTask.setText(task.getName());

        // Sửa task
        btnEdit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Edit Task");

            final EditText input = new EditText(context);
            input.setText(task.getName());
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                task.setName(input.getText().toString());
                notifyDataSetChanged();
            });

            builder.setNegativeButton("Cancel", null);
            builder.show();
        });

        // Xóa task
        btnDelete.setOnClickListener(v -> {
            tasks.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }
}

package com.example.todolistwithsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private List<Task> tasks;
    private DatabaseHelper db;

    public TaskAdapter(Context context, List<Task> tasks, DatabaseHelper db) {
        this.context = context;
        this.tasks = tasks;
        this.db = db;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tasks.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
            holder = new TaskViewHolder();
            holder.taskName = convertView.findViewById(R.id.textTaskName);
            holder.checkBox = convertView.findViewById(R.id.checkCompleted);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (TaskViewHolder) convertView.getTag();
        }

        Task task = tasks.get(position);
        holder.taskName.setText(task.getName());
        holder.checkBox.setChecked(task.isCompleted());

        // Sự kiện khi check/uncheck hoàn thành
        holder.checkBox.setOnClickListener(v -> {
            task.setCompleted(holder.checkBox.isChecked());
            db.updateTask(task);
            Toast.makeText(context, "Đã cập nhật trạng thái công việc", Toast.LENGTH_SHORT).show();
        });

        // Sự kiện xóa task
        holder.btnDelete.setOnClickListener(v -> {
            db.deleteTask(task.getId());
            tasks.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Đã xoá công việc", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }

    private static class TaskViewHolder {
        TextView taskName;
        CheckBox checkBox;
        Button btnDelete;
    }
}

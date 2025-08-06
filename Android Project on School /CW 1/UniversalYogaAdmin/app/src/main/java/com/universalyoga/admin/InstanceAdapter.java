// InstanceAdapter.java - Dùng cho RecyclerView trong InstanceListActivity + Xóa instance khi nhấn giữ
package com.universalyoga.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class InstanceAdapter extends RecyclerView.Adapter<InstanceAdapter.ViewHolder> {

    private final Context context;
    private final List<InstanceModel> instanceList;
    private final DatabaseHelper db;

    public InstanceAdapter(Context context, List<InstanceModel> instanceList, DatabaseHelper db) {
        this.context = context;
        this.instanceList = instanceList;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InstanceModel instance = instanceList.get(position);
        holder.date.setText(instance.date);
        holder.teacher.setText(instance.teacher);

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete this instance?")
                    .setMessage("Are you sure you want to delete it?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.deleteInstance(instance.id);
                        instanceList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return instanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, teacher;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(android.R.id.text1);
            teacher = itemView.findViewById(android.R.id.text2);
        }
    }
}

// ClassAdapter.java (có callback khi click item)
package com.universalyoga.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {
    Context context;
    List<ClassModel> classList;
    DatabaseHelper db;
    OnClassClickListener listener;

    public interface OnClassClickListener {
        void onClick(ClassModel c);
    }

    public ClassAdapter(Context context, List<ClassModel> classList, DatabaseHelper db, OnClassClickListener listener) {
        this.context = context;
        this.classList = classList;
        this.db = db;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ViewHolder holder, int position) {
        ClassModel c = classList.get(position);
        holder.title.setText(c.day + " - " + c.time);
        holder.subtitle.setText(c.type + " | £" + c.price);

        holder.itemView.setOnClickListener(v -> listener.onClick(c));

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete this class?")
                    .setMessage("Are you sure you want to delete it?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.deleteClass(c.id);
                        classList.remove(position);
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
        return classList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(android.R.id.text1);
            subtitle = itemView.findViewById(android.R.id.text2);
        }
    }
}

package com.universalyoga.admin;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    EditText edtSearch;
    Button btnSearch;
    ListView listView;
    DatabaseHelper db;
    ArrayAdapter<String> adapter;
    List<ClassModel> foundClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        listView = findViewById(R.id.listView);

        db = new DatabaseHelper(this);
        foundClasses = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String keyword = edtSearch.getText().toString().trim().toLowerCase();
            adapter.clear();
            foundClasses.clear();

            for (ClassModel c : db.getAllClasses()) {
                if (c.day.toLowerCase().contains(keyword) ||
                        c.type.toLowerCase().contains(keyword)) {
                    foundClasses.add(c);
                    adapter.add(c.day + " - " + c.time + " | " + c.type);
                }
            }

            if (foundClasses.isEmpty()) {
                adapter.add("No results found.");
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= foundClasses.size()) return;
            ClassModel c = foundClasses.get(position);

            String detail = "Day: " + c.day + "\nTime: " + c.time +
                    "\nType: " + c.type + "\nPrice: £" + c.price +
                    "\nCapacity: " + c.capacity + "\nDuration: " + c.duration + " mins" +
                    "\nDescription: " + (c.description.isEmpty() ? "(none)" : c.description);

            new AlertDialog.Builder(this)
                    .setTitle("Class Details")
                    .setMessage(detail)
                    .setPositiveButton("OK", null)
                    .show();
        });
    }
}

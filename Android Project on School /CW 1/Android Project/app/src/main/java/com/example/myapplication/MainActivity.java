package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputValue;
    Spinner fromSpinner, toSpinner;
    Button convertButton;
    TextView resultText;

    String[] units = {"Meter", "Kilometer", "Centimeter", "Mile", "Foot"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        fromSpinner.setSelection(0); // Mặc định chọn Meter
        toSpinner.setSelection(1);   // Mặc định chọn Kilometer

        // set OnClickListener cho nút chuyển đổi
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertLength();
            }
        });
    }

    private void convertLength() {
        String input = inputValue.getText().toString();
        if (input.isEmpty()) {
            resultText.setText("Please enter a value.");
            return;
        }

        double value = Double.parseDouble(input);
        String fromUnit = fromSpinner.getSelectedItem().toString();
        String toUnit = toSpinner.getSelectedItem().toString();
        double result = convert(value, fromUnit, toUnit);
        resultText.setText(String.format("%.2f %s", result, toUnit));
    }
    private double convert(double value, String from, String to) {
        double meters;

        // Chuyển từ đơn vị nguồn về mét
        switch (from) {
            case "Kilometer":
                meters = value * 1000;
                break;
            case "Centimeter":
                meters = value / 100;
                break;
            case "Mile":
                meters = value * 1609.344;  // 1 mile = 1609.344 meters
                break;
            case "Foot":
                meters = value * 0.3048;    // 1 foot = 0.3048 meters
                break;
            default:  // Meter
                meters = value;
                break;
        }

        // Chuyển từ mét sang đơn vị đích
        double result;
        switch (to) {
            case "Kilometer":
                result = meters / 1000;
                break;
            case "Centimeter":
                result = meters * 100;
                break;
            case "Mile":
                result = meters / 1609.344;
                break;
            case "Foot":
                result = meters / 0.3048;
                break;
            default:  // Meter
                result = meters;
                break;
        }

        return result;
    }


}

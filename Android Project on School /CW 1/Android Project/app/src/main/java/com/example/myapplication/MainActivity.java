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

    String[] units = {"Meter", "Kilometer", "Centimeter"};

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

        switch (from) {
            case "Kilometer":
                meters = value * 1000;
                break;
            case "Centimeter":
                meters = value / 100;
                break;
            default:
                meters = value;
                break;
        }

        double result;
        switch (to) {
            case "Kilometer":
                result = meters / 1000;
                break;
            case "Centimeter":
                result = meters * 100;
                break;
            default:
                result = meters;
                break;
        }

        return result;
    }

}

package com.example.samplebmi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText etWeightView;
    private EditText etHeightView;
    private TextView tvResultNumberView;
    private DecimalFormat decimalFormat;
    private ConstraintLayout clMale;
    private ConstraintLayout clFemale;
    private String selectedGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        initializeViews();

        // Set up button click listener
        setUpClickListener();

        // Init selected gender background
        initGenderBG();
    }

    private void initGenderBG() {
        clMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedGender = "Male";
                clMale.setBackground(getResources().getDrawable(R.drawable.selected_gender_bg));
                clFemale.setBackground(getResources().getDrawable(R.drawable.selected_gender_bg_normal));
            }
        });

        clFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedGender = "Female";
                clMale.setBackground(getResources().getDrawable(R.drawable.selected_gender_bg_normal));
                clFemale.setBackground(getResources().getDrawable(R.drawable.selected_gender_bg));
            }
        });
    }

    private void initializeViews() {
        clMale = findViewById(R.id.clMale);
        clFemale = findViewById(R.id.clFemale);
        etWeightView = findViewById(R.id.etWeight);
        etHeightView = findViewById(R.id.etHeight);
        tvResultNumberView = findViewById(R.id.tvResultNumberView);
        decimalFormat = new DecimalFormat("###.##");
    }

    private void setUpClickListener() {
        Button btnCalculateView = findViewById(R.id.btnCalculateBMI);

        // When the user clicks the button
        btnCalculateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the hasValue function returns true
                if (hasValidInput()) {
                    calculateBMI();
                }
            }
        });
    }

    private boolean hasValidInput() {
        String weight = etWeightView.getText().toString();
        String height = etHeightView.getText().toString();

        if (isNullOrEmpty(weight)) {
            showToast("Please enter your weight");
            return false;
        } else if (isNullOrEmpty(height)) {
            showToast("Please enter your height");
            return false;
        }

        return true;
    }

    private boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void calculateBMI() {
        double weightValue = Double.parseDouble(etWeightView.getText().toString());
        double heightValue = Double.parseDouble(etHeightView.getText().toString());

        double convertedHeightValue = Math.pow((heightValue * 0.01), 2.0);
        double bmiValue = weightValue / convertedHeightValue;

        // Set the result with two decimal places
        tvResultNumberView.setText(decimalFormat.format(bmiValue));

        if (bmiValue < 18.5) displayToast("Underweight");
        else if (bmiValue >= 18.5 && bmiValue <= 24.99) displayToast("Healthy");
        else if (bmiValue >= 25.0 && bmiValue <= 29.99) displayToast("Overweight");
        else displayToast("Obese");
    }

    private void displayToast(String message) {
        Toast.makeText(
                this,
                selectedGender + ": " + message,
                Toast.LENGTH_SHORT
        ).show();
    }
}
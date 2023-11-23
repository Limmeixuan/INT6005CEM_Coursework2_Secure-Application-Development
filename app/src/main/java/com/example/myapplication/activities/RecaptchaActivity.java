package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;

public class RecaptchaActivity extends AppCompatActivity {

    private CheckBox checkBox;
    private GoogleApiClient googleApiClient;
    private String siteKey = "6LecbxQpAAAAAOqvBzZkoj1oQK-09sqbirjaQk3-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recaptcha2);

        checkBox = findViewById(R.id.CheckBox_Id);
        Button okButton = findViewById(R.id.okButton);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .build();

        googleApiClient.connect();

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    // Perform reCAPTCHA verification when the checkbox is checked
                    verifyRecaptcha();
                } else {
                    checkBox.setTextColor(Color.RED);
                    Toast.makeText(RecaptchaActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    verifyRecaptcha();
                } else {
                    Toast.makeText(RecaptchaActivity.this, "Please check the verification checkbox.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to perform reCAPTCHA verification
    private void verifyRecaptcha() {
        SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient, siteKey)
                .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                    @Override
                    public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                        Status status = recaptchaTokenResult.getStatus();
                        if (status != null && status.isSuccess()) {
                            // If verification successful
                            Toast.makeText(RecaptchaActivity.this, "Verification successful", Toast.LENGTH_SHORT).show();
                            checkBox.setTextColor(Color.BLUE);
                            navigateToHomePage();
                        } else {
                            // If verification failed
                            Toast.makeText(RecaptchaActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
                            checkBox.setTextColor(Color.RED);
                            checkBox.setChecked(false); // Uncheck the checkbox if verification fails
                        }
                    }
                });
    }

    // Method to navigate to the home page
    private void navigateToHomePage() {
        Intent intent = new Intent(RecaptchaActivity.this, HomePage.class);
        startActivity(intent);
        finish(); // Finish the current activity
        Toast.makeText(RecaptchaActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
    }
}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private Button medicineBtn, reminderBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        medicineBtn = (Button) findViewById(R.id.medicineBtn);
        medicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMedicine();
            }
        });

        reminderBtn = (Button) findViewById(R.id.reminderBtn);
        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReminder();
            }
        });

        logoutBtn = (Button) findViewById(R.id.logoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePage.this, Login.class));
            }
        });
    }

    public void openMedicine(){
        Intent intent = new Intent(this, Medicine.class);
        startActivity(intent);
    }

    public void openReminder(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
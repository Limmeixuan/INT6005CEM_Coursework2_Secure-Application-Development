package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Medicine;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class HomePage extends AppCompatActivity {

    private Button medicineBtn, reminderBtn, logoutBtn, changeBtn, medicineListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Objects.requireNonNull(getSupportActionBar()).hide();

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
                startActivity(new Intent(HomePage.this, LoginActivity.class));
            }
        });

        changeBtn = (Button) findViewById(R.id.changeBtn);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChangePassword();
            }
        });

        medicineListBtn = (Button) findViewById(R.id.medicineListBtn);
        medicineListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, MedicineListActivity.class);
                startActivity(intent);
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

    public void openChangePassword(){
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }
}
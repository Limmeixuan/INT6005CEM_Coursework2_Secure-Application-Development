package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {

    private CardView userCv, adminCv, pharmacistCv, logoutBtn;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Objects.requireNonNull(getSupportActionBar()).hide();

        userCv = findViewById(R.id.userCv);
        adminCv = findViewById(R.id.adminCv);
        pharmacistCv = findViewById(R.id.pharmacistCv);
        logoutBtn = findViewById(R.id.logoutBtn);
        userCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });

        adminCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminListActivity.class);
                startActivity(intent);
            }
        });

        pharmacistCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, PharmacistListActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
            }
        });
    }
}
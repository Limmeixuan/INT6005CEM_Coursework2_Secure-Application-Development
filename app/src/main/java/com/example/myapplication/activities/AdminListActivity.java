package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AdminAdapterAdmin;
import com.example.myapplication.models.AdminModelAdmin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class AdminListActivity extends AppCompatActivity {

    private RecyclerView adminRv;
    private Button addAdminBtn;
    private FirebaseAuth mFirebaseAuth;

    private ArrayList<AdminModelAdmin> mAdminList;
    private AdminAdapterAdmin mAdminAdapterAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        Objects.requireNonNull(getSupportActionBar()).hide();

        adminRv = findViewById(R.id.adminRv);
        addAdminBtn = findViewById(R.id.addAdminBtn);

        mFirebaseAuth = FirebaseAuth.getInstance();
        loadAllAdmin();

        addAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminListActivity.this, AddAdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadAllAdmin() {
        mAdminList = new ArrayList<>();

        // Get all users with the "Admin" account type
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdminList.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Inside this loop, 'userSnapshot' represents each user

                    // Check if the user has an "accountType" field and it equals "Admin"
                    if (userSnapshot.child("accountType").getValue(String.class) != null
                            && userSnapshot.child("accountType").getValue(String.class).equals("Admin")) {
                        // This user is an admin
                        AdminModelAdmin adminModelAdmin = userSnapshot.getValue(AdminModelAdmin.class);
                        mAdminList.add(adminModelAdmin);
                    }
                }

                // Setup adapter
                mAdminAdapterAdmin = new AdminAdapterAdmin(AdminListActivity.this, mAdminList);
                // Set the adapter
                adminRv.setAdapter(mAdminAdapterAdmin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }

}
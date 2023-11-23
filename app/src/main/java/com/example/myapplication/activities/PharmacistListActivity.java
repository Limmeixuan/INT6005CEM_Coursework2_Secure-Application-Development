package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AdminAdapterPharmacist;
import com.example.myapplication.models.AdminModelPharmacist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class PharmacistListActivity extends AppCompatActivity {

    private RecyclerView adminRv;
    private Button addPharmacistBtn;
    private FirebaseAuth mFirebaseAuth;

    private ArrayList<AdminModelPharmacist> mPharmacistList;
    private AdminAdapterPharmacist mAdminAdapterPharmacist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_list);

        Objects.requireNonNull(getSupportActionBar()).hide();

        adminRv = findViewById(R.id.adminRv);
        addPharmacistBtn = findViewById(R.id.addPharmacistBtn);

        mFirebaseAuth = FirebaseAuth.getInstance();
        loadAllPharmacist();
        addPharmacistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PharmacistListActivity.this, AddPharmacistActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadAllPharmacist() {
        mPharmacistList = new ArrayList<>();

        // Get all users with the "Admin" account type
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPharmacistList.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Inside this loop, 'userSnapshot' represents each user

                    // Check if the user has an "accountType" field and it equals "Admin"
                    if (userSnapshot.child("accountType").getValue(String.class) != null
                            && userSnapshot.child("accountType").getValue(String.class).equals("Pharmacist")) {
                        // This user is an admin
                        AdminModelPharmacist adminModelPharmacist = userSnapshot.getValue(AdminModelPharmacist.class);
                        mPharmacistList.add(adminModelPharmacist);
                    }
                }

                // Setup adapter
                mAdminAdapterPharmacist = new AdminAdapterPharmacist(PharmacistListActivity.this, mPharmacistList);
                // Set the adapter
                adminRv.setAdapter(mAdminAdapterPharmacist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }
}
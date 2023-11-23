package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AdminAdapterUser;
import com.example.myapplication.models.AdminModelUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView usersRv;
    private FirebaseAuth mFirebaseAuth;

    private ArrayList<AdminModelUser> mUserList;
    private AdminAdapterUser mAdminAdapterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        Objects.requireNonNull(getSupportActionBar()).hide();

        usersRv = findViewById(R.id.usersRv);

        mFirebaseAuth = FirebaseAuth.getInstance();
        loadAllUsers();

    }

    private void loadAllUsers() {
        mUserList = new ArrayList<>();

        // Get all users with the "User" account type
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        Query query = ref.orderByChild("accountType").equalTo("User");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Before getting data, reset the list
                mUserList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    AdminModelUser adminModelUser = ds.getValue(AdminModelUser.class);
                    mUserList.add(adminModelUser);
                }
                // Setup adapter
                mAdminAdapterUser = new AdminAdapterUser(UserListActivity.this, mUserList);
                // Set the adapter
                usersRv.setAdapter(mAdminAdapterUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }

}
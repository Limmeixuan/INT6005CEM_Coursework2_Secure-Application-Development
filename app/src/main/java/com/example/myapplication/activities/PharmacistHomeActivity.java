package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AdapterMedicine;
import com.example.myapplication.models.ModelMedicine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class PharmacistHomeActivity extends AppCompatActivity {

    private Button logoutBtn;
    private RecyclerView medicineRv;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;

    private ArrayList<ModelMedicine> mMedicineList;
    private AdapterMedicine mAdapterMedicine;

    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_home);

        Objects.requireNonNull(getSupportActionBar()).hide();

        FloatingActionButton fab;

        fab = findViewById(R.id.fab);
        medicineRv = findViewById(R.id.medicineRv);
        logoutBtn = findViewById(R.id.logoutBtn);

        mFirebaseAuth = FirebaseAuth.getInstance();
        loadAllMedicine();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PharmacistHomeActivity.this, LoginActivity.class));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PharmacistHomeActivity.this, AddMedicineActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadAllMedicine() {
        mMedicineList = new ArrayList<>();

        //get all medicine list
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Medicine_List");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //before getting reset list
                mMedicineList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ModelMedicine modelMedicine = ds.getValue(ModelMedicine.class);
                    mMedicineList.add(modelMedicine);
                }
                //setup adapter
                mAdapterMedicine = new AdapterMedicine(PharmacistHomeActivity.this, mMedicineList);
                //set adapter
                medicineRv.setAdapter(mAdapterMedicine);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
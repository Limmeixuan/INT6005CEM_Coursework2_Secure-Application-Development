package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AdapterMedicine;
import com.example.myapplication.models.ModelMedicine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MedicineListActivity extends AppCompatActivity {

    private RecyclerView medicineRv;

    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;

    private ArrayList<ModelMedicine> mMedicineList;
    private AdapterMedicine mAdapterMedicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        Objects.requireNonNull(getSupportActionBar()).hide();

        medicineRv = findViewById(R.id.medicineRv);

        mFirebaseAuth = FirebaseAuth.getInstance();
        loadAllMedicine();
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
                mAdapterMedicine = new AdapterMedicine(MedicineListActivity.this, mMedicineList);
                //set adapter
                medicineRv.setAdapter(mAdapterMedicine);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
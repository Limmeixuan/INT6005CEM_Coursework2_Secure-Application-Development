package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class AddMedicineActivity extends AppCompatActivity {

    private EditText medicineTitleEt, medicineDescriptionEt;
    private Button addBtn;

    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        Objects.requireNonNull(getSupportActionBar()).hide();

        medicineTitleEt = findViewById(R.id.medicineTitleEt);
        medicineDescriptionEt = findViewById(R.id.medicineDescriptionEt);
        addBtn = findViewById(R.id.addBtn);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please Wait");
        mProgressDialog.setCanceledOnTouchOutside(false);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //flow
                //1. Input data
                //2. Validate data
                //3. Add data to database
                inputData();
            }
        });
    }

    private String medicineTitle, medicineDescription;
    private void inputData() {

        //1. Input Data
        medicineTitle = medicineTitleEt.getText().toString().trim();
        medicineDescription = medicineDescriptionEt.getText().toString().trim();

        //2. Validate data
        if (TextUtils.isEmpty(medicineTitle)){
            Toast.makeText(this, "Please enter the Post Title.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(medicineDescription)){
            Toast.makeText(this, "Please enter the Post Description.", Toast.LENGTH_SHORT).show();
            return;
        }
        addMedicine();
    }

    private void addMedicine() {

        //3. Add data to database
        mProgressDialog.setMessage("Adding Medicine. Please Wait.");
        mProgressDialog.show();

        final String timestamp = ""+System.currentTimeMillis();

        //setup data to upload
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("medicineId", "" + timestamp);
        hashMap.put("medicineTitle", "" + medicineTitle);
        hashMap.put("medicineDescription", "" + medicineDescription);
        hashMap.put("timestamp", "" + timestamp);
        hashMap.put("uid", "" + mFirebaseAuth.getUid());

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Medicine_List");
        ref.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //added to database
                        mProgressDialog.dismiss();
                        Toast.makeText(AddMedicineActivity.this, "New Medicine Added Successfully!", Toast.LENGTH_SHORT).show();
                        clearData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed added to database
                        mProgressDialog.dismiss();
                        Toast.makeText(AddMedicineActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearData() {
        //clear data after uploading product
        medicineTitleEt.setText("");
        medicineDescriptionEt.setText("");
    }
}
package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class AddPharmacistActivity extends AppCompatActivity {

    private Button addBtn;
    private EditText fullNameEt, ageEt, emailEt, passwordEt;

    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pharmacist);

        Objects.requireNonNull(getSupportActionBar()).hide();

        fullNameEt = findViewById(R.id.fullNameEt);
        ageEt = findViewById(R.id.ageEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        addBtn = findViewById(R.id.addBtn);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please Wait");
        mProgressDialog.setCanceledOnTouchOutside(false);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });
    }

    private String fullName, age, email, password;
    private void inputData(){
        //input data
        fullName = fullNameEt.getText().toString().trim();
        age = ageEt.getText().toString().trim();
        email = emailEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();

        //validate data
        // 1. Full Name Validation
        if (TextUtils.isEmpty(fullName)){
            Toast.makeText(this, "Empty Full Name. Please insert again!", Toast.LENGTH_SHORT).show();
            return;
        } else if (!fullName.matches("^[a-zA-Z]+$")) {
            Toast.makeText(this, "Username must contain alphabets only.", Toast.LENGTH_SHORT).show();
            return;
        }
        // 2. Phone Number Validation
        if (TextUtils.isEmpty(age)){
            Toast.makeText(this, "Empty Age. Please insert again!", Toast.LENGTH_SHORT).show();
            return;
        } else if (age.length() > 3) {
            Toast.makeText(this, "Age cannot exceed 3 digits.", Toast.LENGTH_SHORT).show();
            return;
        }
        // 4. Email Address Validation
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Empty Email Address. Please insert again!", Toast.LENGTH_SHORT).show();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid email pattern. Please Try Again!", Toast.LENGTH_SHORT).show();
            return;
        }
        // 5. Password Validation
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Empty Password. Please insert again!", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.length() < 8 || !containsUppercase(password)) {
            Toast.makeText(this, "Password must be at least 8 characters and contain at least one uppercase letter.", Toast.LENGTH_SHORT).show();
            return;
        }

        createAccount();
    }

    // Function to check for at least one uppercase letter in the password
    private boolean containsUppercase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private void createAccount(){
        mProgressDialog.setMessage("Creating Account. Please Wait.");
        mProgressDialog.show();

        //create account
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //account created
                        saverFirebaseData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed create account
                        mProgressDialog.dismiss();
                        Toast.makeText(AddPharmacistActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saverFirebaseData() {
        mProgressDialog.setMessage("Saving Account Info. Please Wait");

        String timestamp = "" + System.currentTimeMillis();

        //setup data to save
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", "" + mFirebaseAuth.getUid());
        hashMap.put("email", "" + email);
        hashMap.put("name", "" + fullName);
        hashMap.put("age", "" + age);
        hashMap.put("timestamp", "" + timestamp);
        hashMap.put("accountType", "Pharmacist");
        hashMap.put("online", "true");

        //save to database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.push().setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //added to database
                        mProgressDialog.dismiss();
                        Toast.makeText(AddPharmacistActivity.this, "Pharmacist Added Successfully!", Toast.LENGTH_SHORT).show();
                        clearData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed added to database
                        mProgressDialog.dismiss();
                        Toast.makeText(AddPharmacistActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearData() {
        //clear data after uploading product
        fullNameEt.setText("");
        ageEt.setText("");
        emailEt.setText("");
    }
}
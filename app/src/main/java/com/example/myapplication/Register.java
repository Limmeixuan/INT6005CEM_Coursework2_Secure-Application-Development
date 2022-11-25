package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private TextView registerTitle, registerBtn;
    private EditText editTextFullName, editTextAge, editTextEmailAddress, editTextPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Objects.requireNonNull(getSupportActionBar()).hide();

        mAuth = FirebaseAuth.getInstance();

        registerTitle = (TextView) findViewById(R.id.registerTitle);
        registerTitle.setOnClickListener(this);

        registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextAge = (EditText) findViewById(R.id.age);
        editTextEmailAddress = (EditText) findViewById(R.id.emailAddress);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.appName:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.registerBtn:
                registerBtn();
                break;
        }
    }
    private void registerBtn() {
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String emailAddress = editTextEmailAddress.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextFullName.setError("Full Name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if(age.isEmpty()){
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
            return;
        }

        if(emailAddress.isEmpty()){
            editTextEmailAddress.setError("Email Address is required!");
            editTextEmailAddress.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            editTextEmailAddress.setError("Please provide the valid email!");
            editTextEmailAddress.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Minimum Password length should be 6 characters! Please insert password again!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            User user = new User(fullName, age, emailAddress);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(Register.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(Register.this, "Failed to register! Please try again!", Toast.LENGTH_LONG).show();
                                            }
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                        }else{
                            Toast.makeText(Register.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                });
    }
}
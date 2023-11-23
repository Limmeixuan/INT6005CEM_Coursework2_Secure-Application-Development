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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {

    private TextView backTv;
    private EditText emailEt;
    private Button forgotBtn;

    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Objects.requireNonNull(getSupportActionBar()).hide();

        backTv = findViewById(R.id.backTv);
        emailEt = findViewById(R.id.emailEt);
        forgotBtn = findViewById(R.id.forgotBtn);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please Wait");
        mProgressDialog.setCanceledOnTouchOutside(false);

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });
    }

    private String email;
    private void forgotPassword(){
        email = emailEt.getText().toString().trim();

        //1. Forgot Password Email Validation
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Empty Email Address. Please insert again!", Toast.LENGTH_SHORT).show();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email Address. Please Insert Again!", Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressDialog.setMessage("Sending instructions to reset password.");
        mProgressDialog.show();

        mFirebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //instruction sent
                        mProgressDialog.dismiss();
                        Toast.makeText(ForgotPassword.this, "Password reset instructions sent to your email", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //instruction failed to send
                        mProgressDialog.dismiss();
                        Toast.makeText(ForgotPassword.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
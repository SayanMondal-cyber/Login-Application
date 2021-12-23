package com.example.loginapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.loginapplication.Models.Users;
import com.example.loginapplication.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
     FirebaseAuth auth;
     ActivitySignUpBinding binding;
     FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
//        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Your Account");
        progressDialog.setMessage("Please wait while we are creating your account");
//        if (auth.getCurrentUser() != null)
//        {
//            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//            startActivity(intent);
//        }
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                if (binding.nameBox.getText().toString().equals("") || binding.emailBox.getText().toString().equals("") || binding.passwordBox.getText().toString().equals("") || binding.confirmPasswordBox.getText().toString().equals("") || binding.ageBox.getText().toString().equals("")) {
                    progressDialog.dismiss();
//                      Toast.makeText(SignUpActivity.this, "Please enter valid details", Toast.LENGTH_SHORT).show();
                    binding.nameBox.setError("Username Field cannot be empty");
                    binding.emailBox.setError("Email Field cannot be empty");
                    binding.passwordBox.setError("Password Field cannot be empty");
                    binding.confirmPasswordBox.setError("Confirm Password Field cannot be empty");
                    binding.ageBox.setError("Age Field cannot be empty");

                } else if (binding.passwordBox.getText().toString().equals(binding.confirmPasswordBox.getText().toString()) && (Integer.parseInt(binding.ageBox.getText().toString())) >= 18) {
                    auth.createUserWithEmailAndPassword(binding.emailBox.getText().toString(), binding.passwordBox.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                Toast.makeText(SignUpActivity.this, "User created Successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignUpActivity.this, SignUpScreen.class);

                                intent.putExtra("name", binding.nameBox.getText().toString());
                                intent.putExtra("email", binding.emailBox.getText().toString());
                                intent.putExtra("password", binding.passwordBox.getText().toString());
                                intent.putExtra("age", binding.ageBox.getText().toString());

                                progressDialog.dismiss();
                                startActivity(intent);
                                Users user = new Users(binding.nameBox.getText().toString(), binding.emailBox.getText().toString(), binding.passwordBox.getText().toString(), binding.ageBox.getText().toString());
                                String email = binding.emailBox.getText().toString();
                                String formattedEmail = "";
                                for (int i = 0; i<email.length(); i++)
                                {
                                    char letter = email.charAt(i);
                                    if (letter!='_' && letter!='#' && letter!='[' && letter!=']' && letter!='.') {
                                        formattedEmail = formattedEmail + letter;
                                    }
                                }
                                database.getReference().child("Users").child(formattedEmail).setValue(user);
                                binding.nameBox.setText("");
                                binding.emailBox.setText("");
                                binding.passwordBox.setText("");
                                binding.confirmPasswordBox.setText("");
                                binding.ageBox.setText("");
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if (!binding.passwordBox.getText().toString().equals(binding.confirmPasswordBox.getText().toString())) {
                    progressDialog.dismiss();
//                      Toast.makeText(SignUpActivity.this, "Please enter correct password", Toast.LENGTH_SHORT).show();
                        binding.confirmPasswordBox.setError("Password should match");
                } else if ((Integer.parseInt(binding.ageBox.getText().toString())) < 18) {
//                    Toast.makeText(SignUpActivity.this, "Age cannot be less than 18 years", Toast.LENGTH_SHORT).show();
                      binding.ageBox.setError("Age cannot be less than 18");
                }
            }
        });
        binding.loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    int counter;

    @Override
    public void onBackPressed() {
        counter++;
        if (counter == 1) {
            Toast.makeText(SignUpActivity.this, "Press once again to exit", Toast.LENGTH_SHORT).show();
        }
        if (counter == 2) {
            super.onBackPressed();
            finishAffinity();
        }
    }
}
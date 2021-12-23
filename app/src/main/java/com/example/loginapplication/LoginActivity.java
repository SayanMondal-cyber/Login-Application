package com.example.loginapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.loginapplication.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    public static ActivityLoginBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    DatabaseReference reference;
    String formattedEmail;
    String userEnteredEmail;
    String userEnteredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(LoginActivity.this);
        auth = FirebaseAuth.getInstance();
        binding.regText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        dialog.setTitle("Account Login");
        dialog.setMessage("Please wait while logging in to your account");
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate Login Info
                if (validateEmail() && validatePassword()) {
                    isUser();
                } else {
                    binding.logEmail.setError("Email Field cannot be empty");
                    binding.logPassword.setError("Password Field cannot be empty");
                }
            }
        });

    }

    private Boolean validateEmail() {
        String email_val = binding.logEmail.getText().toString();
        if (email_val.isEmpty()) {
//            Toast.makeText(LoginActivity.this, "Email Field cannot be empty", Toast.LENGTH_SHORT).show();
            binding.logEmail.setError("Email Field cannot be empty");
            return false;
        } else {
            return true;
        }
    }

    private Boolean validatePassword() {
        String password_val = binding.logPassword.getText().toString();
        if (password_val.isEmpty()) {
//            Toast.makeText(LoginActivity.this, "Password Field cannot be empty", Toast.LENGTH_SHORT).show();
            binding.logPassword.setError("Email Field cannot be empty");
            return false;
        } else {
            return true;
        }
    }

    private void isUser() {
        userEnteredEmail = binding.logEmail.getText().toString();
        userEnteredPassword = binding.logPassword.getText().toString();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        formattedEmail = "";
        for (int i = 0; i < userEnteredEmail.length(); i++) {
            char letter = userEnteredEmail.charAt(i);
            if (letter != '_' && letter != '#' && letter != '[' && letter != ']' && letter != '.') {
                formattedEmail = formattedEmail + letter;
            }
        }
        checkUser();
    }

//    private void checkUser() {
//        Query checkUser;
//        String finalFormattedEmail = formattedEmail;
//        checkUser = reference.orderByChild("email").equalTo(finalFormattedEmail);
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists())
//                {
//                    String passwordFromDB = dataSnapshot.child(finalFormattedEmail).child("password").getValue(String.class);
//                    if (passwordFromDB.equals(userEnteredPassword))
//                    {
//                        String nameFromDB = dataSnapshot.child(finalFormattedEmail).child("username").getValue(String.class);
//                        String emailFromDB = dataSnapshot.child(finalFormattedEmail).child("email").getValue(String.class);
//                        String ageFromDB = dataSnapshot.child(finalFormattedEmail).child("age").getValue(String.class);
//                        Intent intent = new Intent(LoginActivity.this, LoginScreen.class);
//                        intent.putExtra("name", nameFromDB);
//                        intent.putExtra("email", emailFromDB);
//                        intent.putExtra("password", passwordFromDB);
//                        intent.putExtra("age", ageFromDB);
//                    }
//                    else{
////                        Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
//                        binding.logPassword.setError("Wrong Password");
//                    }
//                }
//                else{
////                    Toast.makeText(LoginActivity.this, "No such email address exists", Toast.LENGTH_SHORT).show();
//                    binding.logEmail.setError("No such email address exists");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
    private void checkUser(){
        String finalFormattedEmail = formattedEmail;
        String email = reference.
        auth.signInWithEmailAndPassword(finalFormattedEmail, userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                }
                else{

                }
            }
        });
    }

    int counter;

    @Override
    public void onBackPressed() {
        counter++;
        if (counter == 1) {
            Toast.makeText(LoginActivity.this, "Press once again to exit", Toast.LENGTH_SHORT).show();
        }
        if (counter == 2) {
            super.onBackPressed();
            finishAffinity();
        }
    }
}
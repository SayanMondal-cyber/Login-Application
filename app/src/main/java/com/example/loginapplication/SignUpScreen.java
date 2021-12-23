package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapplication.databinding.ActivitySignUpScreenBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpScreen extends AppCompatActivity {
    ActivitySignUpScreenBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String age = intent.getStringExtra("age").concat(" years");
        binding.tvName.setText(intent.getStringExtra("name"));
        binding.tvEmail.setText(intent.getStringExtra("email"));
        binding.tvPassword.setText(intent.getStringExtra("password"));
        binding.tvAge.setText(age);
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(SignUpScreen.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.loginapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapplication.databinding.ActivityLoginScreenBinding;

public class LoginScreen extends AppCompatActivity{
    ActivityLoginScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String age = intent.getStringExtra("age").concat(" years");
        binding.tvLogNm.setText(intent.getStringExtra("name"));
        binding.tvLogEm.setText(intent.getStringExtra("email"));
        binding.tvLogPs.setText(intent.getStringExtra("password"));
        binding.tvLogAge.setText(age);
        binding.btnLoginLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.binding.logEmail.setText("");
                LoginActivity.binding.logPassword.setText("");
                Intent intent = new Intent(LoginScreen.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
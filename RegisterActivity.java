package com.zybooks.cs360_project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        emailEditText = findViewById(R.id.username_reg); // reused as email
        passwordEditText = findViewById(R.id.password_reg);
        Button registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        userViewModel.register(email, password, (success, message) -> {
            if (success) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

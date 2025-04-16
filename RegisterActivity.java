package com.zybooks.cs360_project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText, phoneEditText;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        usernameEditText = findViewById(R.id.username_reg);
        passwordEditText = findViewById(R.id.password_reg);
        phoneEditText = findViewById(R.id.phone_number);
        Button registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = userViewModel.register(username, password, phone);
        if (success) {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        } else {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
        }
    }
}


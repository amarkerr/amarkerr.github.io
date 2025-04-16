package com.zybooks.cs360_project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        emailEditText = findViewById(R.id.username); // same field used
        passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);

        loginButton.setOnClickListener(v -> loginUser());
        registerButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        userViewModel.login(email, password, (success, message) -> {
            if (success) {
                getSharedPreferences("user_prefs", MODE_PRIVATE).edit()
                        .putString("username", email)
                        .apply();
                startActivity(new Intent(LoginActivity.this, EventsListActivity.class));
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

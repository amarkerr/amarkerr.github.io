package com.zybooks.cs360_project2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText phoneNumberEditText;
    private DatabaseHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        dbHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.username_reg);
        passwordEditText = findViewById(R.id.password_reg);
        phoneNumberEditText = findViewById(R.id.phone_number);

        Button registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> registerUser());
    }

    // Method to register
    private void registerUser() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS,
                new String[]{DatabaseHelper.COLUMN_USER_ID},
                DatabaseHelper.COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            cursor.close();
        } else {
            db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_USERS + " (" +
                            DatabaseHelper.COLUMN_USERNAME + ", " +
                            DatabaseHelper.COLUMN_PASSWORD + ", " +
                            DatabaseHelper.COLUMN_PHONE_NUMBER + ") VALUES (?, ?, ?)",
                    new Object[]{username, password, phoneNumber});
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
            assert cursor != null;
            cursor.close();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}

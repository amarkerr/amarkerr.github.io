package com.zybooks.cs360_project2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "settings_prefs";
    private static final String PREF_NIGHT_MODE = "night_mode";
    private static final String PREF_SMS_APPROVAL = "sms_approval";
    private static final int REQUEST_CODE_SMS_PERMISSION = 1;

    private SwitchMaterial dayNightSwitch;
    private SwitchMaterial smsSwitch;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        dayNightSwitch = findViewById(R.id.day_night_switch);

        // Load saved preferences
        boolean isNightMode = sharedPreferences.getBoolean(PREF_NIGHT_MODE, false);

        dayNightSwitch.setChecked(isNightMode);

        // Set the listeners for theme switch
        dayNightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PREF_NIGHT_MODE, isChecked);
            editor.apply();

            // Change the theme
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

    }
}
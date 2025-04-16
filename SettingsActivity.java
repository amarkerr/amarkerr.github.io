package com.zybooks.cs360_project2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "settings_prefs";
    private static final String PREF_NIGHT_MODE = "night_mode";

    private SwitchMaterial dayNightSwitch;

    private SharedPreferences sharedPreferences;
    private SettingsRepository settingsRepository;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        settingsRepository = new SettingsRepository();

        dayNightSwitch = findViewById(R.id.day_night_switch);

        username = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("username", "");

        loadSettingsFromFirestore();

        dayNightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean(PREF_NIGHT_MODE, isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
            saveSettingsToFirestore();
        });
    }

    private void loadSettingsFromFirestore() {
        settingsRepository.loadSettings(username, settings -> {
            boolean nightMode = (boolean) settings.getOrDefault(PREF_NIGHT_MODE, false);

            dayNightSwitch.setChecked(nightMode);

            sharedPreferences.edit()
                    .putBoolean(PREF_NIGHT_MODE, nightMode)
                    .apply();
        });
    }

    private void saveSettingsToFirestore() {
        Map<String, Object> settings = new HashMap<>();
        settings.put(PREF_NIGHT_MODE, dayNightSwitch.isChecked());

        settingsRepository.saveSettings(username, settings);
    }
}

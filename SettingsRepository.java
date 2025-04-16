package com.zybooks.cs360_project2;

import com.google.firebase.firestore.*;

import java.util.Map;

public class SettingsRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference settingsRef = db.collection("settings");

    public void saveSettings(String username, Map<String, Object> settings) {
        settingsRef.document(username).set(settings, SetOptions.merge());
    }

    public void loadSettings(String username, SettingsCallback callback) {
        settingsRef.document(username).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                callback.onSettingsLoaded(snapshot.getData());
            }
        });
    }

    public interface SettingsCallback {
        void onSettingsLoaded(Map<String, Object> settings);
    }
}

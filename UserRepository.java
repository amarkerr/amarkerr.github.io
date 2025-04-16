package com.zybooks.cs360_project2;

import com.google.firebase.auth.FirebaseAuth;

public class UserRepository {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public interface AuthCallback {
        void onComplete(boolean success, String message);
    }

    public void loginUser(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onComplete(true, "Login successful");
                    } else {
                        callback.onComplete(false, task.getException().getMessage());
                    }
                });
    }

    public void registerUser(String email, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onComplete(true, "Registration successful");
                    } else {
                        callback.onComplete(false, task.getException().getMessage());
                    }
                });
    }

    public String getCurrentUserEmail() {
        if (firebaseAuth.getCurrentUser() != null) {
            return firebaseAuth.getCurrentUser().getEmail();
        }
        return null;
    }

    public void logoutUser() {
        firebaseAuth.signOut();
    }
}

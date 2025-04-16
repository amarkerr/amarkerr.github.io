package com.zybooks.cs360_project2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository();
    }

    public void login(String email, String password, UserRepository.AuthCallback callback) {
        repository.loginUser(email, password, callback);
    }

    public void register(String email, String password, UserRepository.AuthCallback callback) {
        repository.registerUser(email, password, callback);
    }

    public String getCurrentUserEmail() {
        return repository.getCurrentUserEmail();
    }

    public void logout() {
        repository.logoutUser();
    }
}

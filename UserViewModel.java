package com.zybooks.cs360_project2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public boolean login(String username, String password) {
        return repository.loginUser(username, password);
    }

    public boolean register(String username, String password, String phone) {
        return repository.registerUser(username, password, phone);
    }
}


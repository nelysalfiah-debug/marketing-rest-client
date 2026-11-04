package com.nely.marketingapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.nely.marketingapp.auth.LoginResponse;
import com.nely.marketingapp.repository.AuthRepository;

import okhttp3.RequestBody;

public class AuthViewModel extends ViewModel {

    private final AuthRepository repository;
    private final MediatorLiveData<LoginResponse> loginResult;

    public AuthViewModel() {
        repository = new AuthRepository();
        loginResult = new MediatorLiveData<>();
    }

    public LiveData<LoginResponse> getLoginResult() {
        return loginResult;
    }

    public void login(RequestBody body) {
        LiveData<LoginResponse> source = repository.login(body);

        loginResult.addSource(source, response -> {
            loginResult.setValue(response);
            loginResult.removeSource(source);
        });
    }

    public LiveData<Boolean> logout(String token) {
        return repository.logout(token);
    }
}
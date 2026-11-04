package com.nely.marketingapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.nely.marketingapp.auth.LoginResponse;
import com.nely.marketingapp.network.RetrofitClient;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    // LOGIN
    public MutableLiveData<LoginResponse> login(RequestBody body) {

        MutableLiveData<LoginResponse> result = new MutableLiveData<>();

        RetrofitClient.getApiService()
                .login(body)
                .enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(
                            Call<LoginResponse> call,
                            Response<LoginResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {
                            result.postValue(response.body());
                        } else {
                            result.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<LoginResponse> call,
                            Throwable t) {

                        result.postValue(null);
                    }
                });

        return result;
    }

    // LOGOUT
    public MutableLiveData<Boolean> logout(String token) {

        MutableLiveData<Boolean> result = new MutableLiveData<>();

        RetrofitClient.getApiService()
                .logout("Bearer " + token)
                .enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(
                            Call<LoginResponse> call,
                            Response<LoginResponse> response) {

                        result.postValue(response.isSuccessful());
                    }

                    @Override
                    public void onFailure(
                            Call<LoginResponse> call,
                            Throwable t) {

                        result.postValue(false);
                    }
                });

        return result;
    }
}
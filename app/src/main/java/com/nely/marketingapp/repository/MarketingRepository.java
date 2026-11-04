package com.nely.marketingapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.nely.marketingapp.model.CampaignRequest;
import com.nely.marketingapp.model.MarketingCampaign;
import com.nely.marketingapp.network.ApiService;
import com.nely.marketingapp.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketingRepository {

    private final ApiService apiService;

    public MarketingRepository() {
        apiService = RetrofitClient.getApiService();
    }

    // GET: Mengambil daftar kampanye
    public MutableLiveData<List<MarketingCampaign>> getMarketingCampaigns() {

        MutableLiveData<List<MarketingCampaign>> liveData =
                new MutableLiveData<>();

        apiService.getMarketingCampaigns().enqueue(
                new Callback<ApiService.CampaignResponse>() {

                    @Override
                    public void onResponse(
                            Call<ApiService.CampaignResponse> call,
                            Response<ApiService.CampaignResponse> response
                    ) {
                        if (response.isSuccessful()
                                && response.body() != null) {

                            liveData.postValue(response.body().getData());

                        } else {
                            Log.e(
                                    "CAMPAIGN_GET_ERROR",
                                    "HTTP: " + response.code()
                            );

                            liveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiService.CampaignResponse> call,
                            Throwable t
                    ) {
                        Log.e(
                                "CAMPAIGN_GET_ERROR",
                                "Gagal mengambil campaign",
                                t
                        );

                        liveData.postValue(null);
                    }
                }
        );

        return liveData;
    }

    // GET: Memuat ulang daftar kampanye
    public void refreshMarketingCampaigns(
            MutableLiveData<List<MarketingCampaign>> liveData
    ) {

        apiService.getMarketingCampaigns().enqueue(
                new Callback<ApiService.CampaignResponse>() {

                    @Override
                    public void onResponse(
                            Call<ApiService.CampaignResponse> call,
                            Response<ApiService.CampaignResponse> response
                    ) {
                        if (response.isSuccessful()
                                && response.body() != null) {

                            liveData.postValue(response.body().getData());

                        } else {
                            Log.e(
                                    "CAMPAIGN_GET_ERROR",
                                    "HTTP: " + response.code()
                            );

                            liveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiService.CampaignResponse> call,
                            Throwable t
                    ) {
                        Log.e(
                                "CAMPAIGN_GET_ERROR",
                                "Gagal mengambil campaign",
                                t
                        );

                        liveData.postValue(null);
                    }
                }
        );
    }

    // POST: Menambahkan kampanye
    public MutableLiveData<ApiService.CreateCampaignResponse>
    createMarketingCampaign(String token, CampaignRequest campaign) {

        MutableLiveData<ApiService.CreateCampaignResponse> liveData =
                new MutableLiveData<>();

        apiService.createMarketingCampaign(
                "Bearer " + token,
                campaign
        ).enqueue(new Callback<ApiService.CreateCampaignResponse>() {

            @Override
            public void onResponse(
                    Call<ApiService.CreateCampaignResponse> call,
                    Response<ApiService.CreateCampaignResponse> response
            ) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.d("CAMPAIGN_POST", "POST berhasil");
                    liveData.postValue(response.body());

                } else {

                    String error = "HTTP: " + response.code();

                    try {
                        if (response.errorBody() != null) {
                            error += " | " + response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e(
                                "CAMPAIGN_POST_ERROR",
                                "Gagal membaca error body",
                                e
                        );
                    }

                    Log.e("CAMPAIGN_POST_ERROR", error);
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(
                    Call<ApiService.CreateCampaignResponse> call,
                    Throwable t
            ) {
                Log.e(
                        "CAMPAIGN_POST_ERROR",
                        "Koneksi POST gagal: " + t.getMessage(),
                        t
                );

                liveData.postValue(null);
            }
        });

        return liveData;
    }
}

package com.nely.marketingapp.network;

import com.nely.marketingapp.model.CampaignRequest;
import com.nely.marketingapp.model.MarketingCampaign;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import com.nely.marketingapp.auth.LoginResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @GET("api/marketing-campaigns")
    Call<CampaignResponse> getMarketingCampaigns();

    @POST("api/marketing-campaigns")
    Call<CreateCampaignResponse> createMarketingCampaign(
            @Header("Authorization") String token,
            @Body CampaignRequest campaign
    );

    @POST("api/login")
    Call<LoginResponse> login(@Body RequestBody body);

    @POST("api/logout")
    Call<LoginResponse> logout(@Header("Authorization") String token);

    class CampaignResponse {

        private boolean success;
        private String message;
        private List<MarketingCampaign> data;

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public List<MarketingCampaign> getData() {
            return data;
        }
    }
    class CreateCampaignResponse {

        private boolean success;
        private String message;
        private MarketingCampaign data;

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public MarketingCampaign getData() {
            return data;
        }
    }
}

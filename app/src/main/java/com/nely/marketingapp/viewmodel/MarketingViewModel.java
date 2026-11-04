package com.nely.marketingapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nely.marketingapp.model.CampaignRequest;
import com.nely.marketingapp.model.MarketingCampaign;
import com.nely.marketingapp.network.ApiService;
import com.nely.marketingapp.repository.MarketingRepository;

import java.util.List;

public class MarketingViewModel extends ViewModel {

    private final MarketingRepository repository;
    private final MutableLiveData<List<MarketingCampaign>> campaigns;

    public MarketingViewModel() {
        repository = new MarketingRepository();
        campaigns = new MutableLiveData<>();

        refreshCampaigns();
    }

    // GET: Mengambil daftar campaign
    public LiveData<List<MarketingCampaign>> getCampaigns() {
        return campaigns;
    }

    // GET: Memuat ulang daftar campaign
    public void refreshCampaigns() {
        repository.refreshMarketingCampaigns(campaigns);
    }

    // POST: Menambahkan campaign
    public LiveData<ApiService.CreateCampaignResponse> createMarketingCampaign(
            String token,
            CampaignRequest campaign
    ) {
        return repository.createMarketingCampaign(token, campaign);
    }
}

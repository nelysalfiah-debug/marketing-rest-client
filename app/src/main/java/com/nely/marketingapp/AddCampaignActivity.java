package com.nely.marketingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nely.marketingapp.model.CampaignRequest;
import com.nely.marketingapp.network.ApiService;
import com.nely.marketingapp.viewmodel.MarketingViewModel;

public class AddCampaignActivity extends AppCompatActivity {

    private EditText etName, etDescription, etStartDate;
    private EditText etEndDate, etBudget, etStatus;
    private Button btnSave, btnCancel;

    private MarketingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_campaign);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etBudget = findViewById(R.id.etBudget);
        etStatus = findViewById(R.id.etStatus);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        viewModel = new ViewModelProvider(this)
                .get(MarketingViewModel.class);

        btnCancel.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> saveCampaign());
    }

    private void saveCampaign() {

        String name = etName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String startDate = etStartDate.getText().toString().trim();
        String endDate = etEndDate.getText().toString().trim();
        String budgetText = etBudget.getText().toString().trim();
        String status = etStatus.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()
                || startDate.isEmpty() || endDate.isEmpty()
                || budgetText.isEmpty() || status.isEmpty()) {

            Toast.makeText(this,
                    "Semua kolom harus diisi.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double budget;

        try {
            budget = Double.parseDouble(budgetText);

            if (budget < 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            etBudget.setError("Masukkan budget yang valid.");
            return;
        }

        SharedPreferences preferences = getSharedPreferences(
                "MarketingAppPrefs",
                MODE_PRIVATE
        );

        String token = preferences.getString("auth_token", null);

        if (token == null || token.isEmpty()) {
            Toast.makeText(this,
                    "Sesi login tidak ditemukan. Silakan login kembali.",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        CampaignRequest campaign = new CampaignRequest(
                name,
                description,
                startDate,
                endDate,
                budget,
                status
        );

        btnSave.setEnabled(false);

        viewModel.createMarketingCampaign(token, campaign)
                .observe(this, response -> {

                    btnSave.setEnabled(true);

                    if (response != null && response.isSuccess()) {

                        Toast.makeText(this,
                                "Kampanye berhasil ditambahkan.",
                                Toast.LENGTH_SHORT).show();

                        setResult(RESULT_OK);
                        finish();

                    } else if (response != null) {

                        String message = response.getMessage();

                        if (message == null || message.isEmpty()) {
                            message = "Server menolak data kampanye.";
                        }

                        Toast.makeText(this,
                                "Gagal: " + message,
                                Toast.LENGTH_LONG).show();

                        Log.e("CAMPAIGN_POST_ERROR", message);

                    } else {

                        Toast.makeText(this,
                                "Gagal menghubungi server. Periksa koneksi.",
                                Toast.LENGTH_LONG).show();

                        Log.e("CAMPAIGN_POST_ERROR",
                                "Respons dari Repository kosong.");
                    }
                });
    }
}

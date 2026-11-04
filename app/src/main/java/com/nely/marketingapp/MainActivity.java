package com.nely.marketingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nely.marketingapp.adapter.CampaignAdapter;
import com.nely.marketingapp.model.MarketingCampaign;
import com.nely.marketingapp.viewmodel.AuthViewModel;
import com.nely.marketingapp.viewmodel.MarketingViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewCampaigns;
    private ProgressBar progressBar;
    private TextView tvMessage;
    private Button btnLogout;
    private Button btnAddCampaign;

    private MarketingViewModel viewModel;
    private AuthViewModel authViewModel;
    private CampaignAdapter adapter;

    private ActivityResultLauncher<Intent> addCampaignLauncher;

    private final List<MarketingCampaign> campaignsData =
            new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen
        listViewCampaigns = findViewById(R.id.listViewCampaigns);
        progressBar = findViewById(R.id.progressBar);
        tvMessage = findViewById(R.id.tvMessage);
        btnLogout = findViewById(R.id.btnLogout);
        btnAddCampaign = findViewById(R.id.btnAddCampaign);

        // Inisialisasi ViewModel
        viewModel = new ViewModelProvider(this)
                .get(MarketingViewModel.class);

        authViewModel = new ViewModelProvider(this)
                .get(AuthViewModel.class);

        // Menerima hasil dari halaman tambah campaign
        addCampaignLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        progressBar.setVisibility(View.VISIBLE);
                        tvMessage.setVisibility(View.GONE);

                        viewModel.refreshCampaigns();
                    }
                }
        );

        // Tombol tambah campaign
        btnAddCampaign.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddCampaignActivity.class
            );

            addCampaignLauncher.launch(intent);
        });

        // Tombol logout
        btnLogout.setOnClickListener(v -> logout());

        // Adapter daftar campaign
        adapter = new CampaignAdapter(this, campaignsData);
        listViewCampaigns.setAdapter(adapter);

        // Klik campaign untuk membuka halaman detail
        listViewCampaigns.setOnItemClickListener(
                (parent, view, position, id) -> {

                    if (position < 0 ||
                            position >= campaignsData.size()) {
                        return;
                    }

                    MarketingCampaign campaign =
                            campaignsData.get(position);

                    Intent intent = new Intent(
                            MainActivity.this,
                            DetailCampaignActivity.class
                    );

                    intent.putExtra("name", campaign.getName());
                    intent.putExtra(
                            "description",
                            campaign.getDescription()
                    );
                    intent.putExtra(
                            "startDate",
                            campaign.getStartDate()
                    );
                    intent.putExtra(
                            "endDate",
                            campaign.getEndDate()
                    );
                    intent.putExtra(
                            "budget",
                            campaign.getBudget()
                    );
                    intent.putExtra(
                            "status",
                            campaign.getStatus()
                    );

                    startActivity(intent);
                }
        );

        // Menampilkan data campaign dari ViewModel
        progressBar.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.GONE);

        viewModel.getCampaigns().observe(this, campaigns -> {

            progressBar.setVisibility(View.GONE);
            campaignsData.clear();

            if (campaigns != null && !campaigns.isEmpty()) {

                campaignsData.addAll(campaigns);
                tvMessage.setVisibility(View.GONE);

            } else {

                tvMessage.setText(
                        "Data campaign tidak ditemukan atau gagal mengambil data dari server."
                );

                tvMessage.setVisibility(View.VISIBLE);
            }

            adapter.notifyDataSetChanged();
        });
    }

    // Proses logout melalui AuthViewModel
    private void logout() {

        SharedPreferences preferences = getSharedPreferences(
                "MarketingAppPrefs",
                MODE_PRIVATE
        );

        String token = preferences.getString("auth_token", null);

        // Jika token tidak ditemukan, hapus sesi lokal
        if (token == null || token.isEmpty()) {
            clearSession(preferences);
            return;
        }

        btnLogout.setEnabled(false);

        authViewModel.logout(token).observe(this, success -> {

            btnLogout.setEnabled(true);

            if (success != null && success) {

                Toast.makeText(
                        MainActivity.this,
                        "Logout berhasil.",
                        Toast.LENGTH_SHORT
                ).show();

                clearSession(preferences);

            } else {

                Toast.makeText(
                        MainActivity.this,
                        "Logout gagal. Periksa koneksi server.",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    // Menghapus sesi lokal dan kembali ke halaman login
    private void clearSession(SharedPreferences preferences) {

        preferences.edit().clear().apply();

        Intent intent = new Intent(
                MainActivity.this,
                LoginActivity.class
        );

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);
        finish();
    }
}

package com.nely.marketingapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailCampaignActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvDescription;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView tvBudget;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_campaign);

        tvName = findViewById(R.id.tvDetailName);
        tvDescription = findViewById(R.id.tvDetailDescription);
        tvStartDate = findViewById(R.id.tvDetailStartDate);
        tvEndDate = findViewById(R.id.tvDetailEndDate);
        tvBudget = findViewById(R.id.tvDetailBudget);
        tvStatus = findViewById(R.id.tvDetailStatus);

        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String startDate = getIntent().getStringExtra("startDate");
        String endDate = getIntent().getStringExtra("endDate");
        double budget = getIntent().getDoubleExtra("budget", 0);
        String status = getIntent().getStringExtra("status");

        NumberFormat formatter = NumberFormat.getNumberInstance(
                new Locale("id", "ID")
        );

        tvName.setText(name != null ? name : "-");
        tvDescription.setText(
                description != null && !description.isEmpty()
                        ? description : "-"
        );
        tvStartDate.setText(startDate != null ? startDate : "-");
        tvEndDate.setText(endDate != null ? endDate : "-");
        tvBudget.setText("Rp" + formatter.format(budget));
        tvStatus.setText(status != null ? status : "-");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail Campaign");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

package com.nely.marketingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nely.marketingapp.R;
import com.nely.marketingapp.model.MarketingCampaign;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CampaignAdapter extends ArrayAdapter<MarketingCampaign> {

    public CampaignAdapter(Context context, List<MarketingCampaign> campaigns) {
        super(context, 0, campaigns);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_campaign, parent, false);
        }

        MarketingCampaign campaign = getItem(position);

        TextView tvName = convertView.findViewById(R.id.tvCampaignName);
        TextView tvBudget = convertView.findViewById(R.id.tvCampaignBudget);
        TextView tvStatus = convertView.findViewById(R.id.tvCampaignStatus);

        if (campaign != null) {
            tvName.setText(campaign.getName());

            NumberFormat format =
                    NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            tvBudget.setText("Anggaran: " + format.format(campaign.getBudget()));

            tvStatus.setText("Status: " + campaign.getStatus());
        }

        return convertView;
    }
}

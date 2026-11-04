package com.nely.marketingapp.model;

import com.google.gson.annotations.SerializedName;

public class MarketingCampaign {

    private int id;
    private String name;
    private String description;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    private double budget;
    private String status;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getBudget() {
        return budget;
    }

    public String getStatus() {
        return status;
    }
}
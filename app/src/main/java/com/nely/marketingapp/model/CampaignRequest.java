package com.nely.marketingapp.model;

public class CampaignRequest {

    private String name;
    private String description;
    private String start_date;
    private String end_date;
    private double budget;
    private String status;

    public CampaignRequest(
            String name,
            String description,
            String start_date,
            String end_date,
            double budget,
            String status
    ) {
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.budget = budget;
        this.status = status;
    }
}
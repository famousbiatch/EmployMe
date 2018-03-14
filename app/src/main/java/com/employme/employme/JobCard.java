package com.employme.employme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class JobCard {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private  String businessName;
    private  String logo;
    private  String jobCategory;

    public JobCard() {
        businessName = "";
        logo = "";
        jobCategory = "";
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public JobCard(String businessName, String logo, String jobCategory) {
        this.businessName = businessName;
        this.logo = logo;
        this.jobCategory = jobCategory;
    }
}
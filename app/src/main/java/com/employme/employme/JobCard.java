package com.employme.employme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class JobCard {

    private  String businessName;
    private  String logo;
    private  String jobDescription;

    public JobCard() {
        businessName = "";
        logo = "";
        jobDescription = "";
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

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public JobCard(String businessName, String logo, String jobDescription) {
        this.businessName = businessName;
        this.logo = logo;
        this.jobDescription = jobDescription;
    }
}
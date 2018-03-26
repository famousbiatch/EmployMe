package com.employme.employme;

import android.widget.ImageView;
import android.widget.TextView;

public class AppCard {

    private int id;
    private String businessName;
    private int applicantId;
    private  String applicantName;
    private int applicantAge;
    private  String applicantPicture;
    private  String applicantPhoneNumber;
    private String applicantEmail;
    private String applicantCity;

    public AppCard(int applicantAge, String applicantName, String applicantPicture, String applicantPhoneNumber, String applicantEmail, String applicantCity) {
        this.applicantAge = applicantAge;
        this.applicantName = applicantName;
        this.applicantPicture = applicantPicture;
        this.applicantPhoneNumber = applicantPhoneNumber;
        this.applicantEmail = applicantEmail;
        this.applicantCity = applicantCity;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getApplicantAge() {
        return applicantAge;
    }

    public void setApplicantAge(int applicantAge) {
        this.applicantAge = applicantAge;
    }

    public String getApplicantCity() {
        return applicantCity;
    }

    public void setApplicantCity(String applicantCity) {
        this.applicantCity = applicantCity;
    }

    public AppCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantPicture() {
        return applicantPicture;
    }

    public void setApplicantPicture(String applicantPicture) {
        this.applicantPicture = applicantPicture;
    }

    public String getApplicantPhoneNumber() {
        return applicantPhoneNumber;
    }

    public void setApplicantPhoneNumber(String applicantPhoneNumber) {
        this.applicantPhoneNumber = applicantPhoneNumber;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }
}
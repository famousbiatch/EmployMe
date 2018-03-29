package com.employme.employme;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private StorageReference mStorage;

    private ImageView ivProfilePicture;
    private TextView tvFullName;
    private TextView tvEmail;
    private TextView tvPassword;
    private TextView tvPhoneNumber;
    private TextView tvCity;
    private TextView tvAge;
    private TextView tvEducation;
    private TextView tvDriverLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mStorage = FirebaseStorage.getInstance().getReference();
        ivProfilePicture = (ImageView) findViewById(R.id.ivProfilePicture);
        tvFullName = (TextView) findViewById(R.id.tvFullName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvAge =  (TextView) findViewById(R.id.tvAge);
        tvEducation = (TextView) findViewById(R.id.tvEducation);
        tvDriverLicense = (TextView) findViewById(R.id.tvDriverLicense);

        User loggedInUser = SQLiteDB.getInstance().getUser(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()));

        try {

            mStorage.child("ProfilePictures/" + SQLiteDB.getInstance().getSessionUser()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(ProfileActivity.this).load(uri).fit().placeholder(R.drawable.ic_no_profile_pic).error(R.drawable.ic_no_profile_pic).into(ivProfilePicture);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });

        } catch (Exception x) {}

        tvFullName.setText(loggedInUser.getName());
        tvEmail.setText(loggedInUser.getEmail());
        int starLength = loggedInUser.getPassword().length();
        String starredPass = "*";
        for (int i = 1; i < starLength; i++)
            starredPass += "*";
        tvPassword.setText(starredPass);
        tvPhoneNumber.setText(loggedInUser.getPhoneNumber());
        tvCity.setText(loggedInUser.getCity());
        tvAge.setText(String.valueOf(loggedInUser.getAge()));
        tvEducation.setText(loggedInUser.getEducation());
        tvDriverLicense.setText((loggedInUser.getDriverLicense() == 1) ? "Driver's License Available" : "No Driver's License");
    }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getStringExtra("intentName").equals("JobListActivity"))
                startActivity(new Intent(this, JobListActivity.class));
            else if (getIntent().getStringExtra("intentName").equals("EmployerDashboard"))
                startActivity(new Intent(this, EmployerDashboardActivity.class));
            else if (getIntent().getStringExtra("intentName").equals("Favorites"))
                startActivity(new Intent(this, FavoritesActivity.class));
        } catch (Exception x) {}
        super.onBackPressed();
    }
}

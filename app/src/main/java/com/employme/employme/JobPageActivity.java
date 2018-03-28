package com.employme.employme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class JobPageActivity extends AppCompatActivity {

    private boolean fav;
    private int job_id;
    private ImageView ivFavorite;
    private ImageView ivLogoFull;
    private TextView tvBusinessNameFull;
    private TextView tvJobCategoryFull;
    private TextView tvJobDescription;
    private TextView tvAgeRange;
    private TextView tvPhoneNumber;
    private TextView tvBusinessLocation;
    private Button btnApplyForJob;
    private Button btnDeleteJob;
    private int numberOfApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbarJob);
        setSupportActionBar(toolbar);

        ivFavorite = (ImageView) findViewById(R.id.ivFavorite);
        ivLogoFull = (ImageView) findViewById(R.id.imgLogoFull);
        tvBusinessNameFull = (TextView) findViewById(R.id.tvBusinessNameFull);
        tvJobCategoryFull = (TextView) findViewById(R.id.tvJobCategoryFull);
        tvJobDescription = (TextView) findViewById(R.id.tvJobDescription);
        tvAgeRange = (TextView) findViewById(R.id.tvAgeRange);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvBusinessLocation = (TextView) findViewById(R.id.tvBusinessLocation);
        btnApplyForJob = (Button) findViewById(R.id.btnApplyForJob);
        btnDeleteJob = (Button) findViewById(R.id.btnDeleteJob);

        this.job_id = getIntent().getIntExtra("job_id", 0);
        List<String> info = SQLiteDB.getInstance().getJobListing(job_id);
        this.numberOfApps = SQLiteDB.getInstance().getNumberOfApplications(job_id);

        if (!info.get(1).equals(SQLiteDB.getInstance().getSessionUser()))
            btnDeleteJob.setVisibility(View.INVISIBLE);
        else {
            btnApplyForJob.setText("VIEW APPLICATIONS (" + numberOfApps + ")");
        }

        fav = false;
        if (SQLiteDB.getInstance().isFavorite(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), job_id)) {
            ivFavorite.setImageResource(R.drawable.ic_golden_star);
            fav = true;
        }
        //ivLogoFull.setImageResource();
        tvBusinessNameFull.setText(info.get(2));
        tvJobCategoryFull.setText(info.get(7));
        tvJobDescription.setText(info.get(4));
        tvAgeRange.setText(info.get(8) + "-" + info.get(9));
        tvPhoneNumber.setText(info.get(5));
        tvBusinessLocation.setText(info.get(6));

        if (SQLiteDB.getInstance().applicationExists(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), job_id)) {
            btnApplyForJob.setText("CANCEL APPLICATION");
            btnApplyForJob.setBackgroundColor(getResources().getColor(R.color.customRed));
        }

        try {

            FirebaseStorage.getInstance().getReference().child("BusinessLogos/" + tvBusinessNameFull.getText().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(JobPageActivity.this).load(uri).fit().into(ivLogoFull);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });

        } catch (Exception x) {}
    }


    public void applyOrViewApps(View view) {
        if (btnApplyForJob.getText().toString().startsWith("VIEW APPLICATIONS")) {
            Intent i = new Intent(this, ApplicationListActivity.class);
            i.putExtra("job_id", this.job_id);
            i.putExtra("intentName", getIntent().getStringExtra("intentName"));
            startActivity(i);
            return;
        } else if (btnApplyForJob.getText().toString().startsWith("CANCEL APPLICATION")) {
            SQLiteDB.getInstance().deleteApp(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), this.job_id);
            Toast.makeText(this, "Application canceled", Toast.LENGTH_SHORT).show();
            btnApplyForJob.setText("APPLY FOR POSITION");
            btnApplyForJob.setBackgroundColor(getResources().getColor(R.color.actionBar));
        } else if (btnApplyForJob.getText().toString().startsWith("APPLY")) {
            SQLiteDB.getInstance().createApplication(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), this.job_id);
            Toast.makeText(this, "Application submitted", Toast.LENGTH_SHORT).show();
            btnApplyForJob.setText("CANCEL APPLICATION");
            btnApplyForJob.setBackgroundColor(getResources().getColor(R.color.customRed));
        }
    }

    public void favoriteClick(View view) {
        if (!fav) {
            SQLiteDB.getInstance().addFavorite(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), job_id);
            ivFavorite.setImageResource(R.drawable.ic_golden_star);
            fav = true;
        } else {
            SQLiteDB.getInstance().deleteFavorite(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), job_id);
            ivFavorite.setImageResource(R.drawable.ic_star_empty);
            fav = false;
        }
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

    public void deleteJob(View view) {
        SQLiteDB.getInstance().deleteJob(job_id);
        SQLiteDB.getInstance().deleteAllApps(this.job_id);
        try {
            if (getIntent().getStringExtra("intentName").equals("JobListActivity"))
                startActivity(new Intent(this, JobListActivity.class));
            else if (getIntent().getStringExtra("intentName").equals("EmployerDashboard"))
                startActivity(new Intent(this, EmployerDashboardActivity.class));
            else if (getIntent().getStringExtra("intentName").equals("Favorites"))
                startActivity(new Intent(this, FavoritesActivity.class));
        } catch (Exception x) {}
    }
}

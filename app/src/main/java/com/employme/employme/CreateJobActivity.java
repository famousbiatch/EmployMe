package com.employme.employme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateJobActivity extends AppCompatActivity {

    private EditText etBusinessName;
    private EditText etJobDescription;
    private EditText etBusinessNumber;
    private EditText etBusinessLocation;
    private Spinner spJobCategory;
    private EditText etMinAge;
    private EditText etMaxAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        initialize();
    }

    public void registerJob(View view) {

        if (etBusinessName.getText().toString().length() < 2 ||
                etJobDescription.getText().toString().length() < 2 || (
                etBusinessNumber.getText().toString().length() != 7 &&
                etBusinessNumber.getText().toString().length() != 9 &&
                etBusinessNumber.getText().toString().length() != 10) || (
                (!etBusinessNumber.getText().toString().startsWith("05")) &&
                (!etBusinessNumber.getText().toString().startsWith("04")) &&
                (!etBusinessNumber.getText().toString().startsWith("6"))) ||
                etBusinessLocation.getText().toString().length() < 2)
        {
            Toast.makeText(this, "Check your input", Toast.LENGTH_LONG).show();
            return;
        }

        SQLiteDB.getInstance().addJobListing(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), etBusinessName.getText().toString(),
                "LOGO URL HERE", etJobDescription.getText().toString(), etBusinessNumber.getText().toString(), etBusinessLocation.getText().toString(),
                spJobCategory.getSelectedItem().toString(), etMinAge.getText().toString(), etMaxAge.getText().toString());
        Toast.makeText(this, "Successfully Listed Job", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, EmployerDashboardActivity.class);
        startActivity(i);
    }

    private void initialize() {
        this.etBusinessName = (EditText) findViewById(R.id.etBusinessName);
        this.etJobDescription = (EditText) findViewById(R.id.etJobDescription);
        this.etBusinessNumber = (EditText) findViewById(R.id.etBusinessNumber);
        this.etBusinessLocation = (EditText) findViewById(R.id.etBusinessLocation);
        this.spJobCategory = (Spinner) findViewById(R.id.spJobCategory);
        this.etMinAge = (EditText) findViewById(R.id.etMinAge);
        this.etMaxAge = (EditText) findViewById(R.id.etMaxAge);
    }

    public void clearFocus(View view) { view.requestFocus(); }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getStringExtra("intentName").equals("JobList"))
                startActivity(new Intent(this, JobList.class));
            else if (getIntent().getStringExtra("intentName").equals("EmployerDashboard"))
                startActivity(new Intent(this, EmployerDashboardActivity.class));
            else if (getIntent().getStringExtra("intentName").equals("Favorites"))
                startActivity(new Intent(this, FavoritesActivity.class));
        } catch (Exception x) {}

        super.onBackPressed();
    }
}

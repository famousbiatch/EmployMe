package com.employme.employme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class EmployerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbarSecond);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_second, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.favoritesSECOND:
            {
                Intent i = new Intent(this, FavoritesActivity.class);
                startActivity(i);
                break;
            }
            case R.id.jobList:
            {
                Intent i = new Intent(this, JobList.class);
                startActivity(i);
                break;
            }
            case R.id.profileSECOND:
            {
                Intent i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                break;
            }
            case R.id.settingsSECOND:
            {
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;
            }
            case R.id.createJobSECOND:
            {
                Intent i = new Intent(this, CreateJobActivity.class);
                startActivity(i);
                break;
            }
            case R.id.aboutSECOND:
            {
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                break;
            }
            case R.id.signOutSECOND:
            {
                SQLiteDB.getInstance().updateSession("");
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

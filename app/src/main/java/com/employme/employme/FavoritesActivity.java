package com.employme.employme;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    ListView list;
    CustomAdapterFavorites adapter;
    public FavoritesActivity CustomListView = null;
    public ArrayList<JobCard> CustomListViewValuesArr = new ArrayList<JobCard>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionBarThird);
        setSupportActionBar(toolbar);

        CustomListView = this;

        setListData();

        Resources res = getResources();
        list = (ListView) findViewById(R.id.lstFavJobs);

        adapter = new CustomAdapterFavorites(CustomListView, CustomListViewValuesArr, res);
        list.setAdapter(adapter);
    }

    public void setListData() {
        for (int jobID : SQLiteDB.getInstance().getAllJobs())
        {
            final JobCard entry = new JobCard();
            List<String> info = SQLiteDB.getInstance().getJobListing(jobID);

            if (!SQLiteDB.getInstance().isFavorite(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), Integer.valueOf(info.get(0))))
                continue;

            entry.setId(Integer.valueOf(info.get(0)));
            entry.setLogo(info.get(3));
            entry.setBusinessName(info.get(2));
            entry.setJobCategory(info.get(7));

            CustomListViewValuesArr.add(entry);
        }
        //another loop here to add ALL other jobs that don't match city
    }

    public void onItemClick(int mPosition) {
        JobCard tempValues = (JobCard) CustomListViewValuesArr.get(mPosition);

        Intent i = new Intent(this, JobPageActivity.class);
        i.putExtra("job_id", tempValues.getId());
        i.putExtra("intentName", "Favorites");
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_third, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.refreshThird:
            {
                setListData();
                Toast.makeText(this, "Refresh successful", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.jobListSecond:
            {
                Intent i = new Intent(this, JobList.class);
                startActivity(i);
                break;
            }
            case R.id.employerDashboardSecond:
            {
                Intent i = new Intent(this, EmployerDashboardActivity.class);
                startActivity(i);
                break;
            }
            case R.id.profileTHIRD:
            {
                Intent i = new Intent(this, ProfileActivity.class);
                i.putExtra("intentName", "Favorites");
                startActivity(i);
                break;
            }
            case R.id.settingsTHIRD:
            {
                Intent i = new Intent(this, SettingsActivity.class);
                i.putExtra("intentName", "Favorites");
                startActivity(i);
                break;
            }
            case R.id.createJobTHIRD:
            {
                Intent i = new Intent(this, CreateJobActivity.class);
                i.putExtra("intentName", "Favorites");
                startActivity(i);
                break;
            }
            case R.id.aboutTHIRD:
            {
                Intent i = new Intent(this, AboutActivity.class);
                i.putExtra("intentName", "Favorites");
                startActivity(i);
                break;
            }
            case R.id.signOutTHIRD:
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

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

public class EmployerDashboardActivity extends AppCompatActivity {

    ListView list;
    CustomAdapterEmployer adapter;
    public EmployerDashboardActivity CustomListView = null;
    public ArrayList<JobCard> CustomListViewValuesArr = new ArrayList<JobCard>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbarSecond);
        setSupportActionBar(toolbar);

        CustomListView = this;

        setListData();

        Resources res = getResources();
        list = (ListView) findViewById(R.id.lstMyJobs);

        adapter = new CustomAdapterEmployer(CustomListView, CustomListViewValuesArr, res);
        list.setAdapter(adapter);
    }

    public void setListData() {
        for (int jobID : SQLiteDB.getInstance().getAllJobs())
        {
            final JobCard entry = new JobCard();
            List<String> info = SQLiteDB.getInstance().getJobListing(jobID);

            if (!info.get(1).equals(SQLiteDB.getInstance().getSessionUser()))
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
        i.putExtra("intentName", "EmployerDashboard");
        startActivity(i);
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
            case R.id.refreshSecond:
            {
                setListData();
                Toast.makeText(this, "Refresh successful", Toast.LENGTH_SHORT).show();
                break;
            }
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
                i.putExtra("intentName", "EmployerDashboard");
                startActivity(i);
                break;
            }
            case R.id.settingsSECOND:
            {
                Intent i = new Intent(this, SettingsActivity.class);
                i.putExtra("intentName", "EmployerDashboard");
                startActivity(i);
                break;
            }
            case R.id.createJobSECOND:
            {
                Intent i = new Intent(this, CreateJobActivity.class);
                i.putExtra("intentName", "EmployerDashboard");
                startActivity(i);
                break;
            }
            case R.id.aboutSECOND:
            {
                Intent i = new Intent(this, AboutActivity.class);
                i.putExtra("intentName", "EmployerDashboard");
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

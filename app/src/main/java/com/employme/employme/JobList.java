package com.employme.employme;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class JobList extends AppCompatActivity {

    ListView list;
    CustomAdapter adapter;
    public JobList CustomListView = null;
    public ArrayList<JobCard> CustomListViewValuesArr = new ArrayList<JobCard>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        CustomListView = this;

        setListData();

        Resources res = getResources();
        list = (ListView) findViewById(R.id.lstJobs);

        adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.employerDashboard:
            {
                Intent i = new Intent(this, EmployerDashboardActivity.class);
                startActivity(i);
                break;
            }
            case R.id.profile:
            {
                Intent i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                break;
            }
            case R.id.settings:
            {
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;
            }
            case R.id.createJob:
            {
                Intent i = new Intent(this, CreateJobActivity.class);
                startActivity(i);
                break;
            }
            case R.id.about:
            {
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void setListData()
    {
        for (int i = 0; i < 3; i++) {

            final JobCard sched = new JobCard();

            sched.setBusinessName("McDonald's "+ i);
            sched.setLogo("Logo " + i);
            sched.setJobCategory("Chef " + i);

            CustomListViewValuesArr.add(sched);
        }
    }

    public void onItemClick(int mPosition)
    {
        JobCard tempValues = (JobCard) CustomListViewValuesArr.get(mPosition);

        Toast.makeText(CustomListView,"" + tempValues.getBusinessName() + " Logo:" + tempValues.getLogo() + " Job category:" + tempValues.getJobCategory(), Toast.LENGTH_LONG).show();
    }
}

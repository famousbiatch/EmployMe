package com.employme.employme;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class JobListActivity extends AppCompatActivity {

    ListView list;
    CustomAdapter adapter;
    public JobListActivity CustomListView = null;
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

    public void setListData() {
        for (int jobID : SQLiteDB.getInstance().getAllJobs())
        {
            final JobCard entry = new JobCard();
            List<String> info = SQLiteDB.getInstance().getJobListing(jobID); //if country & city dont match user, continue; statement

            entry.setId(Integer.valueOf(info.get(0)));
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
        i.putExtra("intentName", "JobListActivity");
        startActivity(i);
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
            case R.id.refresh:
            {
                finish();
                startActivity(getIntent());
                Toast.makeText(this, "Refresh successful", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.favorites:
            {
                Intent i = new Intent(this, FavoritesActivity.class);
                startActivity(i);
                break;
            }
            case R.id.employerDashboard:
            {
                Intent i = new Intent(this, EmployerDashboardActivity.class);
                startActivity(i);
                break;
            }
            case R.id.profile:
            {
                Intent i = new Intent(this, ProfileActivity.class);
                i.putExtra("intentName", "JobListActivity");
                startActivity(i);
                break;
            }
            case R.id.myAppsFIRST:
            {
                Intent i = new Intent(this, MyApplicationsActivity.class);
                i.putExtra("intentName", "JobListActivity");
                startActivity(i);
                break;
            }
            case R.id.settings:
            {
                Intent i = new Intent(this, SettingsActivity.class);
                i.putExtra("intentName", "JobListActivity");
                startActivity(i);
                break;
            }
            case R.id.createJob:
            {
                Intent i = new Intent(this, CreateJobActivity.class);
                i.putExtra("intentName", "JobListActivity");
                startActivity(i);
                break;
            }
            case R.id.about:
            {
                Intent i = new Intent(this, AboutActivity.class);
                i.putExtra("intentName", "JobListActivity");
                startActivity(i);
                break;
            }
            case R.id.signOut:
            {
                SQLiteDB.getInstance().updateSession("");
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

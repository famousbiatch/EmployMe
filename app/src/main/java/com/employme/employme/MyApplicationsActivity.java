package com.employme.employme;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyApplicationsActivity extends AppCompatActivity {

    ListView list;
    CustomAdapterMyApplications adapter;
    public MyApplicationsActivity CustomListView = null;
    public ArrayList<JobCard> CustomListViewValuesArr = new ArrayList<JobCard>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applications);

        CustomListView = this;

        setListData();

        Resources res = getResources();
        list = (ListView) findViewById(R.id.lstMyApps);

        adapter = new CustomAdapterMyApplications(CustomListView, CustomListViewValuesArr, res);
        list.setAdapter(adapter);
    }

    private void setListData() {
        for (int jobID : SQLiteDB.getInstance().getUserApps(Integer.valueOf(SQLiteDB.getInstance().getSessionUser())))
        {
            final JobCard entry = new JobCard();
            List<String> info = SQLiteDB.getInstance().getJobListing(jobID);

            entry.setId(Integer.valueOf(info.get(0)));
            entry.setLogo(info.get(3));
            entry.setBusinessName(info.get(2));
            entry.setJobCategory(info.get(7));

            CustomListViewValuesArr.add(entry);
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

    public void onItemClick(int mPosition) {}
}

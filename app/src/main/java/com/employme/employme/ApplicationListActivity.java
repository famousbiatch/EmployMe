package com.employme.employme;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ApplicationListActivity extends AppCompatActivity {

    ListView list;
    CustomAdapterApplications adapter;
    public ApplicationListActivity CustomListView = null;
    public ArrayList<AppCard> CustomListViewValuesArr = new ArrayList<AppCard>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);

        CustomListView = this;

        setListData();

        Resources res = getResources();
        list = (ListView) findViewById(R.id.lstApps);

        adapter = new CustomAdapterApplications(CustomListView, CustomListViewValuesArr, res);
        list.setAdapter(adapter);
    }

    private void setListData() {
        for (int userID : SQLiteDB.getInstance().getAllApplications(getIntent().getIntExtra("job_id", 99999)))
        {
            final AppCard entry = new AppCard();
            User info = SQLiteDB.getInstance().getUser(userID);

            entry.setBusinessName(SQLiteDB.getInstance().getJobListing(getIntent().getIntExtra("job_id", 99999)).get(2));
            entry.setApplicantId(info.getId());
            entry.setApplicantName(info.getName());
            entry.setApplicantPhoneNumber(info.getPhoneNumber());
            entry.setApplicantEmail(info.getEmail());
            entry.setApplicantAge(info.getAge());
            entry.setApplicantCity(info.getCity());
            entry.setEducation(info.getEducation());
            entry.setLicense(info.getDriverLicense() == 1 ? true : false);

            CustomListViewValuesArr.add(entry);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, JobPageActivity.class);
        i.putExtra("job_id", getIntent().getIntExtra("job_id", 999999));
        i.putExtra("intentName", getIntent().getStringExtra("intentName"));
        startActivity(i);
        super.onBackPressed();
    }

    public void onItemClick(int mPosition) {
    }
}

package com.employme.employme;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
        CustomListView = this;

        setListData();

        Resources res = getResources();
        list = (ListView) findViewById(R.id.lstJobs);

        adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
        list.setAdapter(adapter);
    }

    public void setListData()
    {
        for (int i = 0; i < 3; i++) {

            final JobCard sched = new JobCard();

            sched.setBusinessName(" Business "+ i);
            sched.setLogo(" Logo " + i);
            sched.setJobDescription(" Job Description " + i);

            CustomListViewValuesArr.add(sched);
        }
    }

    public void onItemClick(int mPosition)
    {
        JobCard tempValues = (JobCard) CustomListViewValuesArr.get(mPosition);

        Toast.makeText(CustomListView,"" + tempValues.getBusinessName() + "Logo:" + tempValues.getLogo() + " Job description:" + tempValues.getJobDescription(), Toast.LENGTH_LONG).show();
    }
}

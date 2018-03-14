package com.employme.employme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class JobPageActivity extends AppCompatActivity {

    private int job_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbarJob);
        setSupportActionBar(toolbar);

        this.job_id = getIntent().getIntExtra("job_id", 0);
    }
}

package com.employme.employme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class JobPageActivity extends AppCompatActivity {

    private boolean fav;
    private int job_id;
    private ImageView ivFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbarJob);
        setSupportActionBar(toolbar);

        ivFavorite = (ImageView) findViewById(R.id.ivFavorite);

        this.job_id = getIntent().getIntExtra("job_id", 0);

        fav = false;
        if (SQLiteDB.getInstance().isFavorite(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), job_id)) {
            ivFavorite.setImageResource(R.drawable.ic_golden_star);
            fav = true;
        }
    }


    public void favoriteClick(View view) {
        if (!fav) {
            SQLiteDB.getInstance().addFavorite(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), job_id);
            ivFavorite.setImageResource(R.drawable.ic_golden_star);
            fav = true;
        } else {
            SQLiteDB.getInstance().deleteFavorite(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), job_id);
            ivFavorite.setImageResource(R.drawable.ic_star_empty);
            fav = false;
        }
    }

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

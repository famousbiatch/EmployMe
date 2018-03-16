package com.employme.employme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivProfilePicture;
    TextView tvFullName;
    TextView tvEmail;
    TextView tvPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfilePicture = (ImageView) findViewById(R.id.ivProfilePicture);
        tvFullName = (TextView) findViewById(R.id.tvFullName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvPassword = (TextView) findViewById(R.id.tvPassword);

        User loggedInUser = SQLiteDB.getInstance().getUser(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()));
        tvFullName.setText(loggedInUser.getName());
        tvEmail.setText(loggedInUser.getEmail());
        int starLength = loggedInUser.getPassword().length();
        String starredPass = "*";
        for (int i = 1; i < starLength; i++)
            starredPass += "*";
        tvPassword.setText(starredPass);
    }
}

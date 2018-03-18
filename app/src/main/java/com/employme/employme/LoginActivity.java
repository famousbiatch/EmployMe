package com.employme.employme;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.employme.employme.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();

        if (!SQLiteDB.getInstance().getSessionUser().equals("")) //IF USER HAS 1 OR MORE LISTED JOBS TAKE TO EMPLOYER DASHBOARD *************************
        {
            //SEND TO EMPLOYER DASHBOARD IF USER HAS ANY LISTED JOBS
            if (SQLiteDB.getInstance().hasJobs(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()))) {
                Intent i = new Intent(this, EmployerDashboardActivity.class);
                startActivity(i);
            } else {
                Intent i = new Intent(this, JobList.class);
                startActivity(i);
            }
        }
    }

    private void initialize() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        intent.putExtra("intentName", "Login");
        startActivity(intent);
    }

    public void confirmCredentials(View view) {

        User user = SQLiteDB.getInstance().getUser(etEmail.getText().toString().toLowerCase());

        if (user == null || etEmail.getText().toString().contains(" ")) {
            Toast.makeText(this, "Incorrect E-mail", Toast.LENGTH_LONG).show();
        }
        else if (etPassword.getText().toString().equals(user.getPassword()))
        {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            SQLiteDB.getInstance().updateSession(Integer.toString(user.getId()));
            Intent intent = new Intent(this, JobList.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Incorrect Password", Toast.LENGTH_LONG).show();
        }
    }

    public void goToForgotPassword(View view) {
        Intent i = new Intent(this, ForgotPasswordActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //Go Back
        finish();
    }

    public void clearFocus(View view) {
        view.requestFocus();
    }
}

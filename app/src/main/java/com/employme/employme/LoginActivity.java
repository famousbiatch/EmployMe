package com.employme.employme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

        if (!SQLiteDB.getInstance().getSessionUser().equals(""))
        {
            if (SQLiteDB.getInstance().hasJobs(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()))) {
                Intent i = new Intent(this, EmployerDashboardActivity.class);
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(this, JobListActivity.class);
                startActivity(i);
                finish();
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
            final ProgressDialog pd = new ProgressDialog(this);
            try {
                pd.setTitle("Logging in");
                pd.show();
            } catch (Exception x) {}
            SQLiteDB.getInstance().updateSession(Integer.toString(user.getId()));
            Intent intent = new Intent(this, JobListActivity.class);
            startActivity(intent);
            finish();
            try {pd.dismiss();} catch (Exception x) {}
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
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void clearFocus(View view) {
        view.requestFocus();
    }
}

package com.employme.employme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.employme.employme.R;

public class SignupActivity extends AppCompatActivity {

    private EditText etSignupFullName;
    private EditText etSignupEmail;
    private EditText etSignupPassword;
    private EditText etSignupConfirmPassword;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialize();
    }

    private void initialize() {
        etSignupFullName = (EditText) findViewById(R.id.etSignupFullName);
        etSignupEmail = (EditText) findViewById(R.id.etSignupEmail);
        etSignupPassword = (EditText) findViewById(R.id.etSignupPassword);
        etSignupConfirmPassword = (EditText) findViewById(R.id.etSignupConfirmPassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);
    }

    public void registerAccount(View view) {

        if ((!etSignupPassword.getText().toString().equals(etSignupConfirmPassword.getText().toString())) ||
                etSignupFullName.getText().toString().equals("") ||
                etSignupEmail.getText().toString().equals("") ||
                etSignupPassword.getText().toString().equals("") ||
                SQLiteDB.getInstance().getUser(etSignupEmail.getText().toString()) != null ||
                etSignupEmail.getText().toString().contains(" ") ||
                !etSignupEmail.getText().toString().contains("@"))
        {
            Toast.makeText(this, "Check your input", Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User(
                etSignupFullName.getText().toString(),
                etSignupEmail.getText().toString().toLowerCase(),
                etSignupPassword.getText().toString());

        SQLiteDB.getInstance().addUser(user);
        SQLiteDB.getInstance().updateSession(Integer.toString(SQLiteDB.getInstance().getUser(etSignupEmail.getText().toString()).getId()));
        Intent intent = new Intent(this, JobList.class);
        startActivity(intent);
    }

    public void clearFocus(View view) {
        view.requestFocus();
    }
}

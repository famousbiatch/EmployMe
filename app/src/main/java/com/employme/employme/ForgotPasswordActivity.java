package com.employme.employme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etForgotPasswordEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initialize();
    }

    private void initialize() {
        this.etForgotPasswordEmail = (EditText) findViewById(R.id.etForgotPasswordEmail);
    }

    public void clearFocus(View view) { view.requestFocus(); }

    public void goToChangePassword(View view) {
        if (SQLiteDB.getInstance().getUser(etForgotPasswordEmail.getText().toString().toLowerCase()) == null)
        {
            Toast.makeText(this, "Incorrect E-mail", Toast.LENGTH_LONG).show();
            return;
        }
        Intent i = new Intent(this, ChangePasswordActivity.class);
        i.putExtra("email", etForgotPasswordEmail.getText().toString().toLowerCase());
        startActivity(i);
    }
}

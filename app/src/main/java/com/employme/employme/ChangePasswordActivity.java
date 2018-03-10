package com.employme.employme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etNewPassword;
    private EditText etConfirmNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initialize();
    }

    private void initialize() {
        this.etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        this.etConfirmNewPassword = (EditText) findViewById(R.id.etConfirmNewPassword);
    }

    public void confirmNewPassword(View view) {
        if (!etNewPassword.getText().toString().equals(etConfirmNewPassword.getText().toString()))
        {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

        User u = SQLiteDB.getInstance().getUser(getIntent().getStringExtra("email"));
        u.setPassword(etNewPassword.getText().toString());
        SQLiteDB.getInstance().updateUser(u);
        Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void clearFocus(View view) { view.requestFocus(); }
}

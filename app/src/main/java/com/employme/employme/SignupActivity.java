package com.employme.employme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.employme.employme.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupActivity extends AppCompatActivity {

    private StorageReference mStorage;

    private EditText etSignupFullName;
    private EditText etSignupEmail;
    private EditText etSignupPassword;
    private EditText etSignupConfirmPassword;
    private Button btnSignup;
    private Spinner spSignupCity;
    private EditText etAge;
    private EditText etEducation;
    private Switch swDriverLicense;
    private EditText etPhoneNumber;
    private ImageView imgProfile;

    private Uri picturePath;
    private static final int GALLERY_INTENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialize();
    }

    private void initialize() {
        mStorage = FirebaseStorage.getInstance().getReference();
        etSignupFullName = (EditText) findViewById(R.id.etSignupFullName);
        etSignupEmail = (EditText) findViewById(R.id.etSignupEmail);
        etSignupPassword = (EditText) findViewById(R.id.etSignupPassword);
        etSignupConfirmPassword = (EditText) findViewById(R.id.etSignupConfirmPassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        spSignupCity = (Spinner) findViewById(R.id.spSignupCity);
        etAge = (EditText) findViewById(R.id.etSignupAge);
        etEducation = (EditText) findViewById(R.id.etSignupEducation);
        swDriverLicense = (Switch) findViewById(R.id.swDriverLicense);
        etPhoneNumber = (EditText) findViewById(R.id.etSignupPhoneNumber);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_INTENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            picturePath = data.getData();
            imgProfile.setImageURI(picturePath);
        }
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

        User user = new User();
        user.setName(etSignupFullName.getText().toString());
        user.setEmail(etSignupEmail.getText().toString().toLowerCase());
        user.setPassword(etSignupPassword.getText().toString());
        user.setCity(spSignupCity.getSelectedItem().toString());
        user.setAge(Integer.valueOf(etAge.getText().toString()));
        user.setEducation(etEducation.getText().toString());
        user.setDriverLicense((swDriverLicense.isChecked()) ? 1 : 0);
        user.setPhoneNumber(etPhoneNumber.getText().toString());

        SQLiteDB.getInstance().addUser(user);
        SQLiteDB.getInstance().updateSession(Integer.toString(SQLiteDB.getInstance().getUser(etSignupEmail.getText().toString()).getId()));

        if (picturePath != null) {
            try {
                StorageReference filePath = mStorage.child("ProfilePictures").child(SQLiteDB.getInstance().getSessionUser());

                filePath.putFile(picturePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    }
                });
            } catch (Exception x) {}
        }

        Intent intent = new Intent(this, JobList.class);
        startActivity(intent);
        finish();
    }

    public void clearFocus(View view) {
        view.requestFocus();
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra("intentName").equals("Login"))
            startActivity(new Intent(this, LoginActivity.class));

        super.onBackPressed();
    }
}

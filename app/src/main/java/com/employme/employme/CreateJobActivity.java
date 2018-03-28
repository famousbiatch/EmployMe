package com.employme.employme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class CreateJobActivity extends AppCompatActivity {

    private ImageView imgLogo;
    private EditText etBusinessName;
    private EditText etJobDescription;
    private EditText etBusinessNumber;
    private EditText etBusinessLocation;
    private Spinner spJobCategory;
    private EditText etMinAge;
    private EditText etMaxAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        initialize();
    }

    public void registerJob(View view) {
        if (picturePath == null) {
            Toast.makeText(this, "Business must inclode a logo", Toast.LENGTH_LONG).show();
            return;
        }
        if (etBusinessName.getText().toString().length() < 2 ||
                etJobDescription.getText().toString().length() < 2 || (
                etBusinessNumber.getText().toString().length() != 7 &&
                etBusinessNumber.getText().toString().length() != 9 &&
                etBusinessNumber.getText().toString().length() != 10) || (
                (!etBusinessNumber.getText().toString().startsWith("05")) &&
                (!etBusinessNumber.getText().toString().startsWith("04")) &&
                (!etBusinessNumber.getText().toString().startsWith("6"))) ||
                etBusinessLocation.getText().toString().length() < 2)
        {
            Toast.makeText(this, "Check your input", Toast.LENGTH_LONG).show();
            return;
        }
        final ProgressDialog pd = new ProgressDialog(this);
        try {
        pd.setTitle("Registering Business");
        pd.show();} catch (Exception x) {}

        SQLiteDB.getInstance().addJobListing(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), etBusinessName.getText().toString(),
                "LOGO URL HERE", etJobDescription.getText().toString(), etBusinessNumber.getText().toString(), etBusinessLocation.getText().toString(),
                spJobCategory.getSelectedItem().toString(), etMinAge.getText().toString(), etMaxAge.getText().toString());
        Toast.makeText(this, "Successfully Listed Job", Toast.LENGTH_SHORT).show();

            try {
                StorageReference filePath = mStorage.child("BusinessLogos").child(etBusinessName.getText().toString());

                filePath.putFile(picturePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        try {pd.dismiss();} catch (Exception x) {}
                    }
                });
            } catch (Exception x) {}

        Intent i = new Intent(this, EmployerDashboardActivity.class);
        startActivity(i);
        finish();
    }

    private void initialize() {
        mStorage = FirebaseStorage.getInstance().getReference();
        this.imgLogo = (ImageView) findViewById(R.id.imgUploadLogo);
        this.etBusinessName = (EditText) findViewById(R.id.etBusinessName);
        this.etJobDescription = (EditText) findViewById(R.id.etJobDescription);
        this.etBusinessNumber = (EditText) findViewById(R.id.etBusinessNumber);
        this.etBusinessLocation = (EditText) findViewById(R.id.etBusinessLocation);
        this.spJobCategory = (Spinner) findViewById(R.id.spJobCategory);
        this.etMinAge = (EditText) findViewById(R.id.etMinAge);
        this.etMaxAge = (EditText) findViewById(R.id.etMaxAge);

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_INTENT);
            }
        });
    }

    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
    private Uri picturePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            picturePath = data.getData();
            imgLogo.setImageURI(picturePath);
        }
    }

    public void clearFocus(View view) { view.requestFocus(); }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getStringExtra("intentName").equals("JobListActivity"))
                startActivity(new Intent(this, JobListActivity.class));
            else if (getIntent().getStringExtra("intentName").equals("EmployerDashboard"))
                startActivity(new Intent(this, EmployerDashboardActivity.class));
            else if (getIntent().getStringExtra("intentName").equals("Favorites"))
                startActivity(new Intent(this, FavoritesActivity.class));
        } catch (Exception x) {}

        super.onBackPressed();
    }
}

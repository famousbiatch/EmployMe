package com.employme.employme;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapterApplications extends BaseAdapter implements OnClickListener {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    AppCard tempValues = null;
    int i = 0;


    public CustomAdapterApplications(Activity a, ArrayList d, Resources resLocal) {

        activity = a;
        data = d;
        res = resLocal;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public int getCount() {
        if(data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{

        public TextView businessName;
        public TextView applicantName;
        public TextView applicantAge;
        public ImageView applicantPicture;
        public TextView applicantPhoneNumber;
        public TextView applicantEmail;
        public TextView applicantCity;
        public TextView applicantEducation;
        public TextView license;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        final ViewHolder holder;

        if (convertView == null) {

            vi = inflater.inflate(R.layout.activity_app_card ,null);

            holder = new ViewHolder();
            holder.businessName = (TextView) vi.findViewById(R.id.tvBusinessNameApp);
            holder.applicantName = (TextView) vi.findViewById(R.id.tvApplicantName);
            holder.applicantAge = (TextView) vi.findViewById(R.id.tvApplicantAge);
            holder.applicantPicture = (ImageView) vi.findViewById(R.id.imgApplicantPicture);
            holder.applicantPhoneNumber = (TextView) vi.findViewById(R.id.tvApplicantNumber);
            holder.applicantEmail = (TextView) vi.findViewById(R.id.tvApplicantEmail);
            holder.applicantCity = (TextView) vi.findViewById(R.id.tvApplicantCity);
            holder.applicantEducation = (TextView) vi.findViewById(R.id.tvApplicantEducation);
            holder.license = (TextView) vi.findViewById(R.id.tvApplicantLicense);

            vi.setTag(holder);
        }
        else
            holder = (ViewHolder) vi.getTag();

        if(data.size() <= 0)
        {
            holder.applicantName.setText("No Data");
        }
        else
        {

            tempValues = null;
            tempValues = (AppCard) data.get(position);

            holder.businessName.setText(tempValues.getBusinessName());
            holder.applicantName.setText(tempValues.getApplicantName());
            holder.applicantPhoneNumber.setText(tempValues.getApplicantPhoneNumber());
            holder.applicantEmail.setText(tempValues.getApplicantEmail());
            holder.applicantAge.setText(String.valueOf(tempValues.getApplicantAge()));
            holder.applicantCity.setText(tempValues.getApplicantCity());
            holder.applicantEducation.setText(tempValues.getEducation());
            holder.license.setText(tempValues.isLicense() ? "Yes" : "No");

            try {

                FirebaseStorage.getInstance().getReference().child("ProfilePictures/" + String.valueOf(tempValues.getApplicantId())).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(activity).load(uri).fit().placeholder(R.drawable.ic_no_profile_pic).error(R.drawable.ic_no_profile_pic).into(holder.applicantPicture);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });

            } catch (Exception x) {}

            vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    private class OnItemClickListener  implements OnClickListener{
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            ApplicationListActivity sct = (ApplicationListActivity) activity;

            sct.onItemClick(mPosition);
        }
    }
}
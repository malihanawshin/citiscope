package com.example.imm.citi.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.User;

public class ProfileDisplayActivity extends AppCompatActivity {
    private TextView txtName, txtBio;
    private ImageView imgPhone, imgEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);

        txtName = (TextView) findViewById(R.id.prof_dis_name);
        txtBio = (TextView) findViewById(R.id.prof_dis_bio);

        txtName.setText(User.Name);
        txtBio.setText(User.Bio);

        imgPhone = (ImageView) findViewById(R.id.img_user_call);
        imgEmail = (ImageView) findViewById(R.id.img_user_email);

        imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCall(User.Phone);
            }
        });

        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail(User.Email);
            }
        });
    }

    private void sendMail(String emailAddress) {
        if(emailAddress==null || emailAddress.equals(""))
            return;

//            Toast.makeText(parent, emailAddress, Toast.LENGTH_SHORT).show();
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{emailAddress});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "User Interaction from CITISCOPE");

        emailIntent.setType("message/rfc822");
        startActivity(emailIntent);
    }

    private void startCall(String phoneNo) {
        if(phoneNo==null || phoneNo.equals(""))
            return;

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNo));
//            Toast.makeText(parent, phoneNo, Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }
}

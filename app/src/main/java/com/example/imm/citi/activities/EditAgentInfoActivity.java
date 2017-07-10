package com.example.imm.citi.activities;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imm.citi.R;

public class EditAgentInfoActivity extends AppCompatActivity {

    String service;
    private TextView serviceLabel;
    private LinearLayout renting;
    private LinearLayout tuition;
    private LinearLayout donation;
    private LinearLayout doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_agent_info);

        setTitle("Edit Agent Info");

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        service = intent.getStringExtra("servicename");

        System.out.println("SERVICE: " + service);

        serviceLabel = (TextView) findViewById(R.id.text_service_label);
        renting = (LinearLayout) findViewById(R.id.layout_renting);
        tuition = (LinearLayout) findViewById(R.id.layout_tuition);
        donation = (LinearLayout) findViewById(R.id.layout_donation);
        doctor = (LinearLayout) findViewById(R.id.layout_doctor);

        if(service.equals("Tuition")) {
            tuition.setVisibility(View.VISIBLE);
            serviceLabel.setText("Tuition");
        }
        else if(service.equals("Doctor")) {
            doctor.setVisibility(View.VISIBLE);
            serviceLabel.setText("Doctor");
        }
        else if(service.equals("Apartment Renting")) {
            renting.setVisibility(View.VISIBLE);
            serviceLabel.setText("Apartment Renting");
        }
        else if(service.equals("Blood Donation")) {
            donation.setVisibility(View.VISIBLE);
            serviceLabel.setText("Blood Donation");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}

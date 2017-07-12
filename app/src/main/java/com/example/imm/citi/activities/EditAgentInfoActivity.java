package com.example.imm.citi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imm.citi.R;
import com.example.imm.citi.agents.AgentBloodDonation;
import com.example.imm.citi.agents.CreatorAgent;
import com.example.imm.citi.agents.CreatorBloodDonation;
import com.example.imm.citi.agents.LocalAgent;
import com.example.imm.citi.technicalClasses.UserAgentInput;

public class EditAgentInfoActivity extends AppCompatActivity {

    String service;
    private TextView serviceLabel;
    private LinearLayout renting;
    private LinearLayout tuition;
    private LinearLayout donation;
    private LinearLayout doctor;

    private Button btnSave;

    private EditText edtName, edtEmail, edtPhone, edtAddress, edtDistrict;

    private EditText bldType, bldSmoke, bldDonNo, bldLastDon;

    LocalAgent locAg;
    CreatorAgent creaAg;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_agent_info);

        setTitle("Edit Agent Info");

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        service = intent.getStringExtra("servicename");
        locAg = intent.getParcelableExtra("agent");

        System.out.println("SERVICE: " + service);
        System.out.println("AGENT in EDIT: " + locAg);

        if(locAg==null){
            id = "1";
        }
        else{
            id = locAg.id;
        }

        serviceLabel = (TextView) findViewById(R.id.text_service_label);
        renting = (LinearLayout) findViewById(R.id.layout_renting);
        tuition = (LinearLayout) findViewById(R.id.layout_tuition);
        donation = (LinearLayout) findViewById(R.id.layout_donation);
        doctor = (LinearLayout) findViewById(R.id.layout_doctor);

        setCommonEditTexts();

        if(service.equals("Tuition")) {
            tuition.setVisibility(View.VISIBLE);
            serviceLabel.setText("Tuition");
            //creaAg = new CreatorTuition(this);
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
            setBloodDonationEditTexts();
            creaAg = new CreatorBloodDonation(this);
        }

        setButtonListener();
    }

    private void setBloodDonationEditTexts() {
        bldType = (EditText) findViewById(R.id.edit_blood_type);
        bldSmoke = (EditText) findViewById(R.id.edit_smoking);
        bldDonNo = (EditText) findViewById(R.id.edit_donations);
        bldLastDon = (EditText) findViewById(R.id.edit_since_donated);

        if(locAg!=null){
            AgentBloodDonation agBld = (AgentBloodDonation) locAg;
            edtDistrict.setText(agBld.district);
            bldType.setText(agBld.bloodType);
            bldSmoke.setText(agBld.smokingHabit);
            bldDonNo.setText(agBld.donationsDone+"");
            bldLastDon.setText(agBld.daysSinceLastDonated+"");
        }
    }

    private void setCommonEditTexts() {
        edtName = (EditText) findViewById(R.id.edit_agent_name);
        edtEmail = (EditText) findViewById(R.id.edit_agent_email);
        edtPhone = (EditText) findViewById(R.id.edit_agent_phone);
        edtAddress = (EditText) findViewById(R.id.edit_agent_address);
        edtDistrict = (EditText) findViewById(R.id.edit_agent_district);

        if(locAg!=null){
            edtName.setText(locAg.name);
            edtEmail.setText(locAg.email);
            edtPhone.setText(locAg.phone);
            edtAddress.setText(locAg.address);
        }
    }















    private void setButtonListener() {
        btnSave = (Button) findViewById(R.id.btnToSaveChange);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locAg == null){
                    creaAg.addAgent(getUserInput());
                }
                else{
                    creaAg.updateAgent(getUserInput());
                }
            }
        });
    }

    private UserAgentInput getUserInput() {
        UserAgentInput userInput = new UserAgentInput(id, service, edtName.getText().toString(),
                edtEmail.getText().toString(), edtPhone.getText().toString(), edtAddress.getText().toString());

        if(service.equals("Tuition")) {
            //return getTuitionInput();
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
            return getBloodDonationInput(userInput);
        }

        return null;
    }

    private UserAgentInput getBloodDonationInput(UserAgentInput userInput) {
        userInput.addBloodDonationAttributes(edtDistrict.getText().toString(), bldType.getText().toString(),
                bldSmoke.getText().toString(), bldDonNo.getText().toString(), bldLastDon.getText().toString());

        return userInput;
    }


    public void afterSave() {
        Intent intent = new Intent(this, AgentProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }











    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}

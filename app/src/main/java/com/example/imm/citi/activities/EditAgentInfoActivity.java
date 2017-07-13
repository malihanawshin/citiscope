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
import com.example.imm.citi.agents.AgentApartmentRenting;
import com.example.imm.citi.agents.AgentBloodDonation;
import com.example.imm.citi.agents.AgentDoctor;
import com.example.imm.citi.agents.AgentTuition;
import com.example.imm.citi.agents.CreatorAgent;
import com.example.imm.citi.agents.CreatorApartmentRenting;
import com.example.imm.citi.agents.CreatorBloodDonation;
import com.example.imm.citi.agents.CreatorDoctor;
import com.example.imm.citi.agents.CreatorTuition;
import com.example.imm.citi.agents.LocalAgent;
import com.example.imm.citi.technicalClasses.UserAgentInput;

import java.util.ArrayList;

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
    private EditText aptArea, aptType, aptPrice, aptSize, aptFloor, aptRoom;
    private EditText docSpec, docAddresses, docDegrees, docHospital, docYears;
    private EditText tuiAreas, tuiMediums, tuiClasses, tuiSubjects, 
                        tuiSchool, tuiCollege, tuiUniversity, tuiOccupation, tuiDone, tuiProfile;

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
            setTuitionEditTexts();
            creaAg = new CreatorTuition(this);
        }
        else if(service.equals("Doctor")) {
            doctor.setVisibility(View.VISIBLE);
            serviceLabel.setText("Doctor");
            setDoctorEditTexts();
            creaAg = new CreatorDoctor(this);
        }
        else if(service.equals("Apartment Renting")) {
            renting.setVisibility(View.VISIBLE);
            serviceLabel.setText("Apartment Renting");
            setApartmentRentingEditTexts();
            creaAg = new CreatorApartmentRenting(this);
        }
        else if(service.equals("Blood Donation")) {
            donation.setVisibility(View.VISIBLE);
            serviceLabel.setText("Blood Donation");
            setBloodDonationEditTexts();
            creaAg = new CreatorBloodDonation(this);
        }

        setButtonListener();
    }

    private void setTuitionEditTexts() {
        tuiAreas = (EditText) findViewById(R.id.edit_tui_area);
        tuiMediums = (EditText) findViewById(R.id.edit_medium);
        tuiClasses = (EditText) findViewById(R.id.edit_class);
        tuiSubjects = (EditText) findViewById(R.id.edit_subjects);
        tuiSchool = (EditText) findViewById(R.id.edit_school_name);
        tuiCollege = (EditText) findViewById(R.id.edit_college_name);
        tuiUniversity = (EditText) findViewById(R.id.edit_university_name);
        tuiOccupation = (EditText) findViewById(R.id.edit_occupation);
        tuiDone = (EditText) findViewById(R.id.edit_tuitions);
        tuiProfile = (EditText) findViewById(R.id.edit_link);

        if(locAg!=null){
            AgentTuition agTui = (AgentTuition) locAg;
            edtDistrict.setText(agTui.district);
            tuiMediums.setText(getStringFromArray(agTui.mediums));
            tuiClasses.setText(getStringFromArray(agTui.classes));
            tuiAreas.setText(getStringFromArray(agTui.areas));
            tuiSubjects.setText(getStringFromArray(agTui.subjects));
            tuiSchool.setText(agTui.school);
            tuiCollege.setText(agTui.college);
            tuiUniversity.setText(agTui.university);
            tuiOccupation.setText(agTui.occupation);
            tuiDone.setText(agTui.tuitionsDone+"");
            tuiProfile.setText(agTui.profileLink);
        }
}

    private void setDoctorEditTexts() {
        docAddresses = (EditText) findViewById(R.id.edit_doc_addresses);
        docDegrees = (EditText) findViewById(R.id.edit_degrees);
        docHospital = (EditText) findViewById(R.id.edit_hospital);
        docSpec = (EditText) findViewById(R.id.edit_speciality);
        docYears = (EditText) findViewById(R.id.edit_practice);

        if(locAg!=null){
            AgentDoctor agDoc = (AgentDoctor) locAg;
            edtDistrict.setText(agDoc.district);
            docAddresses.setText(getStringFromArray(agDoc.addresses));
            docDegrees.setText(getStringFromArray(agDoc.degrees));
            docSpec.setText(agDoc.specialty);
            docHospital.setText(agDoc.hospitalName);
            docYears.setText(agDoc.yearsInPractice+"");
        }
    }

    private void setApartmentRentingEditTexts() {
        aptArea = (EditText) findViewById(R.id.edit_apartment_area);
        aptType = (EditText) findViewById(R.id.edit_property_type);
        aptPrice = (EditText) findViewById(R.id.edit_price);
        aptSize = (EditText) findViewById(R.id.edit_area_m2);
        aptFloor = (EditText) findViewById(R.id.edit_floor);
        aptRoom = (EditText) findViewById(R.id.edit_room);

        if(locAg!=null){
            AgentApartmentRenting agApt = (AgentApartmentRenting)locAg;
            edtDistrict.setText(agApt.district);
            aptArea.setText(agApt.area);
            aptType.setText(agApt.propertyType);
            aptPrice.setText(agApt.price+"");
            aptSize.setText(agApt.size+"");
            aptFloor.setText(agApt.floor+"");
            aptRoom.setText(agApt.roomNo+"");
        }
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
            return getTuitionInput(userInput);
        }
        else if(service.equals("Doctor")) {
            return getDoctorInput(userInput);
        }
        else if(service.equals("Apartment Renting")) {
            return getApartmentRentingInput(userInput);
        }
        else if(service.equals("Blood Donation")) {
            return getBloodDonationInput(userInput);
        }

        return null;
    }

    private UserAgentInput getTuitionInput(UserAgentInput userInput) {
        userInput.addTuitionAttributes(edtDistrict.getText().toString(), tuiAreas.getText().toString(),
                tuiMediums.getText().toString(), tuiClasses.getText().toString(), tuiSubjects.getText().toString(),
                tuiSchool.getText().toString(), tuiCollege.getText().toString(), tuiUniversity.getText().toString(),
                tuiOccupation.getText().toString(), tuiDone.getText().toString(), tuiProfile.getText().toString());
        return userInput;
    }

    private UserAgentInput getDoctorInput(UserAgentInput userInput) {
        userInput.addDoctorAttributes(edtDistrict.getText().toString(), docSpec.getText().toString(),
                docHospital.getText().toString(), docYears.getText().toString(), docAddresses.getText().toString(),
                docDegrees.getText().toString());
        return userInput;
    }

    private UserAgentInput getApartmentRentingInput(UserAgentInput userInput) {
        userInput.addApartmentRentingAttributes(edtDistrict.getText().toString(), aptArea.getText().toString(), aptType.getText().toString(),
                aptPrice.getText().toString(), aptSize.getText().toString(), aptFloor.getText().toString(), aptRoom.getText().toString());
        return userInput;
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





    private String getStringFromArray(ArrayList<String> arr) {
        String str = "";

        for(String s: arr){
            str += s + "; ";
        }
        str = str.substring(0, str.length()-2);

        return str;
    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}

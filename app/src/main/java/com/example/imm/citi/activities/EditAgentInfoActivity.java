package com.example.imm.citi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.DigitsKeyListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.imm.citi.technicalClasses.User;
import com.example.imm.citi.technicalClasses.UserAgentInput;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        View root = findViewById(R.id.layout_add_agent);
        Snackbar snackbar = Snackbar.make(root,"* Use ; For Multiple Answers",Snackbar.LENGTH_LONG);
        snackbar.show();


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
        edtPhone.setKeyListener(new DigitsKeyListener());
        edtAddress = (EditText) findViewById(R.id.edit_agent_address);
        edtDistrict = (EditText) findViewById(R.id.edit_agent_district);

        if(locAg!=null){
            edtName.setText(locAg.name);
            edtEmail.setText(locAg.email);
            edtPhone.setText(locAg.phone);
            edtAddress.setText(locAg.address);
        }
        else{
            edtEmail.setText(User.Email);
            edtPhone.setText(User.Phone);
        }
    }















    private void setButtonListener() {
        btnSave = (Button) findViewById(R.id.btnToSaveChange);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldsFilled()){
                    if(locAg == null){
                        creaAg.addAgent(getUserInput());
                    }
                    else{
                        creaAg.updateAgent(getUserInput());
                    }
                }

            }
        });
    }

    private boolean fieldsFilled() {
        if(edtName.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(edtEmail.getText().toString().equals("")){
            Toast.makeText(this, "Please enter an Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(edtPhone.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!verifyPhone(edtPhone.getText().toString())){
            Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(edtAddress.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a Location", Toast.LENGTH_SHORT).show();
            return false;
        }

        Boolean flag = true;

        if(service.equals("Tuition")) {
            flag = tuitionFieldsFilled();
            if(!flag)
                return flag;
        }
        else if(service.equals("Doctor")) {
            flag = doctorFieldsFilled();
            if(!flag)
                return flag;
        }
        else if(service.equals("Apartment Renting")) {
            flag = apartmentRentingFieldsFilled();
            if(!flag)
                return flag;
        }
        else if(service.equals("Blood Donation")) {
            flag = bloodDonationFieldsFilled();
            if(!flag)
                return flag;
        }

        return flag;
    }


    private Boolean verifyPhone(String phone1) {
        System.out.println("AREH PHONE DEKHTESE TO " + phone1);

        if(phone1.equals("")) return true;
        String prefix="";
        Pattern pat = Pattern.compile("(.*?)01[56789]{1}[0-9]{8}$");
        Matcher mat = pat.matcher(phone1);
        if(mat.find()==true)
        {
            System.out.println(phone1);
            prefix = mat.group(1);
            if(prefix.equals("") || prefix.equals("+88")) return true;
        }
        Toast.makeText(this,"Invalid Phone Number",Toast.LENGTH_LONG).show();
        return false;
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

    private Boolean tuitionFieldsFilled() {
        if(edtDistrict.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a District Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tuiAreas.getText().toString().equals("")){
            Toast.makeText(this, "Please enter at least 1 Area within the District", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tuiMediums.getText().toString().equals("")){
            Toast.makeText(this, "Please enter at least 1 Medium (eg: Bangla;English)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tuiClasses.getText().toString().equals("")){
            Toast.makeText(this, "Please enter at least 1 Class (eg: SSC; A Levels)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tuiSubjects.getText().toString().equals("")){
            Toast.makeText(this, "Please enter at least 1 Subject (eg: Physics; Accounting)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tuiDone.getText().toString().equals("")){
            Toast.makeText(this, "Please enter the number of Tuitions you have done", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private UserAgentInput getDoctorInput(UserAgentInput userInput) {
        userInput.addDoctorAttributes(edtDistrict.getText().toString(), docSpec.getText().toString(),
                docHospital.getText().toString(), docYears.getText().toString(), docAddresses.getText().toString(),
                docDegrees.getText().toString());
        return userInput;
    }


    private Boolean doctorFieldsFilled() {
        if(edtDistrict.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a District Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(docSpec.getText().toString().equals("")){
            Toast.makeText(this, "Please enter your Specialty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(docYears.getText().toString().equals("")){
            Toast.makeText(this, "Please enter your number of Years as a Doctor", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private UserAgentInput getApartmentRentingInput(UserAgentInput userInput) {
        userInput.addApartmentRentingAttributes(edtDistrict.getText().toString(), aptArea.getText().toString(), aptType.getText().toString(),
                aptPrice.getText().toString(), aptSize.getText().toString(), aptFloor.getText().toString(), aptRoom.getText().toString());
        return userInput;
    }

    private Boolean apartmentRentingFieldsFilled() {
        if(edtDistrict.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a District Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(aptArea.getText().toString().equals("")){
            Toast.makeText(this, "Please enter an Area", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(aptType.getText().toString().equals("")){
            Toast.makeText(this, "Please enter the Type (eg: Apartment/Flat)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(aptPrice.getText().toString().equals("")){
            Toast.makeText(this, "Please enter the Price", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private UserAgentInput getBloodDonationInput(UserAgentInput userInput) {
        userInput.addBloodDonationAttributes(edtDistrict.getText().toString(), bldType.getText().toString(),
                bldSmoke.getText().toString(), bldDonNo.getText().toString(), bldLastDon.getText().toString());

        return userInput;
    }

    private Boolean bloodDonationFieldsFilled() {
        if(edtDistrict.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a District Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(bldType.getText().toString().equals("")){
            Toast.makeText(this, "Please enter your Blood Group", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(bldDonNo.getText().toString().equals("")){
            Toast.makeText(this, "Please enter the Number of Donations you've made", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

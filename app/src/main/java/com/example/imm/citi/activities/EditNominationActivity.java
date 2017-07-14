package com.example.imm.citi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Nomination;
import com.example.imm.citi.technicalClasses.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EditNominationActivity extends AppCompatActivity{
    Nomination nomination;
    EditText nomName, nomDesc, nomSources, nomFilters, nomCities;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nomination);

        nomination = getIntent().getParcelableExtra("nomination");

        setTexts();
        setListener();

        setTitle("Edit Nomination");

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setListener() {
        btnSave = (Button) findViewById(R.id.btnToSaveNomination);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nomination newNom = getUserInput();
                if(nomination==null){
                    newNom.dateAdded = getCurrentDate();
                    newNom.addNomination();
                }
                else{
                    newNom.dateAdded = nomination.dateAdded;
                    newNom.updateNomination(nomination.name);
                }
            }
        });
    }

    private Nomination getUserInput() {
        Nomination nom = new Nomination(this);
        nom.setAttributes(nomName.getText().toString(), nomDesc.getText().toString(),
                separateStrings(nomSources.getText().toString()), User.Email, 0,
                separateStrings(nomFilters.getText().toString()), separateStrings(nomCities.getText().toString()),
                null);
        return nom;
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        return curDate;
    }

    private void setTexts() {
        nomName = (EditText) findViewById(R.id.edit_nomination_name) ;
        nomDesc = (EditText) findViewById(R.id.edit_nominations_details) ;
        nomSources = (EditText) findViewById(R.id.edit_source_list) ;
        nomFilters = (EditText) findViewById(R.id.edit_filter_list) ;
        nomCities = (EditText) findViewById(R.id.edit_city_list) ;

        if(nomination!=null) {
            nomName.setText(nomination.name);
            nomDesc.setText(nomination.description);
            nomSources.setText(getStringFromArray(nomination.sources));
            nomFilters.setText(getStringFromArray(nomination.filters));
            nomCities.setText(getStringFromArray(nomination.cities));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    private String getStringFromArray(ArrayList<String> arr) {
        String str = "";

        for(String s: arr){
            str += s + "; ";
        }
        str = str.substring(0, str.length()-2);

        return str;
    }

    private ArrayList<String> separateStrings(String str) {
        ArrayList<String> untrimmedResult = new ArrayList<>(Arrays.asList(str.split(";")));
        ArrayList<String> result = new ArrayList<>();

        for(String s: untrimmedResult){
            s = s.trim();
            result.add(s);
            System.out.println(">" + s +"<");
        }

        Set<String> resultSet = new HashSet<>();
        resultSet.addAll(result);
        result.clear();
        result.addAll(resultSet);

        return result;
    }
}

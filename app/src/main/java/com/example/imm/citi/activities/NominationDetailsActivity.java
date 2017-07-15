package com.example.imm.citi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Nomination;
import com.example.imm.citi.technicalClasses.User;

import java.util.ArrayList;

public class NominationDetailsActivity extends AppCompatActivity {
    Nomination nomination;
    Button edit;

    TextView nomName, nominator, nomDate, nomDesc, nomSources, nomFilters, nomCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomination_details);

        setTitle("Nomination Details");

        Intent intent = getIntent();
        nomination = intent.getParcelableExtra("nomination");

        System.out.println("nomnom " + nomination);
        setTexts();


        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setTexts() {
        nomName = (TextView) findViewById(R.id.text_nomination_name) ;
        nominator = (TextView) findViewById(R.id.set_nominator_name) ;
        nomDate = (TextView) findViewById(R.id.set_nominatoion_date) ;
        nomDesc = (TextView) findViewById(R.id.set_nomination_details) ;
        nomSources = (TextView) findViewById(R.id.set_source_list) ;
        nomFilters = (TextView) findViewById(R.id.set_filter_list) ;
        nomCities = (TextView) findViewById(R.id.set_city_list) ;

        nomName.setText(nomination.name);
        nominator.setText(nomination.nominator);
        nomDate.setText(nomination.dateAdded);
        nomDesc.setText(nomination.description);
        nomSources.setText(getStringFromArray(nomination.sources));
        nomFilters.setText(getStringFromArray(nomination.filters));
        nomCities.setText(getStringFromArray(nomination.cities));
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

    public void showEdit(View view){
        if(User.loggedIn){
            if(User.Email.equals(nomination.nominator)){
                Intent intent = new Intent(NominationDetailsActivity.this,EditNominationActivity.class);
                intent.putExtra("title","Edit Nomination");
                intent.putExtra("nomination", nomination);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "You do not have permission", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Log in First", Toast.LENGTH_LONG).show();
        }
    }

    public void removeNom(View view){
        if(User.loggedIn){
            if(User.Email.equals(nomination.nominator) || User.admin){
                nomination.removeNom(this);
            }
            else{
                Toast.makeText(this, "You do not have permission", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Log in First", Toast.LENGTH_LONG).show();
        }
    }
}

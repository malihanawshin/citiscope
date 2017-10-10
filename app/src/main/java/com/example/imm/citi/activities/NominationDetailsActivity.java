package com.example.imm.citi.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Nomination;
import com.example.imm.citi.technicalClasses.User;

import java.util.ArrayList;

public class NominationDetailsActivity extends AppCompatActivity {
    Nomination nomination;
    Button btn_edit,btn_remove;

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

        btn_edit = (Button) findViewById(R.id.btnToEditNomination);
        btn_remove = (Button) findViewById(R.id.btnToRemoveNomination);

        if(!User.loggedIn||!User.Email.equals(nomination.nominator)) {
            btn_edit.setAlpha(.5f);
            btn_edit.setClickable(false);
            btn_remove.setAlpha(.5f);
            btn_remove.setClickable(false);
        }

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setTexts() {
        nomName = (TextView) findViewById(R.id.text_nomination_name) ;
        nominator = (TextView) findViewById(R.id.set_nominator_name) ;
        nomDate = (TextView) findViewById(R.id.set_nominatoion_date) ;
        nomDesc = (TextView) findViewById(R.id.set_nomination_details) ;
        nomFilters = (TextView) findViewById(R.id.set_filter_list) ;
        nomCities = (TextView) findViewById(R.id.set_city_list) ;

        nomName.setText(nomination.name);
        nominator.setText(nomination.nominatorName);
        nomDate.setText(nomination.dateAdded);
        nomDesc.setText(nomination.description);
        nomFilters.setText(getStringFromArray(nomination.filters));
        nomCities.setText(getStringFromArray(nomination.cities));

        setSources();

        nominator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentB = new Intent(NominationDetailsActivity.this,ProfileDisplayActivity.class);
                intentB.putExtra("name", nomination.nominatorName);
                intentB.putExtra("email", nomination.nominator);
                intentB.putExtra("phone", nomination.nominatorPhone);
                intentB.putExtra("bio", nomination.nominatorBio);
                startActivity(intentB);
            }
        });
    }


    private void setSources(){
        View llSources =  findViewById(R.id.ll_sources);

        for(final String link: nomination.sources){
            TextView source = new TextView(this);
            source.setText(link);
            source.setTextColor(ContextCompat.getColor(this, R.color.colorTextLink));
            source.setPaintFlags(source.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

            LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llParams.setMargins(0, 0, 0, 15);
            source.setLayoutParams(llParams);

            source.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link));
                    startActivity(intent);
                }

            });

            ((LinearLayout) llSources).addView(source);
        }

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

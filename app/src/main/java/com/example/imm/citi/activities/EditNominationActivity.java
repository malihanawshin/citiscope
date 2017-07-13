package com.example.imm.citi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Nomination;

public class EditNominationActivity extends AppCompatActivity{
    Nomination nomination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nomination);

//        nomination = getIntent().getBundleExtra("nom").getParcelable("nomination");

        setTitle("Edit Nomination");

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }


}

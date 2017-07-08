package com.example.imm.citi.activities;

import android.content.Intent;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Nomination;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class PollActivity extends BottomBarActivity implements NominationListAdapter.NominationClickCallback{

    private RecyclerView nominationCardView;
    ArrayList<Nomination> nominations;
    NominationListAdapter adapter;


    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        setTitle("Poll");
        ButterKnife.bind(this);

        nominationCardView=(RecyclerView) findViewById(R.id.nominations_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        nominationCardView.setLayoutManager(manager);

        setRecycler();

    }

    private void setRecycler(){
        nominations = new ArrayList<>();
        nominations.add(new Nomination());
        nominations.add(new Nomination());
        nominations.add(new Nomination());

        adapter = new NominationListAdapter(this,nominations,this);
        nominationCardView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEditClick(Nomination nomination) {
        Intent intent = new Intent(this,EditNominationActivity.class);
        startActivity(intent);

    }
}

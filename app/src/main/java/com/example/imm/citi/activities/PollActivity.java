package com.example.imm.citi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Nomination;
import com.example.imm.citi.technicalClasses.Poll;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class PollActivity extends BottomBarActivity implements NominationListAdapter.NominationClickCallback{

    private RecyclerView nominationCardView;
    ArrayList<Nomination> nominations;
    NominationListAdapter adapter;
    Button toVote;
    private Poll poll;


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

        toVote = (Button) findViewById(R.id.btnToVote);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        nominationCardView.setLayoutManager(manager);

        setRecycler();

        setListener();

        poll = new Poll(this);
        poll.createPoll();
    }

    private void setListener() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnToAddNewNomination);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PollActivity.this,EditNominationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setRecycler(){
        nominations = new ArrayList<>();
//        nominations.add(new Nomination());
//        nominations.add(new Nomination());
//        nominations.add(new Nomination());

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
    public void onSeeDetailsClick(Nomination nomination) {
        Intent intent = new Intent(this,NominationDetailsActivity.class);
        startActivity(intent);

    }

    @Override
    public void onVoteClick(Nomination nomination) {
        //Todo vote count

    }

    public void showData(ArrayList<Nomination> noms) {
        nominations.clear();
        nominations.addAll(noms);
        adapter.notifyDataSetChanged();
    }
}

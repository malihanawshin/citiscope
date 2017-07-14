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
import android.widget.Toast;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Nomination;
import com.example.imm.citi.technicalClasses.Poll;
import com.example.imm.citi.technicalClasses.User;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class PollActivity extends BottomBarActivity implements NominationListAdapter.NominationClickCallback{

    private RecyclerView nominationCardView;
    ArrayList<Nomination> nominations;
    public NominationListAdapter adapter;
    Button toVote;
    public Poll poll;

    private int tempVote;
    private int tempPos;


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
                if(User.loggedIn){
                    if(!User.admin){
                        Intent intent = new Intent(PollActivity.this,EditNominationActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(parent, "You do not have permission", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(parent, "Log in First", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setRecycler(){
        nominations = new ArrayList<>();

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
        intent.putExtra("nomination", nomination);
        parent.startActivity(intent);

    }

    public void showData(ArrayList<Nomination> noms) {
        System.out.println("old " + nominations);
        System.out.println("new " + noms);
        if(noms!=nominations){
            nominations.clear();
            nominations.addAll(noms);
        }
        System.out.println("newest " + nominations);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onVoteClick(Nomination nomination, String action, int position) {
        tempPos = position;
        if(action.equals("Vote")){
            nomination.updateVote("vote.php",this);
            tempVote = 1;
        }
        else{
            nomination.updateVote("unvote.php",this);
            tempVote = -1;
        }
    }

    public void afterVoteUpdated(Nomination nomination) {

        for(Nomination nom: nominations){
            if(nom.name.equals(nomination.name)){
                nom.canVote = !nom.canVote;
                nom.voteCount += tempVote;
            }
        }

        nominations = poll.sortByVotes(nominations);
        adapter.notifyDataSetChanged();
    }
}

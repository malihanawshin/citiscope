package com.example.imm.citi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.imm.citi.R;
import com.example.imm.citi.agents.Agent;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity implements AgentListAdapter.AgentClickCallback{

    AgentListAdapter adapter;
    String service;
    FilterActivity f;
    private RecyclerView agentlistview;
    private TextView label;
    private Spinner spin_service;
    ArrayList<Agent> agents;
    int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);

        //Intent intent = getIntent();
        //agents = intent.getBundleExtra("agent").getParcelableArrayList("agents");
        setTitle("Bookmarks");

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agentlistview = (RecyclerView) findViewById(R.id.agent_recycler);

        spin_service = (Spinner) findViewById(R.id.spinner_service);
        spin_service.setVisibility(View.VISIBLE);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        agentlistview.setLayoutManager(manager);

        setRecycler();

    }

    private void setRecycler() {
        agents = new ArrayList<>();
        agents.add(new Agent());
        agents.add(new Agent());
        agents.add(new Agent());
        adapter = new AgentListAdapter(this,agents,this, flag);
        agentlistview.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCompareClick(Agent a) {

    }

    @Override
    public void onBookmarkClick(Agent a) {

    }
}

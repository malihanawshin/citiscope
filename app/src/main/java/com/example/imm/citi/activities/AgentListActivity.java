package com.example.imm.citi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.imm.citi.R;
import com.example.imm.citi.agents.Agent;

import java.util.ArrayList;

public class AgentListActivity extends AppCompatActivity implements AgentListAdapter.AgentClickCallback{

    AgentListAdapter adapter;
    String service;
    FilterActivity f;
    private RecyclerView agentlistview;
    ArrayList<Agent> agents;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);

        Intent intent = getIntent();
        service = intent.getStringExtra("servicename");
        setTitle(service);
        agents = intent.getBundleExtra("agent").getParcelableArrayList("agents");

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agentlistview = (RecyclerView) findViewById(R.id.agent_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        agentlistview.setLayoutManager(manager);

        setRecycler();
    }

    private void setRecycler() {
        adapter = new AgentListAdapter(this,agents, this, flag);
        agentlistview.setAdapter(adapter);
        agentlistview.setItemViewCacheSize(999);
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

    @Override
    public void onEditClick(Agent a) {

    }
}

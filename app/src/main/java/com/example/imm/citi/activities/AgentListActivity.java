package com.example.imm.citi.activities;

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
    ArrayList<Agent> agents = new ArrayList<>();

    //agents.add();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);

        //Intent intent = new Intent(AgentListActivity.this, FilterActivity.class);
        service = getIntent().getStringExtra("servicename");
        setTitle(service);

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agentlistview = (RecyclerView) findViewById(R.id.agent_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        agentlistview.setLayoutManager(manager);

        setRecycler();
    }

    private void setRecycler() {
       adapter = new AgentListAdapter(this,agents, this);
       agentlistview.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Agent a) {
        //Intent intent = new Intent(this, FilterActivity.class);
        //intent.putExtra("servicename",a.getName());
        //startActivity(intent);
    }
}

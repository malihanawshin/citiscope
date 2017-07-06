package com.example.imm.citi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.imm.citi.R;
import com.example.imm.citi.agents.Agent;

import java.util.ArrayList;

public class AgentProfileActivity extends AppCompatActivity implements AgentListAdapter.AgentClickCallback{

    AgentListAdapter adapter;
    String service;
    //FilterActivity f;
    private RecyclerView agentlistview;
    private TextView label;
    Button addAgent;
    ArrayList<Agent> agents;
    int flag = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);

        setTitle("Agent Profile");

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agentlistview = (RecyclerView) findViewById(R.id.agent_recycler);

        addAgent = (Button) findViewById(R.id.btnToAddAgent);
        addAgent.setVisibility(View.VISIBLE);
        addAgent.setOnClickListener(clickListener);

        Intent intent = getIntent();
        service = intent.getStringExtra("servicename");

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

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btnToAddAgent:
                    //showEditPage();
                    break;
            }
        }
    };

                @Override
    public void onCompareClick(Agent a) {

    }

    @Override
    public void onBookmarkClick(Agent a) {

    }

    @Override
    public void onEditClick(Agent a) {
        Intent intent = new Intent(this, EditAgentInfoActivity.class);
        //intent.putExtra("servicename",service);
        intent.putExtra("servicename","Tuition");


        startActivity(intent);
    }
}

package com.example.imm.citi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imm.citi.R;
import com.example.imm.citi.agents.Agent;
import com.example.imm.citi.technicalClasses.Database;
import com.example.imm.citi.technicalClasses.RetrievalData;
import com.example.imm.citi.technicalClasses.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AgentProfileActivity extends AppCompatActivity implements AgentListAdapter.AgentClickCallback{

    AgentListAdapter adapter;
    private RecyclerView agentlistview;
    private TextView label;
    Button addAgent;
    ArrayList<Agent> agents;
    int flag = 2;

    Activity parent = this;
    private final String SERVICEFILE = "allServiceNames.php";
    private Spinner spnCity;
    String chosenService = "Apartment Renting";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);

        setTitle("Agent Profile");

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agentlistview = (RecyclerView) findViewById(R.id.agent_recycler);

        spnCity = (Spinner) findViewById(R.id.spinner_service);

        addAgent = (Button) findViewById(R.id.btnToAddAgent);
        addAgent.setVisibility(View.VISIBLE);
        addAgent.setOnClickListener(clickListener);


        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        agentlistview.setLayoutManager(manager);

        setRecycler();
        fillSpinner();
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
                    showEditPage();
                    break;
            }
        }
    };

    private void showEditPage() {
        Intent intent = new Intent(this, EditAgentInfoActivity.class);
        intent.putExtra("servicename",chosenService);
        startActivity(intent);
    }

    @Override
    public void onCompareClick(Agent a) {

    }

    @Override
    public void onBookmarkClick(Agent a) {

    }

    @Override
    public void onEditClick(Agent a) {
        showEditPage();
    }














    private void fillSpinner() {
        ArrayList<String> keys = new ArrayList<>(), vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, SERVICEFILE, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    ArrayAdapter<String> spnAdapter;
                    ArrayList<String> serviceNames = new ArrayList<String>();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");

                    if(result.length()!=0){
                        System.out.println(result + " X " + result.length());
                        for(int i=0; i<result.length(); i++) {
                            try {
                                JSONObject res = result.getJSONObject(i);
                                serviceNames.add(res.getString("ServiceName"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        spnAdapter = new ArrayAdapter<String>(parent, android.R.layout.simple_spinner_item, serviceNames);
                        spnCity.setAdapter(spnAdapter);
                    }

                    setSpinnerListener();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setSpinnerListener() {
        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View view, int pos, long l) {
                String selected = spinner.getItemAtPosition(pos).toString();
                chosenService = selected;
                Toast.makeText(parent, selected, Toast.LENGTH_SHORT).show();
                //getAgents(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}

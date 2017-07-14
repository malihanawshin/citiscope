package com.example.imm.citi.agents;

import android.app.Activity;
import android.support.v4.util.Pair;

import com.example.imm.citi.technicalClasses.Database;
import com.example.imm.citi.technicalClasses.RetrievalData;
import com.example.imm.citi.technicalClasses.Service;
import com.example.imm.citi.technicalClasses.User;
import com.example.imm.citi.technicalClasses.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/6/2017.
 */

public class FactoryBloodDonation extends FactoryAgent {
    final String BLDFILE = "agentBloodDonationFetcher.php";
    ArrayList<String> areas, mediums, classes, subjects;

    public FactoryBloodDonation(Service serv, Activity act, ArrayList<Agent> agents, String type){
        super(serv,act, agents, type);
    }

    public void fetchAgents(){
        showProgress();

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, BLDFILE, parent), false, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");

                    if (result.length() != 0) {
                        //System.out.println(result + " X " + result.length());
                        for (int i = 0; i < result.length(); i++) {
                            try {
                                JSONObject obj = result.getJSONObject(i);
                                AgentBloodDonation tempAg = new AgentBloodDonation(obj.getString("Name"), obj.getString("Email"), obj.getString("Phone"), obj.getString("Location"),
                                        obj.getString("District"), obj.getString("BloodType"), obj.getString("SmokingHabit"), Integer.parseInt(obj.getString("DonationsDone")),
                                        Integer.parseInt(obj.getString("LastDonated")));
                                tempAg.setID(obj.getString("ID"));

                                agents.add(tempAg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if(User.loggedIn) {
                        if(actionType.equals("profile")){
                            checkAgents();
                        }
                        else {
                            checkBookmarks(agents);
                        }
                    }
                    else
                        finishFetch();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void finishFetch() {
        for(Agent ag: agents){
            if(ag instanceof LocalAgent) {
                AgentBloodDonation agBld = (AgentBloodDonation) ag;
                System.out.println("DONOR: " + agBld.name + " " + agBld.address + " " + agBld.bloodType + " " + agBld.daysSinceLastDonated);
            }
        }
        super.finishFetch();
    }

    @Override
    protected String getServiceName() {
        return "Blood Donation";
    }

    @Override
    protected Boolean checkUp(Agent temp, ArrayList<Pair<String, String>> chosenOptions) {
        AgentBloodDonation agBld = (AgentBloodDonation) temp;

        for(Pair pair : chosenOptions)
        {
            if(pair.first.equals("District"))
            {
                if(agBld.district.equals(pair.second)==false) return false;
            }
            else if(pair.first.equals("Blood Group"))
            {
                if(agBld.bloodType.equals(pair.second)==false) return false;
            }
        }
        return true;
    }
}

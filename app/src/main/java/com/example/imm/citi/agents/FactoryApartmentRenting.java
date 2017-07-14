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

public class FactoryApartmentRenting extends FactoryAgent {
    final String APTFILE = "agentApartmentRentingFetcher.php";
    ArrayList<String> areas, mediums, classes, subjects;

    public FactoryApartmentRenting(Service serv, Activity act, ArrayList<Agent> agents, String type){
        super(serv,act, agents, type);
    }

    public void fetchAgents(){
        showProgress();

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, APTFILE, parent), false, new VolleyCallback() {
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
                                AgentApartmentRenting tempAg = new AgentApartmentRenting(obj.getString("Name"), obj.getString("Email"), obj.getString("Phone"), obj.getString("Location"),
                                        obj.getString("District"), obj.getString("Area"), obj.getString("PropertyType"), Long.parseLong(obj.getString("Price")),
                                        Long.parseLong(obj.getString("Size")), Integer.parseInt(obj.getString("Floor")), Integer.parseInt(obj.getString("RoomNo")));
                                tempAg.setID(obj.getString("ID"));

                                System.out.println("LOOOOOONG" + obj.getString("Size"));

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
                AgentApartmentRenting agApt = (AgentApartmentRenting) ag;
                System.out.println("TUTOR: " + agApt.name + " " + agApt.address + " " + agApt.propertyType + " " + agApt.roomNo);
            }
        }
        super.finishFetch();
    }

    @Override
    protected String getServiceName() {
        return "Apartment Renting";
    }

    @Override
    protected Boolean checkUp(Agent temp, ArrayList<Pair<String, String>> chosenOptions) {
        AgentApartmentRenting agApt = (AgentApartmentRenting) temp;

        long min=-1, max=-1;
        Boolean minH=false,maxH=false;

        for(Pair pair : chosenOptions)
        {
            if(pair.first.equals("District"))
            {
                if(agApt.district.equals(pair.second)==false) return false;
            }
            else if(pair.first.equals("Area"))
            {
                if(agApt.area.equals(pair.second)==false) return false;
            }
            else if(pair.first.equals("Property Type"))
            {
                if(agApt.propertyType.equals(pair.second)==false) return false;
            }
            else if(pair.first.equals("Maximum Price"))
            {
                max=Long.parseLong((String)pair.second);
                maxH=true;
            }
            else if(pair.first.equals("Minimum Price"))
            {
                min=Long.parseLong((String)pair.second);
                minH=true;
            }
            if(minH==true && maxH==true)
            {
                if(min >= max) return true;
                else if(!(min <= agApt.price && max >= agApt.price)) return false;
            }
        }
        return true;
    }
}

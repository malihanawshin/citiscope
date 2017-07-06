package com.example.imm.citi.agents;

import android.app.Activity;

import com.example.imm.citi.technicalClasses.Database;
import com.example.imm.citi.technicalClasses.RetrievalData;
import com.example.imm.citi.technicalClasses.Service;
import com.example.imm.citi.technicalClasses.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/6/2017.
 */

public class FactoryDoctor extends FactoryAgent {
    final String DOCFILE = "agentDoctorFetcher.php", DOCADDRESSFILE = "doctorAddresses.php", DOCDEGFILE = "doctorDegs.php";

    ArrayList<String> areas, mediums, classes, subjects;

    public FactoryDoctor(Service serv, Activity act){
        super(serv,act);
    }

    public void fetchAgents(){
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, DOCFILE, parent), true, new VolleyCallback() {
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
                                AgentDoctor tempAg = new AgentDoctor(obj.getString("Name"), obj.getString("Email"), obj.getString("Phone"), obj.getString("Location"),
                                        obj.getString("District"), obj.getString("Specialty"), new ArrayList(), new ArrayList(), obj.getString("HospitalName"),
                                        Integer.parseInt(obj.getString("YearsInPractice")));
                                tempAg.setID(obj.getString("ID"));

                                System.out.println(tempAg.id);

                                agents.add(tempAg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    getAddresses();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getAddresses() {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, DOCADDRESSFILE, parent), true, new VolleyCallback() {
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
                                getDoctorAgentByID(obj.getString("ID")).addAddress(obj.getString("Address"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    getDegrees();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getDegrees() {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, DOCDEGFILE, parent), true, new VolleyCallback() {
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
                                getDoctorAgentByID(obj.getString("ID")).addDegree(obj.getString("Degree"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    finishFetch();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private AgentDoctor getDoctorAgentByID(String id) {
        for(Agent ag: agents){
            System.out.print(ag.id + "X" + id);
            if(ag.id.equals(id))
                return (AgentDoctor)ag;
        }
        System.out.println();
        return null;
    }


    @Override
    public void finishFetch() {
        for(Agent ag: agents){
            AgentDoctor agDoc = (AgentDoctor) ag;
            System.out.println("TUTOR: " + agDoc.name + " " + agDoc.address + " " + agDoc.specialty + " " + agDoc.hospitalName);
        }
    }
}

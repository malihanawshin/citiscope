package com.example.imm.citi.agents;

import android.app.Activity;

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

public class FactoryTuition extends FactoryAgent {
    final String TUIFILE = "agentTuitionFetcher.php", TUIAREAFILE = "tuitionAreas.php", TUIMEDFLE = "tuitionMeds.php", TUICLSFILE = "tuitionCls.php", TUISUBFILE = "tuitionSubs.php";

    ArrayList<String> areas, mediums, classes, subjects;

    public FactoryTuition(Service serv, Activity act, ArrayList<Agent> agents, String type){
        super(serv,act, agents, type);
    }

    public void fetchAgents(){
        showProgress();

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, TUIFILE, parent), false, new VolleyCallback() {
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
                                AgentTuition tempAg = new AgentTuition(obj.getString("Name"), obj.getString("Email"), obj.getString("Phone"), obj.getString("Location"),
                                        obj.getString("District"), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), obj.getString("School"), obj.getString("College"),
                                        obj.getString("University"), obj.getString("Occupation"), obj.getString("ProfileLink"), Integer.parseInt(obj.getString("TuitionsDone")));
                                tempAg.setID(obj.getString("ID"));

                                System.out.println(tempAg.id);

                                agents.add(tempAg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    getAreas();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getAreas() {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, TUIAREAFILE, parent), false, new VolleyCallback() {
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
                                getTuitionAgent(obj.getString("ID")).addArea(obj.getString("Area"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    getMediums();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getMediums() {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, TUIMEDFLE, parent), false, new VolleyCallback() {
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
                                getTuitionAgent(obj.getString("ID")).addMedium(obj.getString("Medium"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    getClasses();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getClasses() {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, TUICLSFILE, parent), false, new VolleyCallback() {
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
                                getTuitionAgent(obj.getString("ID")).addClass(obj.getString("Class"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    getSubjects();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getSubjects() {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, TUISUBFILE, parent), false, new VolleyCallback() {
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
                                getTuitionAgent(obj.getString("ID")).addSubject(obj.getString("Subject"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if(User.loggedIn)
                        checkBookmarks(agents);
                    else
                        finishFetch();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private AgentTuition getTuitionAgent(String id) {
        for(Agent ag: agents){
            System.out.print(ag.id + "X" + id);
            if(ag.id.equals(id))
                return (AgentTuition)ag;
        }
        System.out.println();
        return null;
    }


    @Override
    public void finishFetch() {
        for(Agent ag: agents) {
            if (ag instanceof LocalAgent) {
                AgentTuition agTui = (AgentTuition) ag;
                System.out.println("TUTOR: " + agTui.name + " " + agTui.address + " " + agTui.school + " " + agTui.tuitionsDone);
            }
        }
        super.finishFetch();
    }

    @Override
    protected String getServiceName() {
        return "Tuition";
    }
}

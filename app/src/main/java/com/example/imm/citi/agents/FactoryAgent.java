package com.example.imm.citi.agents;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.util.Pair;

import com.example.imm.citi.activities.AgentProfileActivity;
import com.example.imm.citi.activities.BookmarkActivity;
import com.example.imm.citi.technicalClasses.Database;
import com.example.imm.citi.technicalClasses.RetrievalData;
import com.example.imm.citi.technicalClasses.Service;
import com.example.imm.citi.technicalClasses.User;
import com.example.imm.citi.technicalClasses.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class FactoryAgent {
	Service service;
	Activity parent;
    ArrayList<Agent> agents = new ArrayList<>(), remoteAgents;
    ProgressDialog loading;

    final String CHKBOOKMARKFILE = "checkBookmarks.php", REMOTEAGFILE = "agentRemoteFactory.php", CHKAGPROFFILE = "checkAgentProfile.php";
    ArrayList<String> bookmarkedIDs;

    protected String actionType;
    private ArrayList<Agent> createdAgents;


    public FactoryAgent(Service serv, Activity act, ArrayList<Agent> agents, String type){
        service = serv;
        parent = act;
		remoteAgents = agents;
        actionType = type;
    }

    public abstract void fetchAgents();

    public void finishFetch(){
        if(actionType.equals("bookmark")){
            BookmarkActivity bkParent = (BookmarkActivity) parent;
            loading.dismiss();
            bkParent.showAgents(agents);
        }
        else if(actionType.equals("search")){
            ArrayList<Agent> unsortedLocalAgents = search(service.getChosenOptions(), agents);

            //ArrayList<LocalAgent> sortedLocalAgents = factory.sort(unsortedAgents);

            ArrayList<ArrayList<Agent>> unsortedFinalAgents = new ArrayList<>();
            unsortedFinalAgents.add(unsortedLocalAgents);
            unsortedFinalAgents.add(remoteAgents);

            agents = service.sortResult(unsortedFinalAgents);

            loading.dismiss();
            service.showResult(agents);
        }
        else if(actionType.equals("profile")){
            AgentProfileActivity agProf = (AgentProfileActivity) parent;
            loading.dismiss();
            agProf.showAgents(agents);
        }
    }

    protected void checkBookmarks(ArrayList<Agent> agents) {
        bookmarkedIDs = new ArrayList<>();

        ArrayList<String> keys = new ArrayList<>();
        keys.add("email");
        ArrayList<String> vals = new ArrayList<>();
        vals.add(User.Email);

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, CHKBOOKMARKFILE, parent), false, new VolleyCallback() {
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
                                bookmarkedIDs.add(obj.getString("ID"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    setBookmarks();
                    if(actionType.equals("bookmark"))
                        getRemoteBookmarks();
                    else
                        finishFetch();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void setBookmarks() {
        ArrayList<Agent> bookmarkedAgents = new ArrayList<>();

        for(Agent ag: agents){
            if(ag instanceof LocalAgent){
                LocalAgent locAg = (LocalAgent)ag;

                if(bookmarkedIDs.contains(locAg.id)){
                    locAg.setBookmarked();
                    bookmarkedAgents.add(locAg);
                }

                System.out.println("eta local " + locAg.name);
            }
        }

        if(actionType.equals("bookmark")){
            agents = bookmarkedAgents;
        }
    }


    private void getRemoteBookmarks() {
        ArrayList<String> keys = new ArrayList<>();
        keys.add("service");
        ArrayList<String> vals = new ArrayList<>();
        vals.add(getServiceName());

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, REMOTEAGFILE, parent), false, new VolleyCallback() {
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
                                String id = obj.getString("ID");
                                if(bookmarkedIDs.contains(id)){
                                    RemoteAgent remAg = new RemoteAgent(getServiceName());
                                    remAg.setAttr(obj.getString("Name"), obj.getString("Phone"), obj.getString("Link"), obj.getString("Location"), obj.getString("Email"));
                                    agents.add(remAg);
                                }
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



    protected void checkAgents() {
        createdAgents = new ArrayList<>();

        ArrayList<String> keys = new ArrayList<>();
        keys.add("email");
        ArrayList<String> vals = new ArrayList<>();
        vals.add(User.Email);

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, CHKAGPROFFILE, parent), false, new VolleyCallback() {
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
                                String id = obj.getString("ID");
                                Agent tempAg = findAgentById(id);
                                if(tempAg!=null)
                                    createdAgents.add(tempAg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    agents = createdAgents;
                    finishFetch();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Agent findAgentById(String id) {
        for(Agent ag: agents){
            System.out.println(ag.id + "<<>>" + id);
            if(ag.id.equals(id))
                return ag;
        }

        return null;
    }


    protected abstract String getServiceName();



    protected ArrayList<Agent> search(ArrayList<Pair<String, String>> chosenOptions, ArrayList<Agent> rawAgents) {
        ArrayList <Agent> unsorted = new ArrayList<Agent>();
        for(Agent temp : rawAgents)
        {
            Boolean passed = checkUp(temp, chosenOptions);
            if(passed) unsorted.add(temp);
        }
        return unsorted;
    }

    protected abstract Boolean checkUp(Agent temp, ArrayList<Pair<String, String>> chosenOptions);

    ArrayList<LocalAgent> sort(ArrayList<LocalAgent> unsortedAgents){
		
		return null;
	}





    protected void showProgress(){
        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String agentType="Local";
                if(actionType.equals("bookmark")){
                    agentType="Bookmarked";
                }
                loading = ProgressDialog.show(parent,"Please wait...","Fetching " + agentType + " Agents",false,false);
            }
        });
    }
}

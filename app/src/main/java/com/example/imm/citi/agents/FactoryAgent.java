package com.example.imm.citi.agents;

import android.app.Activity;
import android.app.ProgressDialog;

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

    final String CHKBOOKMARKFILE = "checkBookmarks.php";
    ArrayList<String> bookmarkedIDs;


    public FactoryAgent(Service serv, Activity act, ArrayList<Agent> agents){
        service = serv;
        parent = act;
		remoteAgents = agents;
    }

    public abstract void fetchAgents();

    public void finishFetch(){
        ArrayList<ArrayList<Agent>> unsortedAgents = new ArrayList<>();
        unsortedAgents.add(agents);
        unsortedAgents.add(remoteAgents);

        agents = service.sortResult(unsortedAgents);

        loading.dismiss();
        service.showResult(agents);
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
                    finishFetch();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setBookmarks() {
        for(Agent ag: agents){
            if(ag instanceof LocalAgent){
                LocalAgent locAg = (LocalAgent)ag;

                if(bookmarkedIDs.contains(locAg.id)){
                    locAg.setBookmarked();
                }

                System.out.println("eta local " + locAg.name);
            }
        }
    }

    ArrayList<LocalAgent> search(ArrayList<android.support.v4.util.Pair<String, String>> chosenOptions){
		
		return null;
	}
	
	ArrayList<LocalAgent> sort(ArrayList<LocalAgent> unsortedAgents){
		
		return null;
	}





    protected void showProgress(){
        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading = ProgressDialog.show(parent,"Please wait...","Fetching Local Agents",false,false);
            }
        });
    }
}

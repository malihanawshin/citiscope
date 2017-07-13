package com.example.imm.citi.agents;

import android.widget.Toast;

import com.example.imm.citi.activities.EditAgentInfoActivity;
import com.example.imm.citi.technicalClasses.Database;
import com.example.imm.citi.technicalClasses.RetrievalData;
import com.example.imm.citi.technicalClasses.UserAgentInput;
import com.example.imm.citi.technicalClasses.VolleyCallback;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/13/2017.
 */

public class CreatorAgent {
    EditAgentInfoActivity parent;
    UserAgentInput userInput;
    protected String ADDFILE, UPDATEFILE;

    ArrayList<String> keys, vals;

    public CreatorAgent(EditAgentInfoActivity act){
        parent = act;
    }

    public void addAgent(UserAgentInput input){
        userInput = input;

        setParams();

        Database db = new Database();
        db.update(new RetrievalData(keys, vals, ADDFILE, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("false"))
                    Toast.makeText(parent,"Sorry something went wrong. Try again.",Toast.LENGTH_LONG).show();
                else{
                    parent.afterSave();
                }
            }
        });
    }

    public void updateAgent(UserAgentInput input) {
        userInput = input;

        setParams();

        Database db = new Database();
        db.update(new RetrievalData(keys, vals, UPDATEFILE, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("false"))
                    Toast.makeText(parent,"Sorry something went wrong. Try again.",Toast.LENGTH_LONG).show();
                else{
                    parent.afterSave();
                }
            }
        });
    }

    public void setParams(){
        keys = new ArrayList<>();
        vals = new ArrayList<>();

        keys.add("id");
        keys.add("service");
        keys.add("ownerEmail");

        keys.add("name");
        keys.add("email");
        keys.add("phone");
        keys.add("location");
    }

    protected ArrayList<String> getKeyForArray(String key, int length){
        ArrayList<String> arrKeys = new ArrayList<>();

        for(int i=1; i<=length; i++){
            arrKeys.add(key+i);
        }

        return arrKeys;
    }
}

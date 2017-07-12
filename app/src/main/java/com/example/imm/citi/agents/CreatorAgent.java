package com.example.imm.citi.agents;

import com.example.imm.citi.activities.EditAgentInfoActivity;
import com.example.imm.citi.technicalClasses.UserAgentInput;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/13/2017.
 */

public abstract class CreatorAgent {
    EditAgentInfoActivity parent;
    UserAgentInput userInput;

    ArrayList<String> keys, vals;

    public CreatorAgent(EditAgentInfoActivity act){
        parent = act;
    }

    public abstract void addAgent(UserAgentInput input);

    public abstract void updateAgent(UserAgentInput input);

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

        vals = userInput.getBloodDonationInput(vals);
    }
}

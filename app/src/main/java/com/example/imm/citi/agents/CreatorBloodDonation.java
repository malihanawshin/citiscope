package com.example.imm.citi.agents;

import android.widget.Toast;

import com.example.imm.citi.activities.EditAgentInfoActivity;
import com.example.imm.citi.technicalClasses.Database;
import com.example.imm.citi.technicalClasses.RetrievalData;
import com.example.imm.citi.technicalClasses.UserAgentInput;
import com.example.imm.citi.technicalClasses.VolleyCallback;

/**
 * Created by Sujoy on 7/13/2017.
 */

public class CreatorBloodDonation extends CreatorAgent {
    private final String ADDBLDFILE = "addAgentBloodDonation.php", UPDATEBLDFILE = "updateAgentBloodDonation.php";

    public CreatorBloodDonation(EditAgentInfoActivity act) {
        super(act);
    }

    @Override
    public void addAgent(UserAgentInput input) {
        userInput = input;

        setParams();

        Database db = new Database();
        db.update(new RetrievalData(keys, vals, ADDBLDFILE, parent), true, new VolleyCallback() {
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

    @Override
    public void updateAgent(UserAgentInput input) {
        userInput = input;

        setParams();

        Database db = new Database();
        db.update(new RetrievalData(keys, vals, UPDATEBLDFILE, parent), true, new VolleyCallback() {
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

    @Override
    public void setParams() {
        super.setParams();

        keys.add("district");
        keys.add("type");
        keys.add("smoke");
        keys.add("donations");
        keys.add("lastDonated");
    }
}

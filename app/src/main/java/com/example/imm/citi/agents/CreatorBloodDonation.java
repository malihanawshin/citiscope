package com.example.imm.citi.agents;

import com.example.imm.citi.activities.EditAgentInfoActivity;

/**
 * Created by Sujoy on 7/13/2017.
 */

public class CreatorBloodDonation extends CreatorAgent {
    private final String ADDBLDFILE = "addAgentBloodDonation.php", UPDATEBLDFILE = "updateAgentBloodDonation.php";

    public CreatorBloodDonation(EditAgentInfoActivity act) {
        super(act);
        ADDFILE = ADDBLDFILE;
        UPDATEFILE = UPDATEBLDFILE;
    }


    @Override
    public void setParams() {
        super.setParams();

        keys.add("district");
        keys.add("type");
        keys.add("smoke");
        keys.add("donations");
        keys.add("lastDonated");


        vals = userInput.getBloodDonationInput(vals);
    }
}

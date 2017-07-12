package com.example.imm.citi.agents;

import com.example.imm.citi.activities.EditAgentInfoActivity;

/**
 * Created by Sujoy on 7/13/2017.
 */

public class CreatorApartmentRenting extends CreatorAgent {
    private final String ADDAPTFILE = "addAgentApartmentRenting.php", UPDATEAPTFILE = "updateAgentApartmentRenting.php";

    public CreatorApartmentRenting(EditAgentInfoActivity act) {
        super(act);
        ADDFILE = ADDAPTFILE;
        UPDATEFILE = UPDATEAPTFILE;
    }

    @Override
    public void setParams() {
        super.setParams();

        keys.add("district");
        keys.add("area");
        keys.add("property");
        keys.add("price");
        keys.add("size");
        keys.add("floor");
        keys.add("roomNo");

        vals = userInput.getApartmentRentingInput(vals);
    }
}

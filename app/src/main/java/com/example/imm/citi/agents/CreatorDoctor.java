package com.example.imm.citi.agents;

import com.example.imm.citi.activities.EditAgentInfoActivity;

/**
 * Created by Sujoy on 7/13/2017.
 */

public class CreatorDoctor extends CreatorAgent {
    private final String ADDDOCFILE = "addAgentDoctor.php", UPDATEDOCFILE = "updateAgentDoctor.php";

    public CreatorDoctor(EditAgentInfoActivity act) {
        super(act);
        ADDFILE = ADDDOCFILE;
        UPDATEFILE = UPDATEDOCFILE;
    }


    @Override
    public void setParams() {
        super.setParams();

        keys.add("district");
        keys.add("specialty");
        keys.add("hospital");
        keys.add("practiceYears");
        keys.add("addressNo");
        keys.addAll(getKeyForArray("address", userInput.docAddresses.size()));
        keys.add("degreeNo");
        keys.addAll(getKeyForArray("degree", userInput.docDegrees.size()));


        vals = userInput.getDoctorInput(vals);
    }
}

package com.example.imm.citi.agents;

import com.example.imm.citi.activities.EditAgentInfoActivity;

/**
 * Created by Sujoy on 7/13/2017.
 */

public class CreatorTuition extends CreatorAgent {
    private final String ADDTUIFILE = "addAgentTuition.php", UPDATETUIFILE = "updateAgentTuition.php";

    public CreatorTuition(EditAgentInfoActivity act) {
        super(act);
        ADDFILE = ADDTUIFILE;
        UPDATEFILE = UPDATETUIFILE;
    }


    @Override
    public void setParams() {
        super.setParams();

        keys.add("district");
        keys.add("areaNo");
        keys.addAll(getKeyForArray("area", userInput.tuiAreas.size()));
        keys.add("mediumNo");
        keys.addAll(getKeyForArray("medium", userInput.tuiMediums.size()));
        keys.add("classNo");
        keys.addAll(getKeyForArray("class", userInput.tuiClasses.size()));
        keys.add("subjectNo");
        keys.addAll(getKeyForArray("subject", userInput.tuiSubjects.size()));
        keys.add("school");
        keys.add("college");
        keys.add("university");
        keys.add("occupation");
        keys.add("tuiDone");
        keys.add("profile");


        vals = userInput.getTuitionInput(vals);
    }

}

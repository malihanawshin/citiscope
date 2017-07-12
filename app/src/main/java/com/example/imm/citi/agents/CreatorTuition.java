package com.example.imm.citi.agents;

import com.example.imm.citi.activities.EditAgentInfoActivity;
import com.example.imm.citi.technicalClasses.UserAgentInput;

/**
 * Created by Sujoy on 7/13/2017.
 */

public class CreatorTuition extends CreatorAgent {
    private final String ADDTUIFILE = "addAgentTuition.php", UPDATETUIFILE = "updateAgentTuition.php";

    public CreatorTuition(EditAgentInfoActivity act) {
        super(act);
    }

    @Override
    public void addAgent(UserAgentInput input) {

    }

    @Override
    public void updateAgent(UserAgentInput input) {

    }
}

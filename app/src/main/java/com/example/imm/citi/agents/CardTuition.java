package com.example.imm.citi.agents;

import android.widget.TextView;

import com.example.imm.citi.activities.AgentListAdapter;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/7/2017.
 */

public class CardTuition extends CardAgent {
    @Override
    public void setAttributes(AgentListAdapter.AgentViewHolder holder, Agent agent) {
        ArrayList<TextView> attInfos = holder.attributeInfos, attTexts = holder.attributeTexts;
        AgentTuition agTui = (AgentTuition) agent;

        attTexts.get(0).setText("Occupation");
        attInfos.get(0).setText(agTui.occupation);

        attTexts.get(1).setText("Tuitions Done");
        attInfos.get(1).setText(agTui.tuitionsDone+"");

        attTexts.get(2).setText("Profile");
        attInfos.get(2).setText(agTui.profileLink);

        attTexts.get(3).setText("");
        attInfos.get(3).setText("");

        attTexts.get(4).setText("");
        attInfos.get(4).setText("");
    }
}

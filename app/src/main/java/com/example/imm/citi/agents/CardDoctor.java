package com.example.imm.citi.agents;

import android.widget.TextView;

import com.example.imm.citi.activities.AgentListAdapter;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/7/2017.
 */

public class CardDoctor extends CardAgent {
    @Override
    public void setAttributes(AgentListAdapter.AgentViewHolder holder, Agent agent) {
        ArrayList<TextView> attInfos = holder.attributeInfos, attTexts = holder.attributeTexts;
        AgentDoctor agDoc = (AgentDoctor) agent;

        attTexts.get(0).setText("Specialty");
        attInfos.get(0).setText(agDoc.specialty);

        attTexts.get(1).setText("Chambers");
        attInfos.get(1).setText(agDoc.convertArrayStrToStr(agDoc.addresses));

        attTexts.get(2).setText("Degrees");
        attInfos.get(2).setText(agDoc.convertArrayStrToStr(agDoc.degrees));

        attTexts.get(3).setText("Hospital");
        attInfos.get(3).setText(agDoc.hospitalName);

        attTexts.get(4).setText("Years in Practice");
        attInfos.get(4).setText(agDoc.yearsInPractice+"");
    }
}

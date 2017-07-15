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

        attTexts.get(0).setText("City");
        attInfos.get(0).setText(agDoc.district);

        attTexts.get(1).setText("Specialty");
        attInfos.get(1).setText(agDoc.specialty);

        attTexts.get(2).setText("Chambers");
        attInfos.get(2).setText(agDoc.convertArrayStrToStr(agDoc.addresses));

        attTexts.get(3).setText("Degrees");
        attInfos.get(3).setText(agDoc.convertArrayStrToStr(agDoc.degrees));

        attTexts.get(4).setText("Hospital");
        attInfos.get(4).setText(agDoc.hospitalName);

        attTexts.get(5).setText("Years in Practice");
        attInfos.get(5).setText(agDoc.yearsInPractice+"");

        attTexts.get(6).setText("");
        attInfos.get(6).setText("");

        attTexts.get(7).setText("");
        attInfos.get(7).setText("");

        attTexts.get(8).setText("");
        attInfos.get(8).setText("");

        attTexts.get(9).setText("");
        attInfos.get(9).setText("");

        attTexts.get(10).setText("");
        attInfos.get(10).setText("");
    }
}

package com.example.imm.citi.agents;

import android.widget.TextView;

import com.example.imm.citi.activities.AgentListAdapter;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/7/2017.
 */

public class CardApartmentRenting extends CardAgent {
    @Override
    public void setAttributes(AgentListAdapter.AgentViewHolder holder, Agent agent) {
        ArrayList<TextView> attInfos = holder.attributeInfos, attTexts = holder.attributeTexts;
        AgentApartmentRenting agApt = (AgentApartmentRenting) agent;

        attTexts.get(0).setText("Price");
        attInfos.get(0).setText(agApt.price+"");

        attTexts.get(1).setText("Size");
        attInfos.get(1).setText(agApt.size+" sq m");

        attTexts.get(2).setText("Floor");
        attInfos.get(2).setText(agApt.floor+"");

        attTexts.get(3).setText("Room Number");
        attInfos.get(3).setText(agApt.roomNo+"");

        attTexts.get(4).setText("");
        attInfos.get(4).setText("");
    }
}

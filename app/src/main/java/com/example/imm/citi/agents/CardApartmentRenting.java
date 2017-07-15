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

        attTexts.get(0).setText("City");
        attInfos.get(0).setText(agApt.district);

        attTexts.get(1).setText("Area");
        attInfos.get(1).setText(agApt.area);

        attTexts.get(2).setText("Property Type");
        attInfos.get(2).setText(agApt.propertyType);

        attTexts.get(3).setText("Price");
        attInfos.get(3).setText(agApt.price+"");

        attTexts.get(4).setText("Size");
        attInfos.get(4).setText(agApt.size+" sq m");

        attTexts.get(5).setText("Floor");
        attInfos.get(5).setText(agApt.floor+"");

        attTexts.get(6).setText("Room Number");
        attInfos.get(6).setText(agApt.roomNo+"");

        attTexts.get(7).setText("");
        attInfos.get(7).setText("");

        attTexts.get(8).setText("");
        attInfos.get(8).setText("");

        attTexts.get(9).setText("");
        attInfos.get(9).setText("");

        attTexts.get(9).setText("");
        attInfos.get(9).setText("");
    }
}

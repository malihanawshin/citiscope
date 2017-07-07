package com.example.imm.citi.agents;

import android.widget.TextView;

import com.example.imm.citi.activities.AgentListAdapter;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/7/2017.
 */

public class CardBloodDonation extends CardAgent {
    @Override
    public void setAttributes(AgentListAdapter.AgentViewHolder holder, Agent agent) {
        ArrayList<TextView> attInfos = holder.attributeInfos, attTexts = holder.attributeTexts;
        AgentBloodDonation agBld = (AgentBloodDonation) agent;

        attTexts.get(0).setText("Blood Type");
        attInfos.get(0).setText(agBld.bloodType);

        attTexts.get(1).setText("Smoking Habit");
        attInfos.get(1).setText(agBld.smokingHabit);

        attTexts.get(2).setText("Donation Number");
        attInfos.get(2).setText(agBld.donationsDone+"");

        attTexts.get(3).setText("Last Donated");
        attInfos.get(3).setText(agBld.daysSinceLastDonated+" months ago");

        attTexts.get(4).setText("");
        attInfos.get(4).setText("");
    }
}

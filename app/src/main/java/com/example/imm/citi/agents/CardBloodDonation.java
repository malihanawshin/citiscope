package com.example.imm.citi.agents;

import android.app.Activity;
import android.widget.TextView;

import com.example.imm.citi.activities.AgentListAdapter;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/7/2017.
 */

public class CardBloodDonation extends CardAgent {
    public CardBloodDonation(Activity act) {
        super(act);
    }

    @Override
    public void setAttributes(AgentListAdapter.AgentViewHolder holder, Agent agent) {
        ArrayList<TextView> attInfos = holder.attributeInfos, attTexts = holder.attributeTexts;
        AgentBloodDonation agBld = (AgentBloodDonation) agent;

        attTexts.get(0).setText("City");
        attInfos.get(0).setText(agBld.district);

        attTexts.get(1).setText("Blood Group");
        attInfos.get(1).setText(agBld.bloodType);

        attTexts.get(2).setText("Smoking Habit");
        attInfos.get(2).setText(agBld.smokingHabit);

        attTexts.get(3).setText("Donation Number");
        attInfos.get(3).setText(agBld.donationsDone+"");

        attTexts.get(4).setText("Last Donated");
        attInfos.get(4).setText(agBld.daysSinceLastDonated+" months ago");

        attTexts.get(5).setText("");
        attInfos.get(5).setText("");

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

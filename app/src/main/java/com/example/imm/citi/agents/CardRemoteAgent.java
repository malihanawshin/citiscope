package com.example.imm.citi.agents;

import android.widget.TextView;

import com.example.imm.citi.activities.AgentListAdapter;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/7/2017.
 */

public class CardRemoteAgent extends CardAgent {
    @Override
    public void setAttributes(AgentListAdapter.AgentViewHolder holder, Agent agent) {
        ArrayList<TextView> attInfos = holder.attributeInfos, attTexts = holder.attributeTexts;
        final RemoteAgent agRem = (RemoteAgent) agent;

        attTexts.get(0).setText("Visit");
        attInfos.get(0).setText(agRem.url);
        attInfos.get(0).setClickable(true);

        attTexts.get(1).setText("");
        attInfos.get(1).setText("");

        attTexts.get(2).setText("");
        attInfos.get(2).setText("");

        attTexts.get(3).setText("");
        attInfos.get(3).setText("");

        attTexts.get(4).setText("");
        attInfos.get(4).setText("");
    }
}

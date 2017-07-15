package com.example.imm.citi.agents;

import android.text.util.Linkify;
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

        attTexts.get(0).setText("City");
        attInfos.get(0).setText(agTui.district);

        attTexts.get(1).setText("Areas");
        attInfos.get(1).setText(getStringFromArray(agTui.areas));

        attTexts.get(2).setText("Mediums");
        attInfos.get(2).setText(getStringFromArray(agTui.mediums));

        attTexts.get(3).setText("Classes");
        attInfos.get(3).setText(getStringFromArray(agTui.classes));

        attTexts.get(4).setText("Subjects");
        attInfos.get(4).setText(getStringFromArray(agTui.subjects));

        attTexts.get(5).setText("School");
        attInfos.get(5).setText(agTui.school);

        attTexts.get(6).setText("College");
        attInfos.get(6).setText(agTui.college);

        attTexts.get(7).setText("University");
        attInfos.get(7).setText(agTui.university);

        attTexts.get(8).setText("Occupation");
        attInfos.get(8).setText(agTui.occupation);

        attTexts.get(9).setText("Tuitions Done");
        attInfos.get(9).setText(agTui.tuitionsDone);

        attTexts.get(10).setText("Profile Link");
        attInfos.get(10).setText(agTui.profileLink);
        Linkify.addLinks(attInfos.get(10), Linkify.WEB_URLS);
    }
}

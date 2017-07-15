package com.example.imm.citi.agents;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imm.citi.activities.AgentListAdapter;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/7/2017.
 */

public class CardTuition extends CardAgent {
    public CardTuition(Activity act) {
        super(act);
    }

    @Override
    public void setAttributes(AgentListAdapter.AgentViewHolder holder, Agent agent) {
        ArrayList<TextView> attInfos = holder.attributeInfos, attTexts = holder.attributeTexts;
        final AgentTuition agTui = (AgentTuition) agent;

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
        attInfos.get(9).setText(agTui.tuitionsDone + "");

        attTexts.get(10).setText("Profile Link");
        attInfos.get(10).setText(agTui.profileLink);
        attInfos.get(10).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(agTui.profileLink));
                try{
                    parent.startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(parent, "Invalid Link", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}

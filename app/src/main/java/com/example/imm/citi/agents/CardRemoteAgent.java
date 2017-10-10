package com.example.imm.citi.agents;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.imm.citi.R;
import com.example.imm.citi.activities.AgentListAdapter;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/7/2017.
 */

public class CardRemoteAgent extends CardAgent {
    public CardRemoteAgent(Activity act) {
        super(act);
    }

    @Override
    public void setAttributes(AgentListAdapter.AgentViewHolder holder, Agent agent) {
        ArrayList<TextView> attInfos = holder.attributeInfos, attTexts = holder.attributeTexts;
        final RemoteAgent agRem = (RemoteAgent) agent;

        attTexts.get(0).setText("Visit");
        attInfos.get(0).setText(agRem.url);
        attInfos.get(0).setTextColor(ContextCompat.getColor(parent, R.color.colorTextLink));
        attInfos.get(0).setPaintFlags(attInfos.get(0).getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        attInfos.get(0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(agRem.url));
            parent.startActivity(intent);
            }

        });

        attTexts.get(1).setText("");
        attInfos.get(1).setText("");

        attTexts.get(2).setText("");
        attInfos.get(2).setText("");

        attTexts.get(3).setText("");
        attInfos.get(3).setText("");

        attTexts.get(4).setText("");
        attInfos.get(4).setText("");

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

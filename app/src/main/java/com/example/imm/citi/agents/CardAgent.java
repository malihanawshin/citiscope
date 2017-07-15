package com.example.imm.citi.agents;

import android.app.Activity;

import com.example.imm.citi.activities.AgentListAdapter;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/7/2017.
 */

public abstract class CardAgent {
    Activity parent;

    public CardAgent (Activity act){
        parent = act;
    }
    public abstract void setAttributes(AgentListAdapter.AgentViewHolder holder, Agent agent);


    protected String getStringFromArray(ArrayList<String> arr) {
        String str = "";

        for(String s: arr){
            str += s + "; ";
        }
        str = str.substring(0, str.length()-2);

        return str;
    }
}

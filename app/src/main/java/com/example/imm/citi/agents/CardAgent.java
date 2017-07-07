package com.example.imm.citi.agents;

import com.example.imm.citi.activities.AgentListAdapter;

/**
 * Created by Sujoy on 7/7/2017.
 */

public abstract class CardAgent {
    public abstract void setAttributes(AgentListAdapter.AgentViewHolder holder, Agent agent);
}

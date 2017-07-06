package com.example.imm.citi.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.imm.citi.R;
import com.example.imm.citi.agents.Agent;
import java.util.ArrayList;

/**
 * Created by imm on 7/2/2017.
 */

public class AgentListAdapter extends RecyclerView.Adapter<AgentListAdapter.AgentViewHolder>{

    private Context aContext;
    private ArrayList<Agent> items;
    private AgentClickCallback aCallback;
    private Button bookmark;
    private Button compare;
    private Button editOwnInfo;
    private int flag;
    //private LinearLayout service;

    public interface AgentClickCallback{
        void onCompareClick(Agent a);
        void onBookmarkClick(Agent a);
        void onEditClick(Agent a);
    }

    public AgentListAdapter(Context aContext,ArrayList<Agent> items,AgentClickCallback aCallback,int flag){
        this.aContext=aContext;
        this.items= items;
        this.aCallback = aCallback;
        this.flag=flag;
    }

    public class AgentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView agentname;
        TextView serviceOfAgent;
        ImageView call;
        ImageView mail;
        ImageView location;

        public AgentViewHolder(View itemView) {
            super(itemView);

            serviceOfAgent = (TextView) itemView.findViewById(R.id.text_agent_service);

            agentname = (TextView) itemView.findViewById(R.id.agentname);
            agentname.setOnClickListener(this);
            call = (ImageView) itemView.findViewById(R.id.img_call);
            call.setOnClickListener(this);
            mail = (ImageView) itemView.findViewById(R.id.img_email);
            mail.setOnClickListener(this);
            location = (ImageView) itemView.findViewById(R.id.img_location);
            location.setOnClickListener(this);

            bookmark = (Button) itemView.findViewById(R.id.btnToBookmark);
            bookmark.setOnClickListener(this);

            compare = (Button) itemView.findViewById(R.id.btnToCompare);
            compare.setOnClickListener(this);

            editOwnInfo = (Button) itemView.findViewById(R.id.btnToEdit);
            editOwnInfo.setOnClickListener(this);

            if(flag==0) bookmark.setVisibility(itemView.VISIBLE);
            else if(flag==1) bookmark.setVisibility(itemView.GONE);
            else if(flag==2) {
                bookmark.setVisibility(itemView.GONE);
                compare.setVisibility(itemView.GONE);
                editOwnInfo.setVisibility(itemView.VISIBLE);
                serviceOfAgent.setText("Service Name"); // TODO set actual service name later
            }

        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.img_call:
                    //startCall();
                    break;
                case R.id.img_email:
                    //sendMail();
                    break;
                case R.id.img_location:
                    //startAddress();
                    break;

                case R.id.btnToBookmark:
                    break;

                case R.id.btnToCompare:
                    break;

                case R.id.btnToEdit:
                    showEditInfoPage();
                    break;
            }

        }

        public void showEditInfoPage(){
            if (aCallback != null) {
                int position = getAdapterPosition();
                Agent a = (Agent) items.get(position);
                aCallback.onEditClick(a);
            }
        }
    }

    @Override
    public AgentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(aContext).inflate(R.layout.agent_item, parent, false);
        return new AgentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AgentViewHolder holder, int position) {
        Agent agent = (Agent) items.get(position);
        holder.agentname.setText(agent.getName());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

}

package com.example.imm.citi.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imm.citi.R;
import com.example.imm.citi.agents.Agent;
import com.example.imm.citi.agents.AgentApartmentRenting;
import com.example.imm.citi.agents.AgentBloodDonation;
import com.example.imm.citi.agents.AgentDoctor;
import com.example.imm.citi.agents.AgentTuition;
import com.example.imm.citi.agents.CardAgent;
import com.example.imm.citi.agents.CardApartmentRenting;
import com.example.imm.citi.agents.CardBloodDonation;
import com.example.imm.citi.agents.CardDoctor;
import com.example.imm.citi.agents.CardRemoteAgent;
import com.example.imm.citi.agents.CardTuition;
import com.example.imm.citi.agents.LocalAgent;
import com.example.imm.citi.agents.RemoteAgent;
import com.example.imm.citi.technicalClasses.User;

import java.util.ArrayList;

/**
 * Created by imm on 7/2/2017.
 */

public class AgentListAdapter extends RecyclerView.Adapter<AgentListAdapter.AgentViewHolder> {

    private Context aContext;
    private Activity parent;
    private ArrayList<Agent> items;
    private AgentClickCallback aCallback;
    private Button editOwnInfo;
    private int flag;
    //private LinearLayout service;

    public interface AgentClickCallback {
        void onCompareClick(Agent a);

        void onBookmarkClick(Agent a);

        void onEditClick(Agent a);
    }

    public AgentListAdapter(Context aContext, ArrayList<Agent> items, AgentClickCallback aCallback, int flag) {
        this.aContext = aContext;
        parent = (Activity) aContext;
        this.items = items;
        this.aCallback = aCallback;
        this.flag = flag;
        setHasStableIds(true);
    }

    public class AgentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView agentname, serviceOfAgent;
        ImageView call, mail, locate;
        public ArrayList<TextView> attributeInfos, attributeTexts;

        private Button bookmark, compare;

        String phoneNo, emailAddress, location;
        Agent agent;


        public AgentViewHolder(View itemView) {
            super(itemView);

            serviceOfAgent = (TextView) itemView.findViewById(R.id.text_agent_service);

            agentname = (TextView) itemView.findViewById(R.id.agentname);
            agentname.setOnClickListener(this);
            call = (ImageView) itemView.findViewById(R.id.img_call);
            call.setOnClickListener(this);
            mail = (ImageView) itemView.findViewById(R.id.img_email);
            mail.setOnClickListener(this);
            locate = (ImageView) itemView.findViewById(R.id.img_location);
            locate.setOnClickListener(this);

            identifyAttributes(itemView);

            bookmark = (Button) itemView.findViewById(R.id.btnToBookmark);
            bookmark.setOnClickListener(this);

            compare = (Button) itemView.findViewById(R.id.btnToCompare);
            compare.setOnClickListener(this);

            editOwnInfo = (Button) itemView.findViewById(R.id.btnToEdit);
            editOwnInfo.setOnClickListener(this);

            if (flag == 0) bookmark.setVisibility(itemView.VISIBLE);
            else if (flag == 1) bookmark.setVisibility(itemView.GONE);
            else if (flag == 2) {
                bookmark.setVisibility(itemView.GONE);
                compare.setVisibility(itemView.GONE);
                editOwnInfo.setVisibility(itemView.VISIBLE);
                serviceOfAgent.setText("Service Name"); // TODO set actual service name later
            }

        }

        private void identifyAttributes(View itemView) {
            attributeInfos = new ArrayList<>(5);
            attributeTexts = new ArrayList<>(5);

            TextView attInfo = (TextView) itemView.findViewById(R.id.info_agent_attribute_1);
            TextView attText = (TextView) itemView.findViewById(R.id.text_agent_attribute_1);
            attributeInfos.add(attInfo);
            attributeTexts.add(attText);

            attInfo = (TextView) itemView.findViewById(R.id.info_agent_attribute_2);
            attText = (TextView) itemView.findViewById(R.id.text_agent_attribute_2);
            attributeInfos.add(attInfo);
            attributeTexts.add(attText);

            attInfo = (TextView) itemView.findViewById(R.id.info_agent_attribute_3);
            attText = (TextView) itemView.findViewById(R.id.text_agent_attribute_3);
            attributeInfos.add(attInfo);
            attributeTexts.add(attText);

            attInfo = (TextView) itemView.findViewById(R.id.info_agent_attribute_4);
            attText = (TextView) itemView.findViewById(R.id.text_agent_attribute_4);
            attributeInfos.add(attInfo);
            attributeTexts.add(attText);

            attInfo = (TextView) itemView.findViewById(R.id.info_agent_attribute_5);
            attText = (TextView) itemView.findViewById(R.id.text_agent_attribute_5);
            attributeInfos.add(attInfo);
            attributeTexts.add(attText);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_call:
                    startCall();
                    break;
                case R.id.img_email:
                    sendMail();
                    break;
                case R.id.img_location:
                    showMap();
                    break;

                case R.id.btnToBookmark:
                    if(bookmark.getText().toString().equals("Bookmark"))
                        bookmark();
                    else
                        removeBookmark();
                    break;

                case R.id.btnToCompare:
                    break;

                case R.id.btnToEdit:
                    showEditInfoPage();
                    break;
            }

        }



        public void showEditInfoPage() {
            if (aCallback != null) {
                int position = getAdapterPosition();
                Agent a = (Agent) items.get(position);
                aCallback.onEditClick(a);
            }
        }



        private void removeBookmark() {
            System.out.println("here to REMOVE BOOKMARK");
            if(!User.loggedIn){
                Toast.makeText(parent, "Sign in First", Toast.LENGTH_SHORT).show();
                return;
            }

            if(agent instanceof RemoteAgent){
                RemoteAgent remAg = (RemoteAgent) agent;

                System.out.println("srvname: " + remAg.serviceName);
                remAg.removeRemoteBookmark(parent);
            }
            else if(agent instanceof LocalAgent){
                LocalAgent locAg = (LocalAgent) agent;
                locAg.removeLocalBookmark(parent, bookmark);
            }
        }

        private void bookmark() {
            if(!User.loggedIn){
                Toast.makeText(parent, "Sign in First", Toast.LENGTH_SHORT).show();
                return;
            }

            if(agent instanceof RemoteAgent){
                RemoteAgent remAg = (RemoteAgent) agent;

                System.out.println("srvname: " + remAg.serviceName);
                remAg.addRemoteBookmark(parent);
            }
            else if(agent instanceof LocalAgent){
                LocalAgent locAg = (LocalAgent) agent;
                locAg.addLocalBookmark(parent, bookmark);
            }
        }

        private void showMap() {
            Intent intent = new Intent(parent, AgMapActivity.class);
            intent.putExtra("address", location);
            parent.startActivity(intent);
        }

        private void sendMail() {
            if(emailAddress==null || emailAddress.equals(""))
                return;

//            Toast.makeText(parent, emailAddress, Toast.LENGTH_SHORT).show();
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{emailAddress});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Client Request from CITISCOPE");

            emailIntent.setType("message/rfc822");
            parent.startActivity(emailIntent);
        }

        private void startCall() {
            if(phoneNo==null || phoneNo.equals(""))
                return;

            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNo));
//            Toast.makeText(parent, phoneNo, Toast.LENGTH_SHORT).show();

            if (ActivityCompat.checkSelfPermission(aContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            parent.startActivity(callIntent);
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
        holder.agent = agent;

        holder.agentname.setText(agent.getName());
        CardAgent card = getCard(agent);
        if(card!=null)
            card.setAttributes(holder, agent);

        holder.phoneNo = agent.phone;
        holder.emailAddress = agent.email;
        holder.location = agent.address;

        if(User.loggedIn)
            setBookmark(holder, agent);
        else
            holder.bookmark.setEnabled(false);
    }

    private void setBookmark(AgentViewHolder holder, Agent agent) {
        if(agent instanceof LocalAgent){
            LocalAgent locAg = (LocalAgent) agent;

            System.out.println(locAg.name + locAg.isBookmarked());
            if(locAg.isBookmarked()){
                System.out.println("eta bookmarked " + locAg.name);
                holder.bookmark.setText("Unbookmark");
            }
        }
    }

    private CardAgent getCard(Agent agent) {
        CardAgent card = null;

        if(agent instanceof AgentTuition){
            card = new CardTuition();
//            AgentTuition agTui = (AgentTuition) agent;
//            System.out.println("TUITION AGENT " + agTui.name + " " + agTui.occupation);
        }
        else if(agent instanceof AgentApartmentRenting){
            card = new CardApartmentRenting();
//            AgentApartmentRenting agApt = (AgentApartmentRenting) agent;
//            System.out.println("TUITION AGENT " + agApt.price + " " + agApt.size);
        }
        else if(agent instanceof AgentBloodDonation){
            card = new CardBloodDonation();
//            AgentApartmentRenting agApt = (AgentApartmentRenting) agent;
//            System.out.println("TUITION AGENT " + agApt.price + " " + agApt.size);
        }
        else if(agent instanceof AgentDoctor){
            card = new CardDoctor();
//            AgentApartmentRenting agApt = (AgentApartmentRenting) agent;
//            System.out.println("TUITION AGENT " + agApt.price + " " + agApt.size);
        }
        else if(agent instanceof RemoteAgent){
            card = new CardRemoteAgent();
        }
        return card;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

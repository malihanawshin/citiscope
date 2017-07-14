package com.example.imm.citi.activities;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Nomination;
import com.example.imm.citi.technicalClasses.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by imm on 7/7/2017.
 */

public class NominationListAdapter extends RecyclerView.Adapter{

    private List<Nomination> items;
    private Context nContext;
    private Activity parent;
    private NominationClickCallback nCallback;

    public interface NominationClickCallback{
        public void onSeeDetailsClick(Nomination nomination);
        public void onVoteClick(Nomination nomination, String action, int position);
    }

    public NominationListAdapter(Context nContext,List <Nomination> items1, NominationClickCallback clickCallback){

        this.nContext=nContext;
        parent = (Activity)nContext;
        this.nCallback=clickCallback;
        items = items1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(nContext).inflate(R.layout.poll_item,parent,false);
        return new NominationHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NominationHolder)holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class NominationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.text_nomination_label) TextView nominationName;
        @BindView(R.id.text_nomination_description) TextView nominationDetails;
        @BindView(R.id.text_vote_count)TextView voteCount;
        @BindView(R.id.btnToVote) Button toVote;
        @BindView(R.id.btnToSeeDetails) Button toViewDetails;

        public NominationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(Nomination nomination){
            nominationName.setText(nomination.name);
            nominationDetails.setText(nomination.description);
            voteCount.setText(""+nomination.voteCount);

            System.out.println("dhet " + nomination.canVote + " " + nomination.name);

            if(nomination.canVote == true)
                toVote.setText("Vote");
            else
                toVote.setText("Unvote");


            toVote.setOnClickListener(this);
            toViewDetails.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.btnToVote:
                    if(User.loggedIn)
                        updateVote();
                    else
                        Toast.makeText(parent, "Log in first", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnToSeeDetails:
                    showDetailsPage();
                    break;
            }
        }

        public void showDetailsPage(){
            if ( nCallback!= null) {
                int position = getAdapterPosition();
                Nomination n = (Nomination) items.get(position);
                nCallback.onSeeDetailsClick(n);
            }
        }

        public void updateVote(){
            if ( nCallback!= null) {
                int position = getAdapterPosition();
                Nomination n = (Nomination) items.get(position);
                nCallback.onVoteClick(n, toVote.getText().toString(), position);
            }
        }
    }
}

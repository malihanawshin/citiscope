package com.example.imm.citi.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Nomination;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by imm on 7/7/2017.
 */

public class NominationListAdapter extends RecyclerView.Adapter{

    private List<Nomination> items;
    private Context nContext;
    private NominationClickCallback nCallback;

    public interface NominationClickCallback{

        public void onEditClick(Nomination nomination);

    }

    public NominationListAdapter(Context nContext,List <Nomination> items, NominationClickCallback clickCallback){

        this.nContext=nContext;
        this.nCallback=clickCallback;
        additems(items);
    }


    public void additems(List<Nomination> items){
        this.items= new ArrayList<>();
        this.items.addAll(items);
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
                // TODO nominationName.setText(nomination.getName());
                // TODO nominationDetails.setText(nomination.getDetails());
                // TODO voteCount.setText(nomination.getVote());

                toVote.setOnClickListener(this);
                toViewDetails.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.btnToVote:
                    //updateVote();
                    //voteCount.setText();
                    break;
                case R.id.btnToSeeDetails:
                    showEditPage();
                    break;
            }
        }

        public void showEditPage(){
            if ( nCallback!= null) {
                int position = getAdapterPosition();
                Nomination n = (Nomination) items.get(position);
                nCallback.onEditClick(n);
            }
        }
    }
}

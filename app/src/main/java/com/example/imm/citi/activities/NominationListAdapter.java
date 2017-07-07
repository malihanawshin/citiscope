package com.example.imm.citi.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.imm.citi.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by imm on 7/7/2017.
 */

public class NominationListAdapter extends RecyclerView.Adapter{

    //private List<Nomination> items;

    public NominationListAdapter(){
      //  additems(items);
    }


    /*public void additems(List<Nomination> items){
        this.items= new ArrayList<>();
        this.items.addAll();
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_item,parent,false);
        return new NominationHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //((NominationHolder)holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class NominationHolder extends RecyclerView.ViewHolder{


        public NominationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(){

        }
    }
}

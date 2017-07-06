package com.example.imm.citi.agents;

import android.app.Activity;

import com.example.imm.citi.technicalClasses.Service;

import java.util.ArrayList;

public abstract class FactoryAgent {
	Service service;
	Activity parent;
    ArrayList<Agent> agents = new ArrayList<>();


    public FactoryAgent(Service serv, Activity act){
        service = serv;
        parent = act;
    }

    public abstract void fetchAgents();

    public abstract void finishFetch();

	ArrayList<LocalAgent> search(ArrayList<android.support.v4.util.Pair<String, String>> chosenOptions){
		
		return null;
	}
	
	ArrayList<LocalAgent> sort(ArrayList<LocalAgent> unsortedAgents){
		
		return null;
	}
}

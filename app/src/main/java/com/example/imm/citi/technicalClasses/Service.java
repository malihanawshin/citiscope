package com.example.imm.citi.technicalClasses;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.imm.citi.R;
import com.example.imm.citi.activities.FilterActivity;
import com.example.imm.citi.agents.Agent;
import com.example.imm.citi.agents.FactoryAgent;
import com.example.imm.citi.agents.FactoryApartmentRenting;
import com.example.imm.citi.agents.FactoryBloodDonation;
import com.example.imm.citi.agents.FactoryDoctor;
import com.example.imm.citi.agents.FactoryTuition;
import com.example.imm.citi.sources.BloodDonorsBd;
import com.example.imm.citi.sources.DoctorsBD;
import com.example.imm.citi.sources.DonateBloodBd;
import com.example.imm.citi.sources.PrivateTutorBD;
import com.example.imm.citi.sources.PropertyInBD;
import com.example.imm.citi.sources.RentalHomeBD;
import com.example.imm.citi.sources.Source;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sujoy on 4/10/2017.
 */

public class Service {
    ArrayList<Source> sources;

    final String FILTEROPTIONFILE = "filterAndOption.php";
    private LinearLayout linLay;

    Activity parent;
    String service;

    public ArrayList<Agent> agents;
    ArrayList<ArrayList<Agent>> tempAgents;
    Source tempSrc;

    ArrayList<Pair<String, Spinner>> spinners;
    ArrayList<Pair<String, String>> chosenOptions;
    private String district;

    ArrayList<FilterOption> filOps;

    public Service(String str, Activity act, String dist){
        parent = act;
        service = str;
        district = dist;
        linLay = (LinearLayout) act.findViewById(R.id.ll_filters);
        spinners = new ArrayList<>();
        chosenOptions = new ArrayList<>();
        agents = new ArrayList<>();
        filOps = new ArrayList<>();

        System.out.println("ACTIVITY1: " + parent);

        getSources();
    }

    protected void getSources(){
        sources = new ArrayList<>();
        if(service.equals("Blood Donation")){
            sources.add(new DonateBloodBd(parent, this));
            sources.add(new BloodDonorsBd(parent, this));
        }
        else if(service.equals("Apartment Renting")){
            sources.add(new RentalHomeBD(parent, this));
            sources.add(new PropertyInBD(parent, this));
        }
        else if(service.equals("Doctor")){
            sources.add(new DoctorsBD(parent, this));
        }
        else if(service.equals("Tuition")){
            sources.add(new PrivateTutorBD(parent, this));
        }
    }





    public void fetchFilters() {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        keys.add("service");
        vals.add(service);

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, FILTEROPTIONFILE, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");

                    if (result.length() != 0) {
                        //System.out.println(result + " X " + result.length());
                        for (int i = 0; i < result.length(); i++) {
                            try {
                                JSONObject obj = result.getJSONObject(i);
                                addToFilOps(obj.getString("Filter"), obj.getString("Option"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    for(FilterOption fo: filOps){
                        System.out.println("Filter Option > > > > > " + fo);
                    }

                    createFilters();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addToFilOps(String filter, String option) {
        for(FilterOption fo: filOps){
            if(fo.filter.equals(filter)) {
                fo.add(option);
                return;
            }
        }

        filOps.add(new FilterOption(filter, option));
    }

    public void createFilters(){
        swapFirstFilterWithDistrict();


        for(FilterOption fo: filOps){
            createSpinners(fo);
            TextView txt = new TextView(parent);
            txt.setText(fo.filter);
            LinearLayout.LayoutParams layPar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layPar.setMargins(0, 20, 20, 0);
            txt.setLayoutParams(layPar);
            txt.setTextColor(ContextCompat.getColor(parent, R.color.colorAccent));
            txt.setTextSize(20);
            linLay.addView(txt);

            ArrayList<String> spinnerArray = new ArrayList<String>();

            for(int i=0; i<fo.options.size(); i++){
                spinnerArray.add(fo.options.get(i));
            }

            Spinner spinner = new Spinner(parent);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(parent,android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            spinner.setAdapter(spinnerArrayAdapter);
            linLay.addView(spinner);
            spinners.add(new Pair<String, Spinner>(txt.getText().toString(), spinner));

            if(txt.getText().equals("District")){
                displayDistrictChosenPreviously(spinner, spinnerArrayAdapter);
            }
        }
    }

    private void createSpinners(FilterOption fo) {
        System.out.println("HEY >> " + fo);
    }

    private void displayDistrictChosenPreviously(Spinner spinner, ArrayAdapter<String> spinnerArrayAdapter) {
        int pos = spinnerArrayAdapter.getPosition(district);
        spinner.setSelection(pos);
    }

    private void swapFirstFilterWithDistrict() {
        for(int i=0; i<filOps.size(); i++){
            if(filOps.get(i).filter.equals("District")){
                FilterOption tempFilOp = filOps.get(i);
                filOps.remove(i);
                filOps.add(0, tempFilOp);
            }
        }
    }

    public void fetchResult(){
        tempAgents = new ArrayList<>();
        getChosenOptons();

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                int i=0;
                for(Source src: sources) {
                    tempSrc = src;
                    try {
                        tempAgents.add(tempSrc.fetchResult(chosenOptions));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                agents = sortResult(tempAgents);

                getLocalAgents();
            }
        }.execute();
    }

    private void getLocalAgents() {
        tempAgents = new ArrayList<ArrayList<Agent>>();

        FactoryAgent agentFac;
        if(service.equals("Tuition"))
            agentFac = new FactoryTuition(this, parent, agents);
        else if(service.equals("Apartment Renting"))
            agentFac = new FactoryApartmentRenting(this, parent, agents);
        else if(service.equals("Blood Donation"))
            agentFac = new FactoryBloodDonation(this, parent, agents);
        else if(service.equals("Doctor"))
            agentFac = new FactoryDoctor(this, parent, agents);
        else
            agentFac = null;

        if(agentFac!=null) agentFac.fetchAgents();
    }

    public ArrayList<Agent> sortResult(ArrayList<ArrayList<Agent>> tempAgents){
        ArrayList<Agent> sortedAgents = new ArrayList<>();
        int i=0;
        int max = -1;
        //System.out.println("LENGTH: " + tempAgents.size());
        for(ArrayList ag: tempAgents){
            if(ag.size()>max) max=ag.size();
        }

        int seq = 1;
        while(i<max)
        {
            for(ArrayList<Agent> ag: tempAgents){
                if(i<ag.size()) {
                    Agent tempAg = ag.get(i);
                    if(tempAg.nameLess()) {
                        tempAg.addSeq(seq);
                        seq++;
                    }

                    sortedAgents.add(tempAg);
                }
            }
            i++;
        }
        return sortedAgents;
    }

    public void showResult(ArrayList<Agent> agents1){
        for(Agent ag: agents1){
            System.out.println("-----------------Start\n");
            System.out.println(ag.name +"\n" + ag.address +"\n" + ag.email +"\n" + ag.phone +"\n" + ag.url +"\n");
            System.out.println("-----------------End\n");
        }

        FilterActivity act = (FilterActivity) parent;
        act.showResult(agents1);
    }





    private void getChosenOptons() {
        chosenOptions.clear();
        for(Pair p: spinners){
            Spinner sp = (Spinner) p.second;
            chosenOptions.add(new Pair<String, String>((String)p.first, sp.getSelectedItem().toString()));
        }
    }
}

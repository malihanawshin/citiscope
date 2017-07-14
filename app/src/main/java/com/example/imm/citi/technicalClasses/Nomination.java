package com.example.imm.citi.technicalClasses;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.imm.citi.activities.PollActivity;

import java.util.ArrayList;

/**
 * Created by Sujoy on 4/10/2017.
 */

public class Nomination implements Parcelable{
    private Activity parent;
    public String name, description, nominator, dateAdded;
    public ArrayList<String> sources, filters, cities;
    public int voteCount;
    PollActivity pollParent;
    private final String ADDNOMFILE = "addNomination.php", UPDATENOMFILE = "updateNomination.php";

    public Nomination(){
        name = "Default";
    }

    public Nomination(Activity act){
        parent = act;
    }

    public void setAttributes(String nm, String desc, ArrayList<String> srcs, String nom, int votes, ArrayList filters1, ArrayList cities1, String date){
        name = nm;
        description = desc; 
        sources = srcs;
        nominator = nom;
        voteCount = votes;
        filters = filters1;
        cities = cities1;
        dateAdded = date;
    }


    public void addSource(String url) {
        sources.add(url);
    }

    public void addFilter(String filter) {
        filters.add(filter);
    }

    public void addCity(String city) {
        cities.add(city);
    }



    public void updateVote(String path, PollActivity act){
        ArrayList<String> keys = new ArrayList<>(), vals = new ArrayList<>();

        keys.add("email");
        keys.add("name");

        vals.add(User.Email);
        vals.add(name);

        pollParent = act;

        Database db = new Database();
        db.update(new RetrievalData(keys, vals, path, pollParent), false, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("true")){
                    //parent.poll.createPoll();
                }
                else{
                    Toast.makeText(pollParent, "Sorry, something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public String toString() {
        return name + " " + dateAdded;
    }



    public void addNomination(){
        ArrayList<String> keys = getKeys(), vals = getVals();

        Database db = new Database();
        db.update(new RetrievalData(keys, vals, ADDNOMFILE, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("true")){
                    goToPoll();
                }
                else{
                    Toast.makeText(parent, "Sorry, something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void updateNomination(String oldName) {
        ArrayList<String> keys = getKeys(), vals = getVals();
        keys.add("oldName");
        vals.add(oldName);

        Database db = new Database();
        db.update(new RetrievalData(keys, vals, UPDATENOMFILE, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("true")){
                    goToPoll();
                }
                else{
                    Toast.makeText(parent, "Sorry, something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void goToPoll() {
        Intent intent = new Intent(parent, PollActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        parent.startActivity(intent);
        parent.finish();
    }

    private ArrayList<String> getVals() {
        ArrayList<String> vals = new ArrayList<>();

        vals.add(name);
        vals.add(description);
        vals.add(nominator);
        vals.add(dateAdded);

        vals.add(sources.size()+"");
        vals.addAll(sources);
        vals.add(filters.size()+"");
        vals.addAll(filters);
        vals.add(cities.size()+"");
        vals.addAll(cities);

        return vals;
    }

    private ArrayList<String> getKeys() {
        ArrayList<String> keys = new ArrayList<>();

        keys.add("name");
        keys.add("desc");
        keys.add("email");
        keys.add("date");

        keys.add("sourceNo");
        keys.addAll(getKeyForArray("source", sources.size()));
        keys.add("filterNo");
        keys.addAll(getKeyForArray("filter", filters.size()));
        keys.add("cityNo");
        keys.addAll(getKeyForArray("city", cities.size()));

        return keys;
    }


    private ArrayList<String> getKeyForArray(String key, int length){
        ArrayList<String> arrKeys = new ArrayList<>();

        for(int i=1; i<=length; i++){
            arrKeys.add(key+i);
        }

        return arrKeys;
    }







    protected Nomination(Parcel in) {
        name = in.readString();
        description = in.readString();
        nominator = in.readString();
        sources = in.createStringArrayList();
        voteCount = in.readInt();
        filters = in.createStringArrayList();
        cities = in.createStringArrayList();
        dateAdded = in.readString();
    }

    public static final Creator<Nomination> CREATOR = new Creator<Nomination>() {
        @Override
        public Nomination createFromParcel(Parcel in) {
            return new Nomination(in);
        }

        @Override
        public Nomination[] newArray(int size) {
            return new Nomination[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(nominator);
        parcel.writeStringList(sources);
        parcel.writeInt(voteCount);
        parcel.writeStringList(filters);
        parcel.writeStringList(cities);
        parcel.writeString(dateAdded);
    }
}

package com.example.imm.citi.technicalClasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.imm.citi.activities.PollActivity;

import java.util.ArrayList;

/**
 * Created by Sujoy on 4/10/2017.
 */

public class Nomination implements Parcelable{
    public String name, description, nominator, dateAdded;
    public ArrayList<String> sources, filters, cities;
    public int voteCount;
    PollActivity parent;

    public Nomination(){
        name = "Default";
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

        parent = act;

        Database db = new Database();
        db.update(new RetrievalData(keys, vals, path, parent), false, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("true")){
                    //parent.poll.createPoll();
                }
                else{
                    Toast.makeText(parent, "Sorry, something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public String toString() {
        return name;
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

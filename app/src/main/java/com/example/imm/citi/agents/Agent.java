package com.example.imm.citi.agents;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sujoy on 4/10/2017.
 */

public class Agent implements Parcelable, Comparable<Agent>{
    public String name;
    public String phone;
    public String address;
    public String email;
    Boolean bookmarked=false;


    public String id;
    ArrayList<String> keys, vals;
    protected Activity parent;


    ArrayList<Agent> bookmarks;

    public Agent() {}




    public void setBookmarked(){
        bookmarked=true;
        System.out.println("bookmarked " + name);
    }

    public Boolean isBookmarked(){
        return bookmarked;
    }


    protected ArrayList<String> multiplePhones(String phone)
    {
        String phones[] = new String[]{};
        boolean onePhone = true;
        if(phone.matches("(.*?),(.*?)") || phone.matches("(.*?)[\\s]{1}(.*?)"))
        {
            phones = phone.split(",");
            onePhone = false;
        }

        ArrayList<String> tempPhoneNo = new ArrayList<String>(Arrays.asList(phones));
        ArrayList<String> phoneNo = new ArrayList<String>();
        if(onePhone==true) phoneNo.add(phone);
        for(String singlePhone : tempPhoneNo)
        {
            if(!singlePhone.equals("")) phoneNo.add(singlePhone.trim());
        }
        return phoneNo;
    }



    public String toString(){
        return "Name: " + name + " ";
    }

    public void addSeq(int seq) {
        name += seq;
    }

    public boolean nameLess() {
        if(name.endsWith("#"))
            return true;
        return false;
    }

    public String getName() {
        return name;
    }



    public void setID(String ID) {
        this.id = ID;
    }


    public String convertArrayStrToStr(ArrayList<String> strArr) {
        String finalStr="";
        for(String tempStr: strArr){
            finalStr+=tempStr+" | ";
        }
        finalStr = finalStr.substring(0,finalStr.length()-3);
        return finalStr;
    }






    public Agent(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        email = in.readString();
    }

    public static final Creator<Agent> CREATOR = new Creator<Agent>() {
        @Override
        public Agent createFromParcel(Parcel in) {
            return new Agent(in);
        }

        @Override
        public Agent[] newArray(int size) {
            return new Agent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(address);
        parcel.writeString(email);
    }





















    public int testInstanceTuition(Agent a, Agent b)
    {
        if(a instanceof AgentTuition) return ((AgentTuition) a).compare((AgentTuition)a, (AgentTuition)b);
        return testInstanceDoctor(a, b);
    }

    public int testInstanceDoctor(Agent a, Agent b)
    {
        if(a instanceof AgentDoctor) return ((AgentDoctor) a).compare((AgentDoctor)a, (AgentDoctor)b);
        return testInstanceBlood(a, b);
    }

    public int testInstanceBlood(Agent a, Agent b)
    {
        if(a instanceof AgentBloodDonation) return ((AgentBloodDonation) a).compare((AgentBloodDonation)a, (AgentBloodDonation)b);
        return testInstanceApartment(a, b);
    }

    public int testInstanceApartment(Agent a, Agent b)
    {
        if(a instanceof AgentApartmentRenting)return ((AgentApartmentRenting) a).compare((AgentApartmentRenting)a, (AgentApartmentRenting)b);
        return extendible(a, b);
    }
    public int extendible(Agent a, Agent b)
    {
        return 0;
        //Do nothing
    }
    @Override
    public int compareTo(Agent o) {
        return testInstanceTuition(this, o);
    }
}

package com.example.imm.citi.agents;

import android.os.Parcel;

/**
 * Created by Sujoy on 7/2/2017.
 */

public class LocalAgent extends Agent {
    public LocalAgent(Parcel in) {
        super(in);
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

    public LocalAgent() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(url);
        parcel.writeString(address);
        parcel.writeString(email);
    }
}

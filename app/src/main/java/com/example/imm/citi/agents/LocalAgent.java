package com.example.imm.citi.agents;

import android.os.Parcel;

/**
 * Created by Sujoy on 7/2/2017.
 */

public class LocalAgent extends Agent {
    Boolean bookmarked=false;

    public void setBookmarked(){
        bookmarked=true;
        System.out.println("bookmarked " + name);
    }

    public Boolean isBookmarked(){
        return bookmarked;
    }

    public LocalAgent(Parcel in) {
        super(in);
        bookmarked = (Boolean)in.readValue(null);
    }

    public static final Creator<LocalAgent> CREATOR = new Creator<LocalAgent>() {
        @Override
        public LocalAgent createFromParcel(Parcel in) {
            return new LocalAgent(in);
        }

        @Override
        public LocalAgent[] newArray(int size) {
            return new LocalAgent[size];
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
        super.writeToParcel(parcel,i);
        parcel.writeValue(bookmarked);
    }
}

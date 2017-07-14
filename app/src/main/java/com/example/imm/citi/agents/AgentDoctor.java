package com.example.imm.citi.agents;

import android.os.Parcel;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/2/2017.
 */

public class AgentDoctor extends LocalAgent {
    public String district, specialty;

    public ArrayList<String> addresses, degrees;
    public String hospitalName;
    public int yearsInPractice;

    public AgentDoctor(String name1, String email1, String phone1, String address1, String district1, String specialty1, ArrayList addresses1, ArrayList degrees1, String hospitalName1, int yearsInPractice1) {
        name = name1;
        email = email1;
        phone = phone1;
        address = address1;

        district = district1;

        addresses = addresses1;
        degrees = degrees1;

        specialty = specialty1;
        hospitalName = hospitalName1;
        yearsInPractice = yearsInPractice1;
    }

    public void addAddress(String address) {
        addresses.add(address);
    }

    public void addDegree(String degree) {
        degrees.add(degree);
    }




    public int compare(AgentDoctor a, AgentDoctor b) {
        // TODO Auto-generated method stub
        return (b.yearsInPractice-a.yearsInPractice);
    }





    public AgentDoctor(Parcel in) {
        super(in);
        district = in.readString();
        addresses = in.readArrayList(String.class.getClassLoader());
        degrees = in.readArrayList(String.class.getClassLoader());

        specialty = in.readString();
        hospitalName = in.readString();
        yearsInPractice = in.readInt();
    }

    public static final Creator<AgentDoctor> CREATOR = new Creator<AgentDoctor>() {
        @Override
        public AgentDoctor createFromParcel(Parcel in) {
            return new AgentDoctor(in);
        }

        @Override
        public AgentDoctor[] newArray(int size) {
            return new AgentDoctor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(district);
        parcel.writeList(addresses);
        parcel.writeList(degrees);

        parcel.writeString(specialty);
        parcel.writeString(hospitalName);
        parcel.writeInt(yearsInPractice);
    }
}

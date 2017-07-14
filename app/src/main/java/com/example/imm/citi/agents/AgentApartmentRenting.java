package com.example.imm.citi.agents;

import android.os.Parcel;

/**
 * Created by Sujoy on 7/2/2017.
 */

public class AgentApartmentRenting extends LocalAgent {
    public String district, area,	propertyType;
    public long price;

    public long size;
    public int floor, roomNo;

    public AgentApartmentRenting(String name1, String email1, String phone1, String address1, String district1, String area1, String propertyType1, long price1, long size1, int floor1, int roomNo1) {
        name = name1;
        email = email1;
        phone = phone1;
        address = address1;

        district = district1;
        area = area1;
        propertyType = propertyType1;
        price = price1;

        size  = size1;
        floor = floor1;
        roomNo = roomNo1;
    }









    public int compare(AgentApartmentRenting a, AgentApartmentRenting b) {
        // TODO Auto-generated method stub
        return Long.compare(a.price, b.price);
    }








    public AgentApartmentRenting(Parcel in) {
        super(in);
        district = in.readString();
        area = in.readString();
        propertyType  = in.readString();
        price = in.readLong();

        size = in.readLong();
        floor = in.readInt();
        roomNo = in.readInt();
    }

    public static final Creator<AgentApartmentRenting> CREATOR = new Creator<AgentApartmentRenting>() {
        @Override
        public AgentApartmentRenting createFromParcel(Parcel in) {
            return new AgentApartmentRenting(in);
        }

        @Override
        public AgentApartmentRenting[] newArray(int size) {
            return new AgentApartmentRenting[size];
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
        parcel.writeString(area);
        parcel.writeString(propertyType);
        parcel.writeLong(price);

        parcel.writeLong(size);
        parcel.writeInt(floor);
        parcel.writeInt(roomNo);
    }
}

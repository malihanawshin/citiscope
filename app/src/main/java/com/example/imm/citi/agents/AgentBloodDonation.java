package com.example.imm.citi.agents;

import android.os.Parcel;

/**
 * Created by Sujoy on 7/2/2017.
 */

public class AgentBloodDonation extends LocalAgent {
    public String district, bloodType;

    public String smokingHabit;
    public int donationsDone, daysSinceLastDonated;

    public AgentBloodDonation(String name1, String email1, String phone1, String address1, String district1, String bloodType1, String smokingHabit1, int donationsDone1, int lastDonated1) {
        name = name1;
        email = email1;
        phone = phone1;
        address = address1;

        district = district1;
        bloodType = bloodType1;

        smokingHabit = smokingHabit1;
        donationsDone = donationsDone1;
        daysSinceLastDonated = lastDonated1;
    }








    public int compare(AgentBloodDonation a, AgentBloodDonation b) {
        // TODO Auto-generated method stub
        return b.donationsDone-a.donationsDone;
    }







    public AgentBloodDonation(Parcel in) {
        super(in);
        district = in.readString();
        bloodType = in.readString();

        smokingHabit  = in.readString();
        donationsDone = in.readInt();
        daysSinceLastDonated = in.readInt();
    }

    public static final Creator<AgentBloodDonation> CREATOR = new Creator<AgentBloodDonation>() {
        @Override
        public AgentBloodDonation createFromParcel(Parcel in) {
            return new AgentBloodDonation(in);
        }

        @Override
        public AgentBloodDonation[] newArray(int size) {
            return new AgentBloodDonation[size];
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
        parcel.writeString(bloodType);

        parcel.writeString(smokingHabit);
        parcel.writeInt(donationsDone);
        parcel.writeInt(daysSinceLastDonated);
    }
}

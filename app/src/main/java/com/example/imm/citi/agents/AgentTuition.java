package com.example.imm.citi.agents;

import android.os.Parcel;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/2/2017.
 */

public class AgentTuition extends LocalAgent {
    public String district;
    public ArrayList<String> areas, mediums, classes, subjects;

    public String school, college, university, occupation, profileLink;
    public int tuitionsDone;

    public AgentTuition(String name1, String email1, String phone1, String address1, String district1, ArrayList areas1,
                        ArrayList mediums1, ArrayList classes1, ArrayList subjects1, String school1, String college1, String university1,
                        String occupation1, String url1, int i) {

        name = name1;
        email = email1;
        phone = phone1;
        address = address1;

        district = district1;
        areas = areas1;
        mediums = mediums1;
        classes = classes1;
        subjects = subjects1;

        school = school1;
        college = college1;
        university = university1;
        occupation = occupation1;
        profileLink = url1;
        tuitionsDone = i;
    }

    public void addArea(String area) {
        areas.add(area);
    }

    public void addMedium(String medium) {
        mediums.add(medium);
    }

    public void addClass(String aClass) {
        classes.add(aClass);
    }

    public void addSubject(String subject) {
        subjects.add(subject);
    }





    public AgentTuition(Parcel in) {
        super(in);
        district = in.readString();
        areas = in.readArrayList(String.class.getClassLoader());
        mediums = in.readArrayList(String.class.getClassLoader());
        classes = in.readArrayList(String.class.getClassLoader());
        subjects = in.readArrayList(String.class.getClassLoader());

        school = in.readString();
        college = in.readString();
        university = in.readString();
        occupation = in.readString();
        profileLink = in.readString();
        tuitionsDone = in.readInt();
    }

    public static final Creator<AgentTuition> CREATOR = new Creator<AgentTuition>() {
        @Override
        public AgentTuition createFromParcel(Parcel in) {
            return new AgentTuition(in);
        }

        @Override
        public AgentTuition[] newArray(int size) {
            return new AgentTuition[size];
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
        parcel.writeList(areas);
        parcel.writeList(mediums);
        parcel.writeList(classes);
        parcel.writeList(subjects);

        parcel.writeString(school);
        parcel.writeString(college);
        parcel.writeString(university);
        parcel.writeString(occupation);
        parcel.writeString(profileLink);
        parcel.writeInt(tuitionsDone);
    }
}

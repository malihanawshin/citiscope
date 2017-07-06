package com.example.imm.citi.agents;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/2/2017.
 */

public class AgentDoctor extends Agent {
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
}

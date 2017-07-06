package com.example.imm.citi.agents;

/**
 * Created by Sujoy on 7/2/2017.
 */

public class AgentBloodDonation extends Agent {
    String district, bloodType;

    String smokingHabit;
    int donationsDone, daysSinceLastDonated;

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
}

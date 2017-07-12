package com.example.imm.citi.technicalClasses;

import java.util.ArrayList;

/**
 * Created by Sujoy on 7/13/2017.
 */

public class UserAgentInput {
    public String id, service, ownerEmail, name, email, phone, address;

    public String district;

    public String bloodType, smokingHabit, donationNo, lastDonated;
    public String aptArea, aptType, aptPrice, aptSize, aptFloor, aptRoom;

    public UserAgentInput(String id1, String service1, String name1, String email1, String phone1, String address1){
        id = id1;
        service = service1;
        ownerEmail = User.Email;

        name = name1;
        email = email1;
        phone = phone1;
        address = address1;
    }

    public void addBloodDonationAttributes(String district1, String bloodType1, String smokingHabit1,
                                           String donationNo1, String lastDonated1){
        district = district1;
        bloodType = bloodType1;
        smokingHabit = smokingHabit1;
        donationNo = donationNo1;
        lastDonated = lastDonated1;
    }


    public void addApartmentRentingAttributes(String district1, String aptArea1, String aptType1,
                                              String aptPrice1, String aptSize1, String aptFloor1, String aptRoom1){
        district = district1;
        aptArea = aptArea1;
        aptType = aptType1;
        aptPrice = aptPrice1;
        aptSize = aptSize1;
        aptFloor = aptFloor1;
        aptRoom = aptRoom1;
    }

    public ArrayList<String> getApartmentRentingInput(ArrayList<String> arr){
        arr = getBasicInput(arr);
        arr.add(district);
        arr.add(aptArea);
        arr.add(aptType);
        arr.add(aptPrice);
        arr.add(aptSize);
        arr.add(aptFloor);
        arr.add(aptRoom);
        return arr;
    }

    public ArrayList<String> getBloodDonationInput(ArrayList<String> arr){
        arr = getBasicInput(arr);
        arr.add(district);
        arr.add(bloodType);
        arr.add(smokingHabit);
        arr.add(donationNo);
        arr.add(lastDonated);
        return arr;
    }

    private ArrayList<String> getBasicInput(ArrayList<String> arr){
        arr.add(id);
        arr.add(service);
        arr.add(ownerEmail);

        arr.add(name);
        arr.add(email);
        arr.add(phone);
        arr.add(address);
        return arr;
    }
}

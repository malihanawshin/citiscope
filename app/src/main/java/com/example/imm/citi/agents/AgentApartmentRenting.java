package com.example.imm.citi.agents;

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
}

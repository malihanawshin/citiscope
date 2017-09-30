package com.example.imm.citi.activities;

import java.util.Comparator;

public class ServiceFeature {

    String serviceName;
    String imageURL;
    int popularity;

    public ServiceFeature(String name, String image, int pop){
        this.serviceName = name;
        this.imageURL = image;
        this.popularity = pop;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return serviceName + " " + popularity;
    }


    public static Comparator<ServiceFeature> serviceNameComparator = new Comparator<ServiceFeature>() {
        @Override
        public int compare(ServiceFeature sf1, ServiceFeature sf2) {
            return sf1.serviceName.compareTo(sf2.getServiceName());
        }
    };

    public static Comparator<ServiceFeature> servicePopularityComparator = new Comparator<ServiceFeature>() {
        @Override
        public int compare(ServiceFeature sf1, ServiceFeature sf2) {
            return sf2.popularity - sf1.popularity;
        }
    };
}

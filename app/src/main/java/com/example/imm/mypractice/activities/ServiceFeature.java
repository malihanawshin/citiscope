package com.example.imm.mypractice.activities;

public class ServiceFeature {

    String serviceName;
    String imageURL;

    public ServiceFeature(String name, String image){
        this.serviceName = name;
        this.imageURL = image;
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
        return serviceName;
    }
}

package com.example.imm.mypractice.activities;

public class ServiceFeature {

    String serviceName;
    int imageURL;

    public ServiceFeature(String name, int image){
        this.serviceName = name;
        this.imageURL = image;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getImageURL() {
        return imageURL;
    }

    public void setImageURL(int imageURL) {
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

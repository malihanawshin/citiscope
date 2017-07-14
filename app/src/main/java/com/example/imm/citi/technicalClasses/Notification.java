package com.example.imm.citi.technicalClasses;

/**
 * Created by imm on 7/14/2017.
 */

public class Notification {

    public String text, id, type, nomination;

    public Notification(){

    }

    public Notification(String id1, String type1, String nomination1){
        id = id1;
        type = type1;
        nomination = nomination1;
        setText();
    }

    private void setText() {
        if(type.equals("Removal")){
            text = "Your nomination " + nomination + " has been removed from the poll";
        }
        else {
            text = "Your nomination " + nomination + " has been added as a Service in the app";
        }
    }
}

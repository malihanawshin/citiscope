package com.example.imm.citi.technicalClasses;

import java.util.ArrayList;

/**
 * Created by Sujoy on 5/2/2017.
 */

public class FilterOption {
    public String filter;
    public ArrayList<String> options;

    public FilterOption(String fil, String op) {
        filter = fil;
        options = new ArrayList<>();
        options.add(op);
    }

    public void add(String op){
        options.add(op);
    }

    @Override
    public String toString() {
        return filter + ": " + options;
    }
}

package org.launchcode.cheesemvc.models;

public enum CheeseType {
    HARD ("HARD"),
    SOFT ("SOFT"),
    FAKE ("FAKE");

    private final String name;

    CheeseType(String name){
        this.name=name;
    }

    public String getName(){return name;}


}

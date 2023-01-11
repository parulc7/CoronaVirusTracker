package com.chandel.coronavirustracker.models;

public class LocationStats {
    private String country;
    private String state;
    private String casesForToday;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCasesForToday() {
        return casesForToday;
    }

    public void setCasesForToday(String casesForToday) {
        this.casesForToday = casesForToday;
    }

    public LocationStats(String country, String state, String cases){
        this.country = country;
        this.state = state;
        this.casesForToday = cases;
    }
}

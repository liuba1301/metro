package com.example.praktika3curs;

public class Station {
    private String id;
    private String name;
    private String address;
    private String region;
    
    public Station(String id, String name, String address, String region) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.region = region;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    @Override
    public String toString() {
        return name + " (" + region + ")";
    }
}

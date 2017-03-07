package com.smartparking.models;

/**
 * Created by Andrej on 27.2.2017..
 */
public class Parking {
    private int id;
    private ParkingStateEnum state;
    private String location;

    public Parking(int id, String state, String location) {
        this.id = id;
        this.state = ParkingStateEnum.valueOf(state);
        this.location = location;
    }

    public Parking(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingStateEnum getState() {
        return state;
    }

    public void setState(ParkingStateEnum state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}

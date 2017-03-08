package com.pavelic.smartparking.models;

import java.util.Date;

/**
 * Created by Andrej on 1.3.2017..
 */
public class State {
    private int parkingId;
    private ParkingStateEnum state;
    private Date date;

    public State() {

    }
    public State(int parkingId, String state, Date date) {
        this.parkingId = parkingId;
        this.state = ParkingStateEnum.valueOf(state);
        this.date = date;
    }

    public int getParkingId() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public ParkingStateEnum getState() {
        return state;
    }

    public void setState(ParkingStateEnum state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

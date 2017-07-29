package main.java.com.pavelic.smartparking.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Andrej on 1.3.2017..
 */
public class State {
    @SerializedName("ParkingId")
    private int parkingId;

    private ParkingStateEnum state;
    private Date date;

    @SerializedName("State")
    private String jsonState;

    @SerializedName("Date")
    private String jsonDate;

    public String getJsonState() {
        return jsonState;
    }

    public void setJsonState(String jsonState) {
        this.jsonState = jsonState;
    }

    public String getJsonDate() {
        return jsonDate;
    }

    public void setJsonDate(String jsonDate) {
        this.jsonDate = jsonDate;
    }

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

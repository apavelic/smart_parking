package main.java.com.pavelic.smartparking.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by epavean on 19.7.2017..
 */
public class ParkingSettings {
    @SerializedName("Price")
    private double price;

    public double getPrice() {
        return price;
    }

    public ParkingSettings() {
    }

    public ParkingSettings(double price) {
        this.price = price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

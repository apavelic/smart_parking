package main.java.com.pavelic.smartparking.models;

/**
 * Created by epavean on 19.7.2017..
 */
public class ParkingSettings {
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

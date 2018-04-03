package com.example.unsan.gpstracker;

/**
 * Created by Unsan on 23/3/18.
 */

public class Delivery {
    String startTime;
    String deliveryTime;
    String deliveryDate;
    String photo;
    long timeval;
    String customer;
    String destinationAddress;
    String startingAddress;

    boolean reached;


    String carNumber;
    String driverName;

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(String startingAddress) {
        this.startingAddress = startingAddress;
    }



    public String getCarNumber() {
        return carNumber;
    }

    public Delivery(String startTime, String deliveryTime,String deliveryDate, String photo, String customer, String destinationAddress, String startingAddress, String carNumber, String driverName) {
        this.startTime = startTime;
        this.deliveryTime = deliveryTime;
        this.deliveryDate=deliveryDate;
        this.photo = photo;

        this.customer = customer;
        this.destinationAddress = destinationAddress;
        this.startingAddress = startingAddress;

        this.carNumber = carNumber;
        this.driverName = driverName;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }




    public Delivery(String startTime, String deliveryTime, String photo, long timeval, String customer) {
        this.startTime = startTime;
        this.deliveryTime = deliveryTime;
        this.photo = photo;
        this.timeval = timeval;
        this.customer = customer;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getTimeval() {
        return timeval;
    }

    public void setTimeval(long timeval) {
        this.timeval = timeval;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public Delivery()
    {

    }
}

package com.example.unsan.gpstracker;

/**
 * Created by Unsan on 28/3/18.
 */

public class StartJourney {
    String carNumber;
    String customerAddress;

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    String date;

    public StartJourney(String carNumber, String date, String startTime, String startPosition,String customerAddress, boolean finished) {
        this.carNumber = carNumber;
        this.date = date;
        this.startTime = startTime;
        this.startPosition = startPosition;
        this.customerAddress=customerAddress;
        this.finished = finished;
    }

    String startTime;
    String startPosition;
    boolean finished;

}

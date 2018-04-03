package com.example.unsan.gpstracker;

import java.io.Serializable;

/**
 * Created by Unsan on 23/3/18.
 */

public class Customer implements Serializable {
    String carNumber;
    String CustomerName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String address;
    String phone;

    public String getCarNumber() {
        return carNumber;
    }

    public Customer(String carNumber, String customerName, String address, String phone) {
        this.carNumber = carNumber;
        this.CustomerName = customerName;
        this.address = address;
        this.phone = phone;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;

    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public Customer(String carNumber, String customerName) {
        this.carNumber = carNumber;
        CustomerName = customerName;

    }

}

package com.example.customer;

import com.example.address.Address;
import com.example.settings.PaymentTerm;


public class Customer {
    private String name, searchTerm, ICO, DIC, incoterms;
    private int number;
    private Address address;
    private PaymentTerm paymentTerm;

    public Customer(int number, String name, String searchTerm, String ICO, String DIC, String incoterms, Address address, PaymentTerm paymentTerm) {
        this.number = number;
        this.name = name;
        this.searchTerm = searchTerm;
        this.ICO = ICO;
        this.DIC = DIC;
        this.incoterms = incoterms;
        this.address = address;
        this.paymentTerm = paymentTerm;

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getICO() {
        return ICO;
    }

    public void setICO(String ICO) {
        this.ICO = ICO;
    }

    public String getDIC() {
        return DIC;
    }

    public void setDIC(String DIC) {
        this.DIC = DIC;
    }

    public String getIncoterms() {
        return incoterms;
    }

    public void setIncoterms(String incoterms) {
        this.incoterms = incoterms;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(PaymentTerm paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name +
                ", searchTerm='" + searchTerm +
                ", ICO='" + ICO +
                ", DIC='" + DIC +
                ", incoterms='" + incoterms +
                ", number=" + number +
                ", address=" + address +
                ", paymentTerm=" + paymentTerm +
                '}';
    }
}

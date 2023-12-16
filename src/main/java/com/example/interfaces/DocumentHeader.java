package com.example.interfaces;

import com.example.customer.Customer;
import com.example.settings.PaymentTerm;

public class DocumentHeader {
    private int id;
    private float price;
    private float vat;
    private Customer customer;

    public DocumentHeader() {

    }


    public DocumentHeader(float price, float vat) {
        this.price = price;
        this.vat = vat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getVat() {
        return vat;
    }

    public void setVat(float vat) {
        this.vat = vat;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

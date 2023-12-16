package com.example.customer;

import com.example.address.Address;
import com.example.settings.PaymentTerm;


public class Customer {
    private String name, incoterms;
    private int id;
    private Address deliveryAddress, invoiceAddress;
    private float discount;
    private PaymentTerm paymentTerm;
    private Role role;

    public Customer (){
        deliveryAddress = invoiceAddress = null;
        paymentTerm = PaymentTerm.NET_7;
    }

    public Customer(String name, Role role, String incoterms, Address deliveryAddress, Address invoiceAddress, float discount, PaymentTerm paymentTerm) {
        this.name = name;
        this.role = role;
        this.incoterms = incoterms;
        this.deliveryAddress = deliveryAddress;
        this.invoiceAddress = invoiceAddress;
        this.discount = discount;
        this.paymentTerm = paymentTerm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getIncoterms() {
        return incoterms;
    }

    public void setIncoterms(String incoterms) {
        this.incoterms = incoterms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(PaymentTerm paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Address getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(Address invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    @Override
    public String toString() {
        return "Zákazník " + name + " s rolí " + role.toString() + " a slevou " + discount + " %. Dodací adresa: " + deliveryAddress.toString() + ". Fakturační adresa: " + invoiceAddress.toString() + ". Incoterms: " + incoterms + ". Platební podmínky: " + paymentTerm.toString() + ".";
    }
}

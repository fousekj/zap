package com.example.SalesOrder;

import com.example.PurchaseOrder.PurchaseOrderItem;
import com.example.interfaces.DocumentHeader;
import com.example.settings.PaymentTerm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SalesOrder extends DocumentHeader {

    private ObservableList<SalesOrderItem> items;
    private String incoterms;
    private PaymentTerm paymentTerms;
    private int financialDocumentId;

    public SalesOrder(String incoterms, PaymentTerm paymentTerms, float price, float vat) {
        super(price, vat);
        items = FXCollections.observableArrayList();
        this.incoterms = incoterms;
        this.paymentTerms = paymentTerms;
        financialDocumentId = 0;
    }

    public SalesOrder() {
        items = FXCollections.observableArrayList();
        financialDocumentId = 0;
    }

    public void addItem(SalesOrderItem item) {
        items.add(item);
    }

    public void removeItem(SalesOrderItem item) {
        items.remove(item);
    }

    public ObservableList<SalesOrderItem> getItems() {
        return items;
    }

    public void setItems(ObservableList<SalesOrderItem> items) {
        this.items = items;
    }


    public String getIncoterms() {
        return incoterms;
    }

    public void setIncoterms(String incoterms) {
        this.incoterms = incoterms;
    }

    public PaymentTerm getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(PaymentTerm paymentTerms) {
        this.paymentTerms = paymentTerms;
    }
/*
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

 */

    public int getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocument(int financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    @Override
    public String toString() {
        return "SalesOrder{" +
                ", id=" + super.getId() +
                ", incoterms='" + incoterms + '\'' +
                ", price=" + super.getPrice() +
                ", vat=" + super.getVat() +
                '}';
    }
}

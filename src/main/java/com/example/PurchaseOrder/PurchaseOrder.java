package com.example.PurchaseOrder;

import com.example.FinancialDocument.FinancialDocument;
import com.example.customer.Customer;
import com.example.interfaces.DocumentHeader;
import com.example.settings.PaymentTerm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PurchaseOrder extends DocumentHeader {

    private ObservableList<PurchaseOrderItem> items;
    private String incoterms;
    private PaymentTerm paymentTerms;
    private int financialDocumentId;

    public PurchaseOrder(String incoterms, PaymentTerm paymentTerms, float price, float vat) {
        super(price, vat);
        items = FXCollections.observableArrayList();
        this.incoterms = incoterms;
        this.paymentTerms = paymentTerms;
        financialDocumentId = 0;
    }

    public PurchaseOrder() {
        items = FXCollections.observableArrayList();
        financialDocumentId = 0;
    }

    public void addItem(PurchaseOrderItem item) {
        items.add(item);
    }

    public void removeItem(PurchaseOrderItem item) {
        items.remove(item);
    }

    public ObservableList<PurchaseOrderItem> getItems() {
        return items;
    }

    public void setItems(ObservableList<PurchaseOrderItem> items) {
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

    public int getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocument(int financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
    }

    @Override
    public String toString() {
        return "Nákupní obj. číslo " + getId() + " v hodnotě " + getPrice() + " od zákazníka " + getCustomer().getName();
    }
}

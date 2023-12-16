package com.example.FinancialDocument;

import com.example.PurchaseOrder.PurchaseOrder;
import com.example.interfaces.DocumentHeader;
import com.example.settings.PaymentStatus;

public class FinancialDocument extends DocumentHeader {
    private String currency;
    private PaymentStatus paymentStatus;

    private DocumentHeader previousDocument;

    public FinancialDocument() {
        super();
    }

    public FinancialDocument(float price, float vat, String currency, PaymentStatus paymentStatus, DocumentHeader previousDocument) {
        super(price, vat);
        this.currency = currency;
        this.paymentStatus = paymentStatus;
        this.previousDocument = previousDocument;
    }

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public DocumentHeader getPreviousDocument() {
        return previousDocument;
    }
    public void setPreviousDocument(DocumentHeader previousDocument) {
        this.previousDocument = previousDocument;
    }

    @Override
    public String toString() {
        return "FinancialDocument{" +
                "document_id='" + super.getId() + '\'' +
                "currency='" + currency + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", previousDocument=" + previousDocument +
                '}';
    }
}

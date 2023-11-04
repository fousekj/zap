package com.example.settings;

public enum PaymentTerm {
    INVOICE ("Platba na fakturu"),
    CASH ("Platba v hotovosti"),
    CARD ("Platba kartou");

    private String label;

    PaymentTerm(String label) {
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }

}

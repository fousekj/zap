package com.example.settings;

public enum PaymentTerm {
    NET_7 ("Splatnost 7 dní", "Net 7"),
    NET_15 ("Splatnost 15 dní", "Net 14"),
    NET_30 ("Splatnost 30 dní", "Net 30"),
    NET_90 ("Splatnost 90 dní", "Net 90"),
    CASH ("Platba v hotovosti", "Cash"),
    CARD ("Platba kartou", "Card");

    private final String label, dbRepresentation;

    PaymentTerm(String label, String dbRepresentation) {
        this.label = label;
        this.dbRepresentation = dbRepresentation;
    }

    public static PaymentTerm fromDBString(String text) {
        for (PaymentTerm p : PaymentTerm.values()) {
            if (p.dbRepresentation.equalsIgnoreCase(text)) {
                return p;
            }
        }
        return null;
    }

    public static PaymentTerm fromLabel(String label) {
        for (PaymentTerm p : PaymentTerm.values()) {
            if (p.label.equalsIgnoreCase(label)) {
                return p;
            }
        }
        return null;
    }


    @Override
    public String toString(){
        return label;
    }

    public String getDbRepresentation(){
        return this.dbRepresentation;
    }

}

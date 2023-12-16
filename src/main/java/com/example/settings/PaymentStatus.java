package com.example.settings;

public enum PaymentStatus {
    WAITING_FOR_PAYMENT ("Čeká na platbu", "WAITING_FOR_PAYMENT"),
    PAID ("Zaplaceno", "PAID"),
    ACTIVE ("Aktivní", "ACTIVE"),
    CANCELED ("Zrušeno", "CANCELED");

    private final String label, dbRepresentation;

    PaymentStatus(String label, String dbRepresentation) {
        this.label = label;
        this.dbRepresentation = dbRepresentation;
    }

    public static PaymentStatus fromDBString(String text) {
        for (PaymentStatus p : PaymentStatus.values()) {
            if (p.dbRepresentation.equalsIgnoreCase(text)) {
                return p;
            }
        }
        return null;
    }

    public static PaymentStatus fromLabel(String label) {
        for (PaymentStatus p : PaymentStatus.values()) {
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

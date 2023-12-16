package com.example.customer;

public enum Role {
    CU ("Zákazník", "CU"),
    VE ("Dodavatel", "SU");

    private final String label, dbRepresentation;

    Role(String label, String dbRepresentation) {
        this.label = label;
        this.dbRepresentation = dbRepresentation;
    }
    public static Role fromDBString(String text) {
        for (Role r : Role.values()) {
            if (r.dbRepresentation.equalsIgnoreCase(text)) {
                return r;
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

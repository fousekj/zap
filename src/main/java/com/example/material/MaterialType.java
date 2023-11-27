package com.example.material;

public enum MaterialType {
    FINISHED_MATERIAL ("Hotový výrobek"),
    PACKAGING ("Obalový materiál"),
    PROMO ("Propagační materiál"),
    RESOURCE ("Surovina");

    private String label;

    MaterialType(String label) {
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }

}

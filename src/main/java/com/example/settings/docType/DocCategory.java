package com.example.settings.docType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public enum DocCategory {
    SALES_ORDER ("Zakázka"),
    DELIVERY ("Dodávka"),
    INVOICE ("Faktura");

    private String label;
    DocCategory(String label){
        this.label = label;
    }
    @Override
    public String toString(){
        return label;
    }

}

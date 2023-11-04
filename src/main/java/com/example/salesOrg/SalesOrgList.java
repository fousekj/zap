package com.example.salesOrg;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SalesOrgList {
    private ObservableList<SalesOrg> salesOrgs = FXCollections.observableArrayList();

    public ObservableList<SalesOrg> getSalesOrgs() {
        return salesOrgs;
    }

    public void addSalesOrg(SalesOrg salesOrg){
        salesOrgs.add(salesOrg);
    }

    public void removeSalesOrg(SalesOrg salesOrg){
        salesOrgs.remove(salesOrg);
    }
}

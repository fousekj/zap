package com.example.DB;

import com.example.salesOrg.SalesOrg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DB {
    private ObservableList<SalesOrg> salesOrgs = FXCollections.observableArrayList();

    public ObservableList<SalesOrg> getSalesOrgs() {
        return salesOrgs;
    }

    public void addSalesOrg(SalesOrg salesOrg){
        salesOrgs.add(salesOrg);
    }

    public void setSalesOrgs(ObservableList<SalesOrg> salesOrgObservableList){
        salesOrgs = salesOrgObservableList;
    }

    public void removeSalesOrg(SalesOrg salesOrg){
        salesOrgs.remove(salesOrg);
    }
}

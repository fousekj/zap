package com.example.DB;

import com.example.settings.docType.DocType;
import com.example.settings.salesOrg.SalesOrg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DB {
    private ObservableList<SalesOrg> salesOrgs = FXCollections.observableArrayList();
    private ObservableList<DocType> docTypes = FXCollections.observableArrayList();

    public ObservableList<SalesOrg> getSalesOrgs() {
        return salesOrgs;
    }

    public void setSalesOrgs(ObservableList<SalesOrg> salesOrgObservableList){
        salesOrgs = salesOrgObservableList;
    }

    // Je toto vůbec potřeba?
    /*public void addSalesOrg(SalesOrg salesOrg){
        salesOrgs.add(salesOrg);
    }
    public void removeSalesOrg(SalesOrg salesOrg){
        salesOrgs.remove(salesOrg);
    }
    */

    public ObservableList<DocType> getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(ObservableList<DocType> docTypes) {
        this.docTypes = docTypes;
    }
}

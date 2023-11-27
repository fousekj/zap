package com.example.DB;

import com.example.customer.Customer;
import com.example.settings.docType.DocType;
import com.example.settings.salesOrg.SalesOrg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DB {
    private static final String dbURL = "jdbc:sqlserver://dbsys.cs.vsb.cz\\\\SQLDB:52124;database=FOU0027;encrypt=true;trustServerCertificate=true;";
    private static Connection connection;
    private static final String user = "FOU0027";
    private static final String password = "xxxxx";

    static {
        try {
            connection = DriverManager.getConnection(dbURL, user, password);
            System.out.println("Connection successfull");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private ObservableList<SalesOrg> salesOrgs = FXCollections.observableArrayList();
    private ObservableList<DocType> docTypes = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    public ObservableList<Customer> getCustomers(){
        return customers;
    }

    public void setCustomers(ObservableList<Customer> customers) {
        this.customers = customers;
    }

    public ObservableList<SalesOrg> getSalesOrgs() {
        return salesOrgs;
    }

    public void setSalesOrgs(ObservableList<SalesOrg> salesOrgObservableList){
        salesOrgs = salesOrgObservableList;
    }


    public ObservableList<DocType> getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(ObservableList<DocType> docTypes) {
        this.docTypes = docTypes;
    }
}

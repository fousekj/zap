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
    private static final String password = "";

    static {
        try {
            connection = DriverManager.getConnection(dbURL, user, password);
            System.out.println("Connection successfull");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        //CachedRowSetImpl crs = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            //dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");
            //Create statement
            stmt = connection.createStatement();
            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);
            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            //crs = new CachedRowSetImpl();
            //crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            /*if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }*/
            //Close connection
            //dbDisconnect();
        }
        //Return CachedRowSet
        return resultSet;
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

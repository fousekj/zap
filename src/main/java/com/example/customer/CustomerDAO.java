package com.example.customer;

import com.example.DB.DB;
import com.example.main.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CustomerDAO {

    public static ObservableList<Customer> getAllCustomers(){

        String query = "SELECT * FROM customer_2";

        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsCustomers = DB.dbExecuteQuery(query);
            //Send ResultSet to the getEmployeeList method and get employee object
            //Return employee object
            return getCustomerList(rsCustomers);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);

        }
        return null;
    }

    private static ObservableList<Customer> getCustomerList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Customer> custList = FXCollections.observableArrayList();
        while (rs.next()) {
            Customer customer = new Customer();
            customer.setNumber(rs.getInt("BP_ID"));
            customer.setName(rs.getString("NAME"));
            customer.setIncoterms(rs.getString("INCOTERMS"));
            //Add employee to the ObservableList
            custList.add(customer);
        }
        //return empList (ObservableList of Employees)
        return custList;
    }
}

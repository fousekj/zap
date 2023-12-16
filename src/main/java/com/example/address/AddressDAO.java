package com.example.address;

import com.example.DB.DB;
import com.example.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAO {

    public static int insertAddress(Address address){

        String query = String.format("insert into Address\n" +
                "values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%S')",
                address.getName(), address.getStreet(), address.getHouseNum(), address.getCity(),
                address.getPostCode(), address.getCountry(), address.getEmail(), address.getPhone());

        try {
            ResultSet resultSet = DB.dbExecuteInsert(query);
            if (resultSet.next()){
                return resultSet.getInt(1);
            } else {
                throw new SQLException();
            }

        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }

        return 0;
    }

}

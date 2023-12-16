package com.example.customer;

import com.example.DB.DB;
import com.example.address.Address;
import com.example.address.AddressDAO;
import com.example.settings.PaymentTerm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Locale;

public class CustomerDAO {

    public static ObservableList<Customer> getAllCustomers(){

        String query = """
                SELECT bp.bp_id, bp.role, bp.name, bp.incoterms, bp.payment_terms, bp.discount,
                    ship.name, ship.street, ship.city,  ship.house_num, ship.post_code, ship.country, ship.email, ship.phone,
                    invoic.name, invoic.street, invoic.city,  invoic.house_num, invoic.post_code, invoic.country, invoic.email, invoic.phone
                FROM Business_Partner bp
                JOIN Address ship ON ship.address_id = bp.delivery_address_id
                JOIN Address invoic ON invoic.address_id = bp.invoice_address_id""";

        try {
            ResultSet rsCustomers = DB.dbExecuteSelect(query);
            return getCustomerList(rsCustomers);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    private static ObservableList<Customer> getCustomerList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Customer> custList = FXCollections.observableArrayList();

        while (rs.next()) {
            Customer customer = new Customer();
            Address shipAddress = new Address();
            Address invoicAddress = new Address();
            customer.setId(rs.getInt(1));
            customer.setRole(Role.fromDBString(rs.getString(2)));
            customer.setName(rs.getString(3));
            customer.setIncoterms(rs.getString(4));
            customer.setPaymentTerm(PaymentTerm.fromDBString(rs.getString(5)));
            customer.setDiscount(rs.getFloat(6));


            shipAddress.setName(rs.getString(7));
            shipAddress.setStreet(rs.getString(8));
            shipAddress.setCity(rs.getString(9));
            shipAddress.setHouseNum(rs.getString(10));
            shipAddress.setPostCode(rs.getString(11));
            shipAddress.setCountry(rs.getString(12));
            shipAddress.setEmail(rs.getString(13));
            shipAddress.setPhone(rs.getString(14));
            customer.setDeliveryAddress(shipAddress);

            invoicAddress.setName(rs.getString(15));
            invoicAddress.setStreet(rs.getString(16));
            invoicAddress.setCity(rs.getString(17));
            invoicAddress.setHouseNum(rs.getString(18));
            invoicAddress.setPostCode(rs.getString(19));
            invoicAddress.setCountry(rs.getString(20));
            invoicAddress.setEmail(rs.getString(21));
            invoicAddress.setPhone(rs.getString(22));
            customer.setInvoiceAddress(invoicAddress);

            custList.add(customer);
        }
        return custList;
    }

    public static Customer getCustomer(int id){

        String query = String.format("""
                SELECT bp.bp_id, bp.role, bp.name, bp.incoterms, bp.payment_terms, bp.discount,
                    ship.name, ship.street, ship.city,  ship.house_num, ship.post_code, ship.country, ship.email, ship.phone,
                    invoic.name, invoic.street, invoic.city,  invoic.house_num, invoic.post_code, invoic.country, invoic.email, invoic.phone
                FROM Business_Partner bp
                JOIN Address ship ON ship.address_id = bp.delivery_address_id
                JOIN Address invoic ON invoic.address_id = bp.invoice_address_id
                where bp.bp_id = %d""", id);

        try {
            ResultSet rsCustomers = DB.dbExecuteSelect(query);
            return getCustomer(rsCustomers);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    private static Customer getCustomer(ResultSet rs) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer();
        Address shipAddress = new Address();
        Address invoicAddress = new Address();
        if (rs.next()) {


            customer.setId(rs.getInt(1));
            customer.setRole(Role.fromDBString(rs.getString(2)));
            customer.setName(rs.getString(3));
            customer.setIncoterms(rs.getString(4));
            customer.setPaymentTerm(PaymentTerm.fromDBString(rs.getString(5)));
            customer.setDiscount(rs.getFloat(6));


            shipAddress.setName(rs.getString(7));
            shipAddress.setStreet(rs.getString(8));
            shipAddress.setCity(rs.getString(9));
            shipAddress.setHouseNum(rs.getString(10));
            shipAddress.setPostCode(rs.getString(11));
            shipAddress.setCountry(rs.getString(12));
            shipAddress.setEmail(rs.getString(13));
            shipAddress.setPhone(rs.getString(14));
            customer.setDeliveryAddress(shipAddress);

            invoicAddress.setName(rs.getString(15));
            invoicAddress.setStreet(rs.getString(16));
            invoicAddress.setCity(rs.getString(17));
            invoicAddress.setHouseNum(rs.getString(18));
            invoicAddress.setPostCode(rs.getString(19));
            invoicAddress.setCountry(rs.getString(20));
            invoicAddress.setEmail(rs.getString(21));
            invoicAddress.setPhone(rs.getString(22));
            customer.setInvoiceAddress(invoicAddress);


        }
        return customer;
    }


    public static int insertCustomer(Customer customer){

        int deliveryAddressPK = AddressDAO.insertAddress(customer.getDeliveryAddress());
        int invoiceAddressPK = AddressDAO.insertAddress(customer.getInvoiceAddress());

        String query = String.format(Locale.US, "insert into Business_Partner\n" +
                "values ('%s', '%s', '%s', '%s', %.2f, %d,\n" +
                "        %d, current_timestamp, current_timestamp)", customer.getRole().getDbRepresentation(),
                customer.getName(), customer.getIncoterms(), customer.getPaymentTerm().getDbRepresentation(), customer.getDiscount(),
                deliveryAddressPK, invoiceAddressPK);

        try {
            ResultSet resultSet = DB.dbExecuteInsert(query);
            if (resultSet.next()){
                return resultSet.getInt(1);
            } else {
                throw new SQLException();
            }
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
            return 0;
        }

    }
}

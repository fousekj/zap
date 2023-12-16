package com.example.SalesOrder;

import com.example.DB.DB;
import com.example.PurchaseOrder.PurchaseOrder;
import com.example.PurchaseOrder.PurchaseOrderItem;
import com.example.customer.CustomerDAO;
import com.example.material.MaterialDAO;
import com.example.settings.PaymentTerm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;

public class SalesOrderDAO {

    public static ObservableList<SalesOrder> getAllSalesOrders(){

        String query = """
                select document_id, incoterms, payment_terms, price, vat, sold_to, fi_document_id
                from Sales_Order_Header""";

        try {
            ResultSet rsPurchaseOrders = DB.dbExecuteSelect(query);
            return getSalesOrderList(rsPurchaseOrders);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    public static SalesOrder getSalesOrderFromId(int id){

        String query = """
                select document_id, incoterms, payment_terms, price, vat, sold_to, fi_document_id
                from Sales_Order_Header
                where document_id = %d""".formatted(id);

        try {
            ResultSet rsSalesOrders = DB.dbExecuteSelect(query);
            return getSalesOrder(rsSalesOrders);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    private static SalesOrder getSalesOrder(ResultSet rs) throws SQLException, ClassNotFoundException {
        SalesOrder salesOrder = new SalesOrder();

        if (rs.next()) {
            salesOrder.setId(rs.getInt(1));
            salesOrder.setIncoterms(rs.getString(2));
            salesOrder.setPaymentTerms(PaymentTerm.fromDBString(rs.getString(3)));
            salesOrder.setPrice(rs.getFloat(4));
            salesOrder.setVat(rs.getFloat(5));
            salesOrder.setCustomer(CustomerDAO.getCustomer(rs.getInt(6)));
            salesOrder.setFinancialDocument(rs.getInt(7));
            for (SalesOrderItem item : Objects.requireNonNull(getItemsForSalesOrder(salesOrder.getId()))){
                salesOrder.addItem(item);
            }

        }
        return salesOrder;
    }

    private static ObservableList<SalesOrder> getSalesOrderList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<SalesOrder> salesOrders = FXCollections.observableArrayList();

        while (rs.next()) {
            SalesOrder salesOrder = new SalesOrder();
            salesOrder.setId(rs.getInt(1));
            salesOrder.setIncoterms(rs.getString(2));
            salesOrder.setPaymentTerms(PaymentTerm.fromDBString(rs.getString(3)));
            salesOrder.setPrice(rs.getFloat(4));
            salesOrder.setVat(rs.getFloat(5));
            salesOrder.setCustomer(CustomerDAO.getCustomer(rs.getInt(6)));
            salesOrder.setFinancialDocument(rs.getInt(7));
            for (SalesOrderItem item : Objects.requireNonNull(getItemsForSalesOrder(salesOrder.getId()))){
                salesOrder.addItem(item);
            }
            salesOrders.add(salesOrder);

        }
        return salesOrders;
    }

    public static ObservableList<SalesOrderItem> getItemsForSalesOrder(int documentId){

        String query = String.format(Locale.US,"""
                select posnr, quantity, price, vat, material_id
                from Sales_Order_Item
                where document_id = %d""", documentId);

        try {
            ResultSet rsSalesOrderItems = DB.dbExecuteSelect(query);
            return getSalesOrderItemList(rsSalesOrderItems);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    private static ObservableList<SalesOrderItem> getSalesOrderItemList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<SalesOrderItem> salesOrderItems = FXCollections.observableArrayList();

        while (rs.next()) {
            SalesOrderItem salesOrderItem = new SalesOrderItem();
            salesOrderItem.setPosnr(rs.getInt(1));
            salesOrderItem.setQuantity(rs.getFloat(2));
            salesOrderItem.setPrice(rs.getFloat(3));
            salesOrderItem.setVat(rs.getFloat(4));
            salesOrderItem.setMaterial(MaterialDAO.getMaterialFromId(rs.getInt(5)));
            salesOrderItems.add(salesOrderItem);

        }
        return salesOrderItems;
    }


    public static int insertSalesOrder(SalesOrder salesOrder){

        String query = String.format(Locale.US,
                """
                insert into Sales_Order_Header (sold_to, incoterms, payment_terms, price, vat, created_at,
                         updated_at, fi_document_id)
                values (%d, '%s', '%s', %.2f, %.2f, GETDATE(), GETDATE(), null)""",
                salesOrder.getCustomer().getId(), salesOrder.getIncoterms(), salesOrder.getPaymentTerms().getDbRepresentation(),
                salesOrder.getPrice(), salesOrder.getVat());

        try {
            ResultSet resultSet = DB.dbExecuteInsert(query);
            if (resultSet.next()){
                insertSalesOrderItems(salesOrder.getItems() ,resultSet.getInt(1));
                return resultSet.getInt(1);
            } else {
                throw new SQLException();
            }
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
            return 0;
        }

    }
    public static void insertSalesOrderItems(ObservableList<SalesOrderItem> items, int documentId){
        try {
            for (SalesOrderItem item : items){
                String insertQuery = String.format(Locale.US,
                        """
                        insert into Sales_Order_Item (document_id, posnr, quantity, price, vat, material_id)
                                                values (%d, %d, %.2f, %.2f, %.2f, %d)""",
                        documentId, item.getPosnr(), item.getQuantity(), item.getPrice(), item.getVat(), item.getMaterial().getId());
                ResultSet resultSet = DB.dbExecuteInsert(insertQuery);
                String updateQuery = String.format(Locale.US,
                        """
                                update Material
                                     set qty_available = qty_available - %.2f
                                     where material_id = %d;""",
                        item.getQuantity(), item.getMaterial().getId());
                DB.dbExecuteInsert(updateQuery);
                if (!resultSet.next()){
                    throw new SQLException();
                }
            }
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }

    }
}

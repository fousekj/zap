package com.example.PurchaseOrder;

import com.example.DB.DB;
import com.example.customer.CustomerDAO;
import com.example.material.MaterialDAO;
import com.example.settings.PaymentTerm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;

public class PurchaseOrderDAO {

    public static ObservableList<PurchaseOrder> getAllPurchaseOrders(){

        String query = """
                select document_id, incoterms, payment_terms, price, vat, vendor, fi_document_id
                from Purchase_Order_Header""";

        try {
            ResultSet rsPurchaseOrders = DB.dbExecuteSelect(query);
            return getPurchaseOrderList(rsPurchaseOrders);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    public static PurchaseOrder getPurchaseOrderFromId(int id){

        String query = """
                select document_id, incoterms, payment_terms, price, vat, vendor, fi_document_id
                from Purchase_Order_Header
                where document_id = %d""".formatted(id);

        try {
            ResultSet rsPurchaseOrders = DB.dbExecuteSelect(query);
            return getPurchaseOrder(rsPurchaseOrders);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    private static PurchaseOrder getPurchaseOrder(ResultSet rs) throws SQLException, ClassNotFoundException {
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        if (rs.next()) {
            purchaseOrder.setId(rs.getInt(1));
            purchaseOrder.setIncoterms(rs.getString(2));
            purchaseOrder.setPaymentTerms(PaymentTerm.fromDBString(rs.getString(3)));
            purchaseOrder.setPrice(rs.getFloat(4));
            purchaseOrder.setVat(rs.getFloat(5));
            purchaseOrder.setCustomer(CustomerDAO.getCustomer(rs.getInt(6)));
            purchaseOrder.setFinancialDocument(rs.getInt(7));
            for (PurchaseOrderItem item : Objects.requireNonNull(getItemsForPurchaseOrder(purchaseOrder.getId()))){
                purchaseOrder.addItem(item);
            }

        }
        return purchaseOrder;
    }

    private static ObservableList<PurchaseOrder> getPurchaseOrderList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<PurchaseOrder> purchaseOrders = FXCollections.observableArrayList();

        while (rs.next()) {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setId(rs.getInt(1));
            purchaseOrder.setIncoterms(rs.getString(2));
            purchaseOrder.setPaymentTerms(PaymentTerm.fromDBString(rs.getString(3)));
            purchaseOrder.setPrice(rs.getFloat(4));
            purchaseOrder.setVat(rs.getFloat(5));
            purchaseOrder.setCustomer(CustomerDAO.getCustomer(rs.getInt(6)));
            purchaseOrder.setFinancialDocument(rs.getInt(7));
            for (PurchaseOrderItem item : Objects.requireNonNull(getItemsForPurchaseOrder(purchaseOrder.getId()))){
                purchaseOrder.addItem(item);
            }
            purchaseOrders.add(purchaseOrder);

        }
        return purchaseOrders;
    }

    public static ObservableList<PurchaseOrderItem> getItemsForPurchaseOrder(int documentId){

        String query = String.format(Locale.US,"""
                select posnr, quantity, price, vat, material_id
                from Purchase_Order_Item
                where document_id = %d""", documentId);

        try {
            ResultSet rsPurchaseOrderItems = DB.dbExecuteSelect(query);
            return getPurchaseOrderItemList(rsPurchaseOrderItems);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    private static ObservableList<PurchaseOrderItem> getPurchaseOrderItemList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<PurchaseOrderItem> purchaseOrderItems = FXCollections.observableArrayList();

        while (rs.next()) {
            PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
            purchaseOrderItem.setPosnr(rs.getInt(1));
            purchaseOrderItem.setQuantity(rs.getFloat(2));
            purchaseOrderItem.setPrice(rs.getFloat(3));
            purchaseOrderItem.setVat(rs.getFloat(4));
            purchaseOrderItem.setMaterial(MaterialDAO.getMaterialFromId(rs.getInt(5)));
            purchaseOrderItems.add(purchaseOrderItem);

        }
        return purchaseOrderItems;
    }


    public static int insertPurchaseOrder(PurchaseOrder purchaseOrder){

        String query = String.format(Locale.US,
                """
                insert into Purchase_Order_Header (vendor, incoterms, payment_terms, price, vat, created_at,
                         updated_at, fi_document_id)
                values (%d, '%s', '%s', %.2f, %.2f, GETDATE(), GETDATE(), null)""",
                purchaseOrder.getCustomer().getId(), purchaseOrder.getIncoterms(), purchaseOrder.getPaymentTerms().getDbRepresentation(),
                purchaseOrder.getPrice(), purchaseOrder.getVat());

        try {
            ResultSet resultSet = DB.dbExecuteInsert(query);
            if (resultSet.next()){
                insertandUpdatePurchaseOrderItems(purchaseOrder.getItems() ,resultSet.getInt(1));
                return resultSet.getInt(1);
            } else {
                throw new SQLException();
            }
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
            return 0;
        }

    }
    public static void insertandUpdatePurchaseOrderItems(ObservableList<PurchaseOrderItem> items, int documentId){
        try {

            for (PurchaseOrderItem item : items){
                String insertQuery = String.format(Locale.US,
                        """
                        insert into Purchase_Order_Item (document_id, posnr, quantity, price, vat, material_id)
                                                values (%d, %d, %.2f, %.2f, %.2f, %d)""",
                        documentId, item.getPosnr(), item.getQuantity(), item.getPrice(), item.getVat(), item.getMaterial().getId());
                ResultSet resultSet = DB.dbExecuteInsert(insertQuery);
                String updateQuery = String.format(Locale.US,
                        """
                                update Material
                                     set qty_available = qty_available + %.2f
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

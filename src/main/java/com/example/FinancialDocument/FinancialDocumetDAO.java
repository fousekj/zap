package com.example.FinancialDocument;

import com.example.DB.DB;
import com.example.PurchaseOrder.PurchaseOrder;
import com.example.PurchaseOrder.PurchaseOrderDAO;
import com.example.PurchaseOrder.PurchaseOrderItem;
import com.example.customer.CustomerDAO;
import com.example.interfaces.DocumentHeader;
import com.example.material.MaterialDAO;
import com.example.settings.PaymentStatus;
import com.example.settings.PaymentTerm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;

public class FinancialDocumetDAO {
    public static ObservableList<FinancialDocument> getAllFinancialDocuments() {

        String query = """
                select document_id, amount, vat, currency, payment_status, bill_to, sales_order_id, purchase_order_id
                from Financial_Document""";

        try {
            ResultSet rsFinancialDocuments = DB.dbExecuteSelect(query);
            return getFinancialDocumentList(rsFinancialDocuments);
        } catch (Exception e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    private static ObservableList<FinancialDocument> getFinancialDocumentList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<FinancialDocument> financialDocuments = FXCollections.observableArrayList();

        while (rs.next()) {
            FinancialDocument financialDocument = new FinancialDocument();
            financialDocument.setId(rs.getInt(1));
            financialDocument.setPrice(rs.getFloat(2));
            financialDocument.setVat(rs.getFloat(3));
            financialDocument.setCurrency(rs.getString(4));
            financialDocument.setPaymentStatus(PaymentStatus.fromDBString(rs.getString(5)));
            financialDocument.setCustomer(CustomerDAO.getCustomer(rs.getInt(6)));
            if (rs.getInt(7) != 0){
                financialDocument.setPreviousDocument(null);
            } else {
                financialDocument.setPreviousDocument(PurchaseOrderDAO.getPurchaseOrderFromId(rs.getInt(8)));
            }

            financialDocuments.add(financialDocument);

        }
        return financialDocuments;
    }


    public static int insertFinancialDocumentFromPO(FinancialDocument financialDocument) {

        String query = String.format(Locale.US,
                """
                insert into Financial_Document (amount, vat, currency, payment_status, bill_to, sales_order_id,
                              purchase_order_id)
                        values (%.2f, %.2f, '%s', '%s', %d, null, %d)""",
                financialDocument.getPrice(), financialDocument.getVat(), financialDocument.getCurrency().toUpperCase(),
                financialDocument.getPaymentStatus().getDbRepresentation(), financialDocument.getCustomer().getId(), financialDocument.getPreviousDocument().getId());

        try {
            ResultSet resultSet = DB.dbExecuteInsert(query);
            if (resultSet.next()) {
                //MaterialDAO.decreaseMaterialStock( financialDocument.getPreviousDocument().getItems());
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

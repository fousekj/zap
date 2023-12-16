module com.example.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;


    opens com.example.main to javafx.fxml;
    exports com.example.main;
    exports com.example.DB;
    opens com.example.DB to javafx.fxml;
    exports com.example.customer;
    opens com.example.customer to javafx.fxml;
    exports com.example.material;
    opens com.example.material to javafx.fxml;
    exports com.example.PurchaseOrder;
    opens com.example.PurchaseOrder to javafx.fxml;
    exports com.example.FinancialDocument;
    opens com.example.FinancialDocument to javafx.fxml;
    exports com.example.SalesOrder;
    opens com.example.SalesOrder to javafx.fxml;

}
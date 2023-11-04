module com.example.main {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.main to javafx.fxml;
    exports com.example.main;
    exports com.example.settings.salesOrg;
    opens com.example.settings.salesOrg to javafx.fxml;
    exports com.example.DB;
    opens com.example.DB to javafx.fxml;
    exports com.example.settings.docType;
    opens com.example.settings.docType to javafx.fxml;
}
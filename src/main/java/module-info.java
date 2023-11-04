module com.example.main {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.main to javafx.fxml;
    exports com.example.main;
    exports com.example.salesOrg;
    opens com.example.salesOrg to javafx.fxml;
    exports com.example.DB;
    opens com.example.DB to javafx.fxml;
}
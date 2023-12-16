package com.example.main;

import com.example.FinancialDocument.FinancialDocumentController;
import com.example.SalesOrder.SalesOrderController;
import com.example.customer.CustomerController;
import com.example.interfaces.Alertable;
import com.example.material.MaterialController;
import com.example.PurchaseOrder.PurchaseOrderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable, Alertable {

    @FXML
    private MenuBar menu;
    @FXML
    private MenuItem salesOrgMenu;
    @FXML
    private MenuItem docTypeMenu;

    @FXML
    private BorderPane borderPaneScreen;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleCustomerAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CustomerController.class.getResource("displayAllCustomers.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            borderPaneScreen.setCenter(anchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void handleMaterialAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MaterialController.class.getResource("displayAllMaterials.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            borderPaneScreen.setCenter(anchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void handlePurchaseOrderListAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PurchaseOrderController.class.getResource("displayAllPurchaseOrders.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            borderPaneScreen.setCenter(anchorPane);
        } catch (Exception e){
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void handleFinancialDocumentAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FinancialDocumentController.class.getResource("displayAllFinancialDocuments.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            borderPaneScreen.setCenter(anchorPane);
        } catch (Exception e){
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void handleSalesOrderAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SalesOrderController.class.getResource("displayAllSalesOrders.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            borderPaneScreen.setCenter(anchorPane);
        } catch (Exception e){
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
        }
    }
}
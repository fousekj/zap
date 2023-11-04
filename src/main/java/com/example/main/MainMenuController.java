package com.example.main;

import com.example.customer.CustomerController;
import com.example.interfaces.Alertable;
import com.example.settings.docType.DocTypeController;
import com.example.settings.salesOrg.SalesOrgController;
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
    public AnchorPane anchorPaneCreateDocType;
    @FXML
    private MenuBar menu;
    @FXML
    private MenuItem salesOrgMenu;
    @FXML
    private MenuItem docTypeMenu;

    @FXML
    private BorderPane borderPaneScreen;

    private boolean isUserLoggedOn;
    private boolean isUserAdmin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //validace pro zobrazení konfigurace
    }

    @FXML
    protected void handleSalesOrgAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SalesOrgController.class.getResource("displayAllSalesOrgs.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            borderPaneScreen.setCenter(anchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
        }

    }

    public void handleDocTypeAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DocTypeController.class.getResource("displayAllDocTypes.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            borderPaneScreen.setCenter(anchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
        }
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

    public void createMaterial(ActionEvent actionEvent) {
    }
}
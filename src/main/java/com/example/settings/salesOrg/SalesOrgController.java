package com.example.settings.salesOrg;

import com.example.DB.DB;
import com.example.address.Address;
import com.example.interfaces.Validatable;
import com.example.main.App;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SalesOrgController implements Initializable, Validatable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfKey;
    @FXML
    private TextField tfStreet;
    @FXML
    private TextField tfHouseNum;
    @FXML
    private TextField tfCity;
    @FXML
    private TextField tfCountry;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPhone;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ListView<SalesOrg> salesOrgListView = new ListView<>();
    private ObservableList<SalesOrg> salesOrgList;

    private DB database;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = App.getDatabase();
        salesOrgList = database.getSalesOrgs();

        salesOrgListView.setItems(salesOrgList);
        if (salesOrgList.isEmpty()){
            salesOrgListView.getSelectionModel().select(0);
        }
    }

    public void handleCreateSalesOrgAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SalesOrgController.class.getResource("createSalesOrg.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void handleCreateNewSalesOrgAction(ActionEvent event) {
        if (checkIfSalesOrgExists(tfKey.getText())){
            showAlert(Alert.AlertType.WARNING, "Prodejní organizace s daným klíčem již existuje");
        } else {
            try {
                Address address = new Address(tfStreet.getText(), tfHouseNum.getText(), tfCity.getText(), tfCountry.getText(), tfEmail.getText(), tfPhone.getText());
                SalesOrg newSalesOrg = new SalesOrg(tfName.getText(), tfName.getText(), address);
                salesOrgList.add(newSalesOrg);
                database.setSalesOrgs(salesOrgList);
                showAlert(Alert.AlertType.CONFIRMATION, "Nová prodejní organizace úspěšně vytvořena");
                FXMLLoader fxmlLoader = new FXMLLoader(SalesOrgController.class.getResource("displayAllSalesOrgs.fxml"));
                AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
                BorderPane parent = (BorderPane) anchorPane.getParent();
                parent.setCenter(newAnchorPane);

            } catch (Exception e){
                showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se vytvořit novou prodejní organizaci");
            }
        }

    }

    private boolean checkIfSalesOrgExists (String key) {
        for (SalesOrg s : salesOrgList) {
            if (Objects.equals(s.getKey(), key)) {
                //showAlert(Alert.AlertType.ERROR, "Prodejní organizace s daným klíčem již existuje");
                return true;
            }
        }
        return false;
    }

    private void showAlert(Alert.AlertType alertType, String text) {
        Alert alert = new Alert(alertType, text);
        alert.show();
    }


    @Override
    public void validateBeforeCreation() {

    }
}

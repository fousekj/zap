package com.example.salesOrg;

import com.example.address.Address;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SalesOrgController implements Initializable {

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
    private AnchorPane anchorPaneDocType;
    private SalesOrgList salesOrgList = new SalesOrgList();

    @FXML
    private ListView<SalesOrg> salesOrgListView;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        salesOrgListView.setItems(salesOrgList.getSalesOrgs());
        if (salesOrgList.getSalesOrgs().isEmpty()){
            salesOrgListView.getSelectionModel().select(0);
        }
    }

    public void handleCreateDocTypeAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SalesOrgController.class.getResource("createSalesOrg.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPaneDocType.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
            alert.show();
        }
    }

    public void handleCreateNewDocTypeAction(ActionEvent event) {
        if (checkIfSalesOrgExists(tfKey.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Prodejní organizace s daným klíčem již existuje");
            alert.show();
        } else {
            try {
                Address address = new Address(tfStreet.getText(), tfHouseNum.getText(), tfCity.getText(), tfCountry.getText(), tfEmail.getText(), tfPhone.getText());
                SalesOrg newSalesOrg = new SalesOrg(tfName.getText(), tfName.getText(), address);
                salesOrgList.addSalesOrg(newSalesOrg);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Nová prodejní organizace úspěšně vytvořena");
                alert.show();
                FXMLLoader fxmlLoader = new FXMLLoader(SalesOrgController.class.getResource("displayAllSalesOrgs.fxml"));
                AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
                BorderPane parent = (BorderPane) anchorPaneDocType.getParent();
                parent.setCenter(anchorPane);

            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
                alert.show();
            }
        }

    }

    private boolean checkIfSalesOrgExists(String key){
        for (SalesOrg s : salesOrgList.getSalesOrgs()) {
            if (Objects.equals(s.getKey(), key)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Prodejní organizace s daným klíčem již existuje");
                alert.show();
                return true;
            }
        }
        return false;
    }


}

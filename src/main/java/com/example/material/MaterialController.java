package com.example.material;

import com.example.DB.DB;
import com.example.address.Address;
import com.example.interfaces.Alertable;
import com.example.interfaces.Validatable;
import com.example.main.App;
import com.example.settings.salesOrg.SalesOrg;
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

public class MaterialController implements Initializable, Validatable, Alertable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfKey;
    @FXML
    private TextField tfBruttoWeight;
    @FXML
    private TextField tfNettoWeigt;
    @FXML
    private TextField tfBaseUOM;
    @FXML
    private TextField tfVAT;
    @FXML
    private TextField tfEAN;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ListView<Material> materialListView = new ListView<>();
    private ObservableList<Material> salesOrgList;

    private DB database;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = App.getDatabase();
        //materialListView = database.getSalesOrgs();

        /*salesOrgListView.setItems(salesOrgList);
        if (salesOrgList.isEmpty()){
            salesOrgListView.getSelectionModel().select(0);
        }*/
    }

    public void handleCreateSalesOrgAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MaterialController.class.getResource("createSalesOrg.fxml"));
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
               /* Address address = new Address(tfStreet.getText(), tfHouseNum.getText(), tfCity.getText(), tfCountry.getText(), tfEmail.getText(), tfPhone.getText());
                //SalesOrg newSalesOrg = new SalesOrg(tfName.getText(), tfName.getText(), address);
                salesOrgList.add(newSalesOrg);
                database.setSalesOrgs(salesOrgList);
                showAlert(Alert.AlertType.CONFIRMATION, "Nová prodejní organizace úspěšně vytvořena");
                FXMLLoader fxmlLoader = new FXMLLoader(MaterialController.class.getResource("displayAllSalesOrgs.fxml"));
                AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
                BorderPane parent = (BorderPane) anchorPane.getParent();
                parent.setCenter(newAnchorPane);
                */
            } catch (Exception e){
                showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se vytvořit novou prodejní organizaci");
            }
        }

    }

    private boolean checkIfSalesOrgExists (String key) {
        /*for (SalesOrg s : salesOrgList) {
            if (Objects.equals(s.getKey(), key)) {
                return true;
            }
        }*/
        return false;
    }

    @Override
    public void validateBeforeCreation() {

    }
}

package com.example.material;

import com.example.DB.DB;
import com.example.address.Address;
import com.example.customer.CustomerController;
import com.example.customer.CustomerDAO;
import com.example.interfaces.Alertable;
import com.example.interfaces.Validatable;
import com.example.main.App;
//import com.example.settings.salesOrg.SalesOrg;
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
    private TextField tfDescription, tfPrice, tfVatRate, tfBruttoWeight, tfNettoWeight, tfBaseUOM;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ListView<Material> materialListView = new ListView<>();
    private ObservableList<Material> materialList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        materialList = MaterialDAO.getAllMaterials();
        materialListView.setItems(materialList);
        if (materialList.isEmpty()){
            materialListView.getSelectionModel().select(0);
        }
    }

    public void handleCreateMaterial(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MaterialController.class.getResource("createMaterial.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void handleCreateNewMaterialAction(ActionEvent event) {

            try {
                Material newMaterial = new Material(tfDescription.getText(),
                        Float.parseFloat(tfPrice.getText()), tfBaseUOM.getText(),Float.parseFloat(tfVatRate.getText()),
                        Float.parseFloat(tfBruttoWeight.getText()), Float.parseFloat(tfNettoWeight.getText()));
                materialList.add(newMaterial);
                int newMaterialId = MaterialDAO.insertMaterial(newMaterial);
                showAlert(Alert.AlertType.CONFIRMATION, "Materiál byl úspěšně vytvořen pod číslem " + newMaterialId);
                FXMLLoader fxmlLoader = new FXMLLoader(MaterialController.class.getResource("displayAllMaterials.fxml"));
                AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
                BorderPane parent = (BorderPane) anchorPane.getParent();
                parent.setCenter(newAnchorPane);

            } catch (Exception e){
                showAlert(Alert.AlertType.ERROR, "Nepodařilo se vytvořit nový materiál");
            }


    }

    @Override
    public void validateBeforeCreation() {

    }
}

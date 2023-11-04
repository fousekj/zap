package com.example.settings.docType;

import com.example.DB.DB;
import com.example.interfaces.Validatable;
import com.example.main.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class DocTypeController implements Initializable, Validatable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfKey;
    @FXML
    private ComboBox<DocCategory> cbCategory = new ComboBox<>();

    @FXML
    private AnchorPane anchorPaneDocType;
    @FXML
    private ListView<DocType> docTypeListView = new ListView<>();
    private ObservableList<DocType> docTypeList;

    private DB database;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = App.getDatabase();
        docTypeList = database.getDocTypes();

        docTypeListView.setItems(docTypeList);
        if (docTypeList.isEmpty()){
            docTypeListView.getSelectionModel().select(0);
        }

        cbCategory.getItems().addAll(DocCategory.values());
    }

    public void handleCreateDocTypeAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DocTypeController.class.getResource("createDocType.fxml"));
            AnchorPane newAnchorPane = fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPaneDocType.getParent();
            parent.setCenter(newAnchorPane);

        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
        }

    }

    public void handleCreateNewDocTypeAction(ActionEvent event) {
        if (checkIfDocTypeExists(tfKey.getText())){
            showAlert(Alert.AlertType.WARNING, "Prodejní organizace s daným klíčem již existuje");
        } else {
            try {
                DocType docType = new DocType(tfName.getText(), tfKey.getText(), cbCategory.getValue());
                docTypeList.add(docType);
                database.setDocTypes(docTypeList);
                showAlert(Alert.AlertType.CONFIRMATION, "Nový druh dokladu úspěšně vytvořena");
                FXMLLoader fxmlLoader = new FXMLLoader(DocTypeController.class.getResource("displayAllDocTypes.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                BorderPane parent = (BorderPane) anchorPaneDocType.getParent();
                parent.setCenter(anchorPane);

            } catch (Exception e){
                showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se vytvořit novou prodejní organizaci");
            }
        }

    }

    private boolean checkIfDocTypeExists (String key) {
        for (DocType d : docTypeList) {
            if (Objects.equals(d.getKey(), key)) {
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

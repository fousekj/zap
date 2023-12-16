package com.example.FinancialDocument;

import com.example.PurchaseOrder.PurchaseOrder;
import com.example.PurchaseOrder.PurchaseOrderController;
import com.example.PurchaseOrder.PurchaseOrderDAO;
import com.example.PurchaseOrder.PurchaseOrderItem;
import com.example.customer.Customer;
import com.example.customer.CustomerDAO;
import com.example.interfaces.Alertable;
import com.example.interfaces.DocumentHeader;
import com.example.material.Material;
import com.example.material.MaterialDAO;
import com.example.settings.PaymentStatus;
import com.example.settings.PaymentTerm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FinancialDocumentController implements Initializable, Alertable {
    @FXML
    public ComboBox<PurchaseOrder> cbPurchaseOrder = new ComboBox<>();
    @FXML
    public ListView<FinancialDocument> financialDocumentListView = new ListView<>();
    @FXML
    private TextField tfCurrency, tfTotalPrice, tfTotalVat, tfCustomer;


    @FXML
    private AnchorPane anchorPane;
    private ObservableList<PurchaseOrder> purchaseOrderList = FXCollections.observableArrayList();
    private ObservableList<FinancialDocument> financialDocumentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        purchaseOrderList = PurchaseOrderDAO.getAllPurchaseOrders();
        financialDocumentList = FinancialDocumetDAO.getAllFinancialDocuments();
        purchaseOrderList.removeIf(po -> po.getFinancialDocumentId() != 0);
        cbPurchaseOrder.setItems(purchaseOrderList);

        financialDocumentListView.setItems(financialDocumentList);
        if (financialDocumentList.isEmpty()){
            financialDocumentListView.getSelectionModel().select(0);
        }

    }

    public void handleCreateFIDocFromPurchaseOrder(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FinancialDocumentController.class.getResource("createFinancialDocumentFromPO.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void fillFIData(){
        PurchaseOrder po = cbPurchaseOrder.getValue();
        tfTotalVat.setText(String.valueOf(po.getVat()));
        tfCustomer.setText(po.getCustomer().getName());
        tfTotalPrice.setText(String.valueOf(po.getPrice()));

    }


    public void handleCreateNewFIDocFromPurchaseOrder(ActionEvent event) {

        try {
            FinancialDocument financialDocument =
                    new FinancialDocument(Float.parseFloat(tfTotalPrice.getText()), Float.parseFloat(tfTotalVat.getText()), tfCurrency.getText(), PaymentStatus.ACTIVE, cbPurchaseOrder.getValue());
            financialDocument.setCustomer(cbPurchaseOrder.getValue().getCustomer());
            financialDocument.setPreviousDocument(cbPurchaseOrder.getValue());
            int newDoc = FinancialDocumetDAO.insertFinancialDocumentFromPO(financialDocument);
            FXMLLoader fxmlLoader = new FXMLLoader(FinancialDocumentController.class.getResource("displayAllFinancialDocuments.fxml"));
            showAlert(Alert.AlertType.CONFIRMATION, "Finanční doklad s číslem " + newDoc  + "byla úspěšně vytvořena");
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se vytvořit novou nákupní objednávku");
        }
    }
}

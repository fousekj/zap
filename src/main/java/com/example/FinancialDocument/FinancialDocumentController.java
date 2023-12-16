package com.example.FinancialDocument;

import com.example.PurchaseOrder.PurchaseOrder;
import com.example.PurchaseOrder.PurchaseOrderDAO;
import com.example.SalesOrder.SalesOrder;
import com.example.SalesOrder.SalesOrderDAO;
import com.example.interfaces.Alertable;
import com.example.interfaces.DocumentHeader;
import com.example.settings.PaymentStatus;
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
import java.util.ResourceBundle;

public class FinancialDocumentController implements Initializable, Alertable {
    @FXML
    public ComboBox<PurchaseOrder> cbPurchaseOrder = new ComboBox<>();
    @FXML
    public ComboBox<SalesOrder> cbSalesOrder = new ComboBox<>();
    @FXML
    public ComboBox<DocumentHeader> cbPreviousDocument = new ComboBox<>();
    @FXML
    public ListView<FinancialDocument> financialDocumentListView = new ListView<>();
    @FXML
    private TextField tfCurrency, tfTotalPrice, tfTotalVat, tfCustomer, tfId = new TextField();


    @FXML
    private AnchorPane anchorPane;
    private ObservableList<PurchaseOrder> purchaseOrderList = FXCollections.observableArrayList();
    private ObservableList<SalesOrder> salesOrderList = FXCollections.observableArrayList();
    private ObservableList<FinancialDocument> financialDocumentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        purchaseOrderList = PurchaseOrderDAO.getAllPurchaseOrders();
        financialDocumentList = FinancialDocumetDAO.getAllFinancialDocuments();
        purchaseOrderList.removeIf(po -> po.getFinancialDocumentId() != 0);
        cbPurchaseOrder.setItems(purchaseOrderList);
        salesOrderList = SalesOrderDAO.getAllSalesOrders();
        salesOrderList.removeIf(so -> so.getFinancialDocumentId() != 0);
        cbSalesOrder.setItems(salesOrderList);

        financialDocumentListView.setItems(financialDocumentList);
        if (financialDocumentList.isEmpty()) {
            financialDocumentListView.getSelectionModel().select(0);
        }

    }

    public void handleCreateFIDocFromPurchaseOrder(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FinancialDocumentController.class.getResource("createFinancialDocumentFromPO.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void handleCreateFIDocFromSalesOrder(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FinancialDocumentController.class.getResource("createFinancialDocumentFromSO.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void fillFIDataFromSO() {
        SalesOrder so = cbSalesOrder.getValue();
        tfTotalVat.setText(String.valueOf(so.getVat()));
        tfCustomer.setText(so.getCustomer().getName());
        tfTotalPrice.setText(String.valueOf(so.getPrice()));

    }

    public void fillFIDataFromPO() {
        PurchaseOrder po = cbPurchaseOrder.getValue();
        tfTotalVat.setText(String.valueOf(po.getVat()));
        tfCustomer.setText(po.getCustomer().getName());
        tfTotalPrice.setText(String.valueOf(po.getPrice()));

    }


    public void handleCreateNewFIDocFromPurchaseOrder(ActionEvent event) {
        if (cbPurchaseOrder.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Musíte vybrat nákupní objednávku");
            return;
        }
        try {
            FinancialDocument financialDocument =
                    new FinancialDocument(Float.parseFloat(tfTotalPrice.getText()), Float.parseFloat(tfTotalVat.getText()), tfCurrency.getText(), PaymentStatus.ACTIVE, cbPurchaseOrder.getValue());
            financialDocument.setCustomer(cbPurchaseOrder.getValue().getCustomer());
            financialDocument.setPreviousDocument(cbPurchaseOrder.getValue());
            int newDoc = FinancialDocumetDAO.insertFinancialDocumentFromPO(financialDocument);
            FXMLLoader fxmlLoader = new FXMLLoader(FinancialDocumentController.class.getResource("displayAllFinancialDocuments.fxml"));
            showAlert(Alert.AlertType.CONFIRMATION, "Finanční doklad s číslem " + newDoc + "byla úspěšně vytvořena");
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se vytvořit novou nákupní objednávku");
        }
    }

    public void handleCreateNewFIDocFromSalesOrder(ActionEvent event) {
        if (cbSalesOrder.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Musíte vybrat zakázku");
            return;
        }

        try {
            FinancialDocument financialDocument =
                    new FinancialDocument(Float.parseFloat(tfTotalPrice.getText()), Float.parseFloat(tfTotalVat.getText()), tfCurrency.getText(), PaymentStatus.ACTIVE, cbPurchaseOrder.getValue());
            financialDocument.setCustomer(cbSalesOrder.getValue().getCustomer());
            financialDocument.setPreviousDocument(cbSalesOrder.getValue());
            int newDoc = FinancialDocumetDAO.insertFinancialDocumentFromSO(financialDocument);
            FXMLLoader fxmlLoader = new FXMLLoader(FinancialDocumentController.class.getResource("displayAllFinancialDocuments.fxml"));
            showAlert(Alert.AlertType.CONFIRMATION, "Finanční doklad s číslem " + newDoc + "byl úspěšně vytvořen");
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se vytvořit finanční doklad k zakázce");
        }
    }

    public void handleDisplayDocument() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FinancialDocumentController.class.getResource("displayFinancialDocument.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se zobrazit finanční doklad");
        }
    }

    public void handleDisplayDocumentById() {
        FinancialDocument fiDocument = null;
        if (tfId.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Musíte zadat číslo finančního dokladu");
            return;
        }
        for (FinancialDocument fd : this.financialDocumentList) {
            if (fd.getId() == Integer.parseInt(tfId.getText())) {
                fiDocument = fd;
                break;
            }
        }

        if (fiDocument == null) {
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se nalézt finanční doklad s tímto číslem");
        } else {
            tfId.setText(String.valueOf(fiDocument.getId()));
            tfTotalPrice.setText(String.valueOf(fiDocument.getPrice()));
            tfTotalVat.setText(String.valueOf(fiDocument.getVat()));
            tfCurrency.setText(fiDocument.getCurrency());
            tfCustomer.setText(fiDocument.getCustomer().getName());
            cbPreviousDocument.setValue(fiDocument.getPreviousDocument());
        }
    }
}

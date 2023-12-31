package com.example.PurchaseOrder;

import com.example.SalesOrder.SalesOrder;
import com.example.customer.Customer;
import com.example.customer.CustomerDAO;
import com.example.customer.Role;
import com.example.interfaces.Alertable;
import com.example.material.Material;
import com.example.material.MaterialDAO;
import com.example.settings.PaymentTerm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class PurchaseOrderController implements Initializable, Alertable {

    @FXML
    private TextField tfQuantity, tfTotalPrice, tfTotalVat, tfIncoterms, tfPaymentTerms, tfId;

    @FXML
    ComboBox<Customer> cbCustomer = new ComboBox<>();
    @FXML
    ComboBox<Material> cbMaterial = new ComboBox<>();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ListView<PurchaseOrder> purchaseOrderListView = new ListView<>();
    @FXML
    private ListView<PurchaseOrderItem> lvItems = new ListView<>();
    private ObservableList<PurchaseOrder> purchaseOrderList = FXCollections.observableArrayList();
    private ObservableList<PurchaseOrderItem> purchaseOrderItems = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        purchaseOrderList = PurchaseOrderDAO.getAllPurchaseOrders();
        purchaseOrderListView.setItems(purchaseOrderList);
        if (purchaseOrderList.isEmpty()){
            purchaseOrderListView.getSelectionModel().select(0);
        }
        ObservableList<Customer> customers = CustomerDAO.getAllCustomers();
        customers.removeIf(c -> c.getRole() != Role.VE);
        cbCustomer.getItems().addAll(customers);
        ObservableList<Material> materials = MaterialDAO.getAllMaterials();
        cbMaterial.setItems(materials);


    }

    public void handleCreatePurchaseOrder(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PurchaseOrderController.class.getResource("createPurchaseOrder.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void handleAddMaterial(){
        if (cbMaterial.getValue() == null){
            showAlert(Alert.AlertType.ERROR, "Není vybrán materiál");
            return;
        }

        if (tfQuantity.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Není vyplněno množství");
            return;
        }

        if (Float.parseFloat(tfQuantity.getText()) < 0){
            showAlert(Alert.AlertType.ERROR, "Množství musí být větší než 0");
            return;
        }

        Material mat = (Material) cbMaterial.getValue();
        PurchaseOrderItem item = new PurchaseOrderItem();
        if (lvItems.getItems().isEmpty()){
            item.setPosnr(1);
        } else {
            item.setPosnr(lvItems.getItems().size() + 1);
        }
        item.setMaterial(mat);
        item.setPrice(mat.getPrice() * Float.parseFloat(tfQuantity.getText()));
        item.setQuantity(Float.parseFloat(tfQuantity.getText()));
        item.setVat(mat.getVatRate() * mat.getPrice() / 100);

        purchaseOrderItems.add(item);
        lvItems.getItems().add(item);

        tfTotalPrice.setText(String.format(Locale.US, "%.2f", Float.parseFloat(tfTotalPrice.getText()) + item.getPrice()));
        tfTotalVat.setText(String.format(Locale.US, "%.2f", Float.parseFloat(tfTotalVat.getText()) + item.getVat()));


    }

    public void handleDeleteMaterial(){
        int index = lvItems.getSelectionModel().getSelectedIndex();
        if (index != -1){
            PurchaseOrderItem item = lvItems.getItems().get(index);
            tfTotalPrice.setText(String.format(Locale.US, "%.2f", Float.parseFloat(tfTotalPrice.getText()) - item.getPrice()));
            tfTotalVat.setText(String.format(Locale.US, "%.2f", Float.parseFloat(tfTotalVat.getText()) - item.getVat()));
            lvItems.getItems().remove(index);
            purchaseOrderItems.remove(index);
        }
    }

    public void fillBusinessPartnerData(){
        Customer c = cbCustomer.getValue();
        tfIncoterms.setText(c.getIncoterms());
        tfPaymentTerms.setText(c.getPaymentTerm().toString());
    }


    public void handleCreateNewPurchaseOrder(ActionEvent event) {
        if (cbCustomer.getValue() == null){
            showAlert(Alert.AlertType.ERROR, "Musíte vybrat dodavatele");
            return;
        }
        if (purchaseOrderItems.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Musíte přidat materiál");
            return;
        }
        try {
            PurchaseOrder po = new PurchaseOrder(tfIncoterms.getText(), PaymentTerm.fromLabel(tfPaymentTerms.getText()), Float.parseFloat(tfTotalPrice.getText()), Float.parseFloat(tfTotalVat.getText()));
            po.setItems(purchaseOrderItems);
            po.setCustomer(cbCustomer.getValue());
            int newDocId = PurchaseOrderDAO.insertPurchaseOrder(po);
            showAlert(Alert.AlertType.CONFIRMATION, "Nová nákupní objednávka s číslem " + newDocId + " úspěšně vytvořena!");
            FXMLLoader fxmlLoader = new FXMLLoader(PurchaseOrderController.class.getResource("displayAllPurchaseOrders.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se vytvořit novou nákupní objednávku");
        }
    }

    public void handleDisplayDocument() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PurchaseOrder.class.getResource("displayPurchaseOrder.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se zobrazit účetní doklad");
        }
    }
    public void handleDisplayDocumentById() {
        PurchaseOrder purchaseOrder = null;
        if (tfId.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Musíte zadat číslo objednávky dokladu");
            return;
        }
        for (PurchaseOrder po : this.purchaseOrderList) {
            if (po.getId() == Integer.parseInt(tfId.getText())) {
                purchaseOrder = po;
                break;
            }
        }

        if (purchaseOrder == null) {
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se nalézt zakázku s tímto číslem");
        } else {
            tfId.setText(String.valueOf(purchaseOrder.getId()));
            tfTotalPrice.setText(String.valueOf(purchaseOrder.getPrice()));
            tfTotalVat.setText(String.valueOf(purchaseOrder.getVat()));
            cbCustomer.setValue(purchaseOrder.getCustomer());
            tfIncoterms.setText(purchaseOrder.getIncoterms());
            tfPaymentTerms.setText(purchaseOrder.getPaymentTerms().toString());
            lvItems.setItems(purchaseOrder.getItems());
        }
    }

}

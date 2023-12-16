package com.example.SalesOrder;

import com.example.PurchaseOrder.PurchaseOrder;
import com.example.PurchaseOrder.PurchaseOrderDAO;
import com.example.PurchaseOrder.PurchaseOrderItem;
import com.example.customer.Customer;
import com.example.customer.CustomerDAO;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SalesOrderController implements Initializable, Alertable {

    @FXML
    private TextField tfQuantity, tfTotalPrice, tfTotalVat, tfIncoterms, tfPaymentTerms;

    @FXML
    ComboBox<Customer> cbCustomer = new ComboBox<>();
    @FXML
    ComboBox<Material> cbMaterial = new ComboBox<>();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ListView<SalesOrder> salesOrderListView = new ListView<>();
    @FXML
    private ListView<SalesOrderItem> lvItems;
    private ObservableList<SalesOrder> saleseOrderList = FXCollections.observableArrayList();
    private ObservableList<SalesOrderItem> salesOrderItems = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saleseOrderList = SalesOrderDAO.getAllSalesOrders();
        salesOrderListView.setItems(saleseOrderList);
        if (saleseOrderList.isEmpty()){
            salesOrderListView.getSelectionModel().select(0);
        }
        cbCustomer.getItems().addAll(CustomerDAO.getAllCustomers());
        cbMaterial.setItems(MaterialDAO.getAllMaterials());

    }

    public void handleCreateSalesOrder(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SalesOrderController.class.getResource("createSalesOrder.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void handleAddMaterial(){

        Material mat = (Material) cbMaterial.getValue();
        SalesOrderItem item = new SalesOrderItem();
        if (lvItems.getItems().isEmpty()){
            item.setPosnr(1);
        } else {
            item.setPosnr(lvItems.getItems().size() + 1);
        }
        item.setMaterial(mat);
        item.setPrice(mat.getPrice() * Float.parseFloat(tfQuantity.getText()));
        item.setQuantity(Float.parseFloat(tfQuantity.getText()));
        item.setVat(mat.getVatRate() * mat.getPrice() / 100);

        salesOrderItems.add(item);
        lvItems.getItems().add(item);

        tfTotalPrice.setText(String.format(Locale.US, "%.2f", Float.parseFloat(tfTotalPrice.getText()) + item.getPrice()));
        tfTotalVat.setText(String.format(Locale.US, "%.2f", Float.parseFloat(tfTotalVat.getText()) + item.getVat()));


    }

    public void handleDeleteMaterial(){
        int index = lvItems.getSelectionModel().getSelectedIndex();
        if (index != -1){
            SalesOrderItem item = lvItems.getItems().get(index);
            tfTotalPrice.setText(String.format(Locale.US, "%.2f", Float.parseFloat(tfTotalPrice.getText()) - item.getPrice()));
            tfTotalVat.setText(String.format(Locale.US, "%.2f", Float.parseFloat(tfTotalVat.getText()) - item.getVat()));
            lvItems.getItems().remove(index);
            salesOrderItems.remove(index);
        }
    }

    public void fillBusinessPartnerData(){
        Customer c = cbCustomer.getValue();
        tfIncoterms.setText(c.getIncoterms());
        tfPaymentTerms.setText(c.getPaymentTerm().toString());
    }


    public void handleCreateNewSalesOrder(ActionEvent event) {

        try {
            SalesOrder so = new SalesOrder(tfIncoterms.getText(), PaymentTerm.fromLabel(tfPaymentTerms.getText()), Float.parseFloat(tfTotalPrice.getText()), Float.parseFloat(tfTotalVat.getText()));
            so.setItems(salesOrderItems);
            so.setCustomer(cbCustomer.getValue());
            int newDocId = SalesOrderDAO.insertSalesOrder(so);
            showAlert(Alert.AlertType.CONFIRMATION, "Nová nákupní objednávka s číslem " + newDocId + " úspěšně vytvořena!");
            FXMLLoader fxmlLoader = new FXMLLoader(SalesOrderController.class.getResource("displayAllSalesOrders.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Nepodařilo se vytvořit novou nákupní objednávku");
        }
    }

}

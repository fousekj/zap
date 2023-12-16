package com.example.customer;

import com.example.address.Address;
import com.example.interfaces.Alertable;
import com.example.settings.PaymentTerm;
import com.example.DB.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import com.example.DB.DB;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable, Alertable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField tfName, tfSearch, tfIncoterms, tfStreetShip, tfNameShip, tfHouseNumShip,
            tfCityShip, tfCountryShip, tfPostCodeShip, tfEmailShip, tfPhoneShip, tfNameInvoic, tfStreetInvoic,
            tfHouseNumInvoic, tfCityInvoic, tfCountryInvoic, tfPostCodeInvoic, tfEmailInvoic, tfPhoneInvoic, tfDiscount;
    @FXML
    private ListView<Customer> customerListView = new ListView<>();
    @FXML
    private ComboBox<PaymentTerm> cbPaymentTerms = new ComboBox<>();
    @FXML
    private ComboBox<Role> cbRoles = new ComboBox<>();
    private ObservableList<Customer> customerList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerList = CustomerDAO.getAllCustomers();
        customerListView.setItems(customerList);
        if (customerList.isEmpty()){
            customerListView.getSelectionModel().select(0);
        }

        cbPaymentTerms.getItems().addAll(PaymentTerm.values());
        cbRoles.getItems().addAll(Role.values());
    }

    public void handleCreateCustomerAction() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CustomerController.class.getResource("createCustomer.fxml"));
            AnchorPane newAnchorPane = fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se nalézt zdroj zobrazení");
        }
    }

    public void handleFindCustomer() {
        ObservableList<Customer> newCustomerList = FXCollections.observableArrayList();
        for (Customer c : customerList) {
            if (c.getName().contains(tfSearch.getText())) {
                newCustomerList.add(c);
            }
        }

        if (newCustomerList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Zákazník s tímto hledanám názvem neexistuje");
        } else {
            customerListView.setItems(newCustomerList);
        }
    }

    public void handleCreateNewCustomerAction() {
        if (tfName.getText().isEmpty() || tfIncoterms.getText().isEmpty() || tfStreetShip.getText().isEmpty() ||
                tfNameShip.getText().isEmpty() || tfHouseNumShip.getText().isEmpty() || tfCityShip.getText().isEmpty() ||
                tfCountryShip.getText().isEmpty() || tfPostCodeShip.getText().isEmpty() || tfEmailShip.getText().isEmpty() ||
                tfPhoneShip.getText().isEmpty() || tfNameInvoic.getText().isEmpty() || tfStreetInvoic.getText().isEmpty() ||
                tfHouseNumInvoic.getText().isEmpty() || tfCityInvoic.getText().isEmpty() || tfCountryInvoic.getText().isEmpty() ||
                tfPostCodeInvoic.getText().isEmpty() || tfEmailInvoic.getText().isEmpty() || tfPhoneInvoic.getText().isEmpty() ||
                tfDiscount.getText().isEmpty() || cbPaymentTerms.getValue() == null || cbRoles.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Některé z povinných polí nejsou vyplněny");
            return;
        }

        try {
            Address delivery_address = new Address(tfNameShip.getText(), tfStreetShip.getText(), tfHouseNumShip.getText(),
                    tfCityShip.getText(), tfCountryShip.getText(), tfPostCodeShip.getText(),
                    tfEmailShip.getText(), tfPhoneShip.getText());

            Address invoice_address = new Address(tfNameInvoic.getText(), tfStreetInvoic.getText(), tfHouseNumInvoic.getText(),
                    tfCityInvoic.getText(), tfCountryInvoic.getText(), tfPostCodeInvoic.getText(),
                    tfEmailInvoic.getText(), tfPhoneInvoic.getText());

            Customer newCustomer = new Customer(tfName.getText(), cbRoles.getValue(), tfIncoterms.getText(),
                    delivery_address, invoice_address, Float.parseFloat(tfDiscount.getText()), cbPaymentTerms.getValue());

            CustomerDAO.insertCustomer(newCustomer);
            showAlert(Alert.AlertType.CONFIRMATION, "Nový zákazník byl úspěšně vytvořen");
            FXMLLoader fxmlLoader = new FXMLLoader(CustomerController.class.getResource("displayAllCustomers.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);
        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se vytvořit nového zákazníka");
        }
    }

}

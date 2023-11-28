package com.example.customer;

import com.example.DB.DB;
import com.example.address.Address;
import com.example.interfaces.Alertable;
import com.example.main.App;
import com.example.settings.PaymentTerm;
import com.example.settings.docType.DocTypeController;
import com.example.settings.salesOrg.SalesOrg;
import com.example.settings.salesOrg.SalesOrgController;
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

import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable, Alertable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField tfName, tfSearchTerm, tfIncoterms, tfICO, tfDIC, tfStreet, tfHouseNum,
            tfCity, tfCountry, tfEmail, tfPhone;
    @FXML
    private ListView<Customer> customerListView = new ListView<>();
    @FXML
    private ComboBox<PaymentTerm> cbPaymentTerms = new ComboBox<>();
    private ObservableList<Customer> customerList;
    //private DB database;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //database = App.getDatabase();
        customerList = CustomerDAO.getAllCustomers();
        customerListView.setItems(customerList);
        if (customerList.isEmpty()){
            customerListView.getSelectionModel().select(0);
        }
        cbPaymentTerms.getItems().addAll(PaymentTerm.values());
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
            if (c.getSearchTerm().contains(tfSearchTerm.getText())) {
                newCustomerList.add(c);
            }
        }

        if (newCustomerList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Zákazník s tímto hledanám termínem neexistuje");
        } else {
            customerListView.setItems(newCustomerList);
        }
    }

    public void handleCreateNewCustomerAction() {
        /*try {
            Address address = new Address(tfStreet.getText(), tfHouseNum.getText(), tfCity.getText(), tfCountry.getText(), tfEmail.getText(), tfPhone.getText());
            Customer newCustomer = new Customer(findNewCustomerNumber(), tfName.getText(), tfSearchTerm.getText(), tfICO.getText(), tfDIC.getText(), tfIncoterms.getText(), address, cbPaymentTerms.getValue());
            customerList.add(newCustomer);
            database.setCustomers(customerList);
            showAlert(Alert.AlertType.CONFIRMATION, "Nový zákazník byl úspěšně vytvořen");
            FXMLLoader fxmlLoader = new FXMLLoader(CustomerController.class.getResource("displayAllCustomers.fxml"));
            AnchorPane newAnchorPane = (AnchorPane) fxmlLoader.load();
            BorderPane parent = (BorderPane) anchorPane.getParent();
            parent.setCenter(newAnchorPane);

        } catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Kritická chyba - nepodařilo se vytvořit nového zákazníka");
        }*/
    }

    private int findNewCustomerNumber() {
        if (customerList.isEmpty()) {
            return 1;
        } else {
            Optional<Customer> currentMaxCustomer = customerList.stream().max(Comparator.comparing(Customer::getNumber));
            return currentMaxCustomer.get().getNumber() + 1;
        }
    }

}

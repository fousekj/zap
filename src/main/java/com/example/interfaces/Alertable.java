package com.example.interfaces;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public interface Alertable {

    default void showAlert(Alert.AlertType alertType, String text){
        Alert alert = new Alert(alertType, text);
        alert.show();
    }
}

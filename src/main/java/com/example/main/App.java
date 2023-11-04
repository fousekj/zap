package com.example.main;

import com.example.DB.DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static DB database;

    @Override
    public void start(Stage primaryStage) throws IOException {
        setup();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("homeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("ZAP - FOU0027");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void setup(){
        database = new DB();
    }

    public static DB getDatabase (){
        return database;
    }

    public static void main(String[] args) {
        launch();
    }
}
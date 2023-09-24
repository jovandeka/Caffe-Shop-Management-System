package com.example.cafeshopmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CaffeShopManagementSystem extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("caffe-login-ui.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Caffe Shop Management System");
        stage.setMinHeight(450);
        stage.setMinWidth(610);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
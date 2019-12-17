package com.lunchtime.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class home_controller implements Initializable {

    @FXML
    private StackPane homeRootPane;

    @FXML
    private Pane homeTopImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeTopImage.setStyle("-fx-background-size: cover;" + "-fx-background-image: url('https://ik.imagekit.io/er6odmy9tr/tr:n-image/chicken_tDUVHKZNK4.jpg');");
    }
}

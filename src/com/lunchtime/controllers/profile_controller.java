package com.lunchtime.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.User;
import com.lunchtime.network.apiObjects.requests.UpdateProfileRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class profile_controller {

    @FXML
    private StackPane profilePane;

    @FXML
    private JFXTextField first_name_field;

    @FXML
    private JFXTextField last_name_field;

    @FXML
    private JFXTextField email_field;

    @FXML
    private JFXPasswordField password_field;

    @FXML
    private JFXTextField phone_field;

    @FXML
    private Circle profilePictureView;

    @FXML
    private Circle addPictureView;

    @FXML
    void selectPictureClicked(MouseEvent event) {

    }

    @FXML
    void update_button_clicked(ActionEvent event) {

    }


}

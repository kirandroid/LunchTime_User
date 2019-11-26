package com.lunchtime.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.requests.RegisterRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class register_controller {

    @FXML
    private AnchorPane register_pane;

    @FXML
    private JFXTextField phone_field;

    @FXML
    private JFXPasswordField password_field;

    @FXML
    private JFXTextField email_field;

    @FXML
    private JFXTextField last_name_field;

    @FXML
    private JFXTextField first_name_field;

    @FXML
    private JFXPasswordField confirm_password_field;

    @FXML
    void login_button_clicked(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
        register_pane.getChildren().setAll(pane);
    }

    @FXML
    void register_button_clicked(ActionEvent event) {
        RegisterRequest registerRequest = new RegisterRequest(first_name_field.getText(), last_name_field.getText(), phone_field.getText(), email_field.getText(), password_field.getText(), "google");

        NetworkManager.getInstance().Register(registerRequest, new NetworkResponseListener<ApiBaseResponse>() {
            @Override
            public void onResponseReceived(ApiBaseResponse apiBaseResponse) {
                Platform.runLater(
                            () -> {
                                try {
                                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
                                    register_pane.getChildren().setAll(pane);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
            }

            @Override
            public void onError() {
                System.out.println("Register Error!");
            }
        });
    }
}

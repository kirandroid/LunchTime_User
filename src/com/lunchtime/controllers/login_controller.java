package com.lunchtime.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.requests.LoginRequest;
import com.lunchtime.network.apiObjects.wrappers.UserWrapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class login_controller {
    @FXML
    private AnchorPane login_pane;

    @FXML
    private JFXTextField email_field;

    @FXML
    private JFXPasswordField password_field;

    @FXML
    void login_button_clicked(ActionEvent event) {
        LoginRequest loginRequest = new LoginRequest(email_field.getText(), password_field.getText());

        NetworkManager.getInstance().Login(loginRequest, new NetworkResponseListener<ApiBaseResponse<UserWrapper>>() {
            @Override
            public void onResponseReceived(ApiBaseResponse<UserWrapper> userWrapperApiBaseResponse) {

                Platform.runLater(
                            () -> {
                                try {
                                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/dashboard_view.fxml"));
                                    login_pane.getChildren().setAll(pane);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );

            }

            @Override
            public void onError() {
                System.out.println("Login Error");
            }
        });
    }

    @FXML
    void register_button_clicked(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/register_view.fxml"));
        login_pane.getChildren().setAll(pane);
    }



}

package com.lunchtime.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.lunchtime.apiservices.ApiBaseResponse;
import com.lunchtime.apiservices.Network;
import com.lunchtime.apiservices.requests.RegisterRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Call<ApiBaseResponse> call = Network.apiService.register(registerRequest);
        call.enqueue(new Callback<ApiBaseResponse>() {
            @Override
            public void onResponse(Call<ApiBaseResponse> call, Response<ApiBaseResponse> response) {
                ApiBaseResponse baseResponse = response.body();
                if (baseResponse.isSuccess()) {
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
                } else {
                    System.out.println("Registration Failed because " + baseResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ApiBaseResponse> call, Throwable throwable) {

            }
        });

    }
}

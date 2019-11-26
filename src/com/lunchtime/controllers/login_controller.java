package com.lunchtime.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.lunchtime.apiservices.ApiBaseResponse;
import com.lunchtime.apiservices.Network;
import com.lunchtime.apiservices.requests.LoginRequest;
import com.lunchtime.apiservices.wrappers.UserWrapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.prefs.Preferences;

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
        Call<ApiBaseResponse<UserWrapper>> call = Network.apiService.login(loginRequest);
        call.enqueue(new Callback<ApiBaseResponse<UserWrapper>>() {
            @Override
            public void onResponse(Call<ApiBaseResponse<UserWrapper>> call, Response<ApiBaseResponse<UserWrapper>> response) {

                ApiBaseResponse<UserWrapper> user = response.body();
                if (user.isSuccess()) {

                    Preferences userPreferences = Preferences.userRoot();
                    userPreferences.put("Name", user.getData().getUser().getFirst_name());

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
                } else {
                    System.out.println("Login Failed because " + user.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ApiBaseResponse<UserWrapper>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @FXML
    void register_button_clicked(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/register_view.fxml"));
        login_pane.getChildren().setAll(pane);
    }



}

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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class profile_controller {
    @FXML
    private AnchorPane profilePane;

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
    void register_button_clicked(ActionEvent event) {
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(first_name_field.getText(), last_name_field.getText(), phone_field.getText(), email_field.getText(), password_field.getText(), 1);

        NetworkManager.getInstance().Update(updateProfileRequest, new NetworkResponseListener<ApiBaseResponse>() {
            @Override
            public void onResponseReceived(ApiBaseResponse apiBaseResponse) {
                Platform.runLater(
                        () -> {
                            try {
                                AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/dashboard_view.fxml"));
                                profilePane.getChildren().setAll(pane);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
            }

            @Override
            public void onError() {
                System.out.println("Update Error!");
            }
        });
    }

    public void initialize(){
//        User user = new User();
//        first_name_field.setText(user.getFirst_name());
//        last_name_field.setText(user.getLast_name());
//        phone_field.setText(user.getPhone_number());
//        email_field.setText(user.getEmail());
//        password_field.setText(user.getPassword());
        login_controller loginController = new login_controller();
        List<User> userResponse = loginController.getUser();
        System.out.println(userResponse);
    }

}

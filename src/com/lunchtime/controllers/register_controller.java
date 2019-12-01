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
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class register_controller implements Initializable {

    @FXML
    private AnchorPane registerPane;

    @FXML
    private MediaView registerVideoPlayer;

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
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
        registerPane.getChildren().setAll(pane);
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
                                    StackPane pane = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
                                    registerPane.getChildren().setAll(pane);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final MediaPlayer video = new MediaPlayer(new Media(new File("src/com/lunchtime/assets/registerVideo.mp4").toURI().toString()));
        video.setMute(true);
        video.setCycleCount(MediaPlayer.INDEFINITE);
        video.play();
        registerVideoPlayer.setMediaPlayer(video);
    }
}

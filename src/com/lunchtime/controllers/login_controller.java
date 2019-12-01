package com.lunchtime.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.User;
import com.lunchtime.network.apiObjects.requests.LoginRequest;
import com.lunchtime.network.apiObjects.wrappers.UserWrapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class login_controller  implements Initializable {
    @FXML
    private StackPane login_pane;

    @FXML
    private JFXTextField email_field;

    @FXML
    private JFXPasswordField password_field;


    @FXML
    private MediaView loginVideoPlayer;

    //---------------For making the screen draggable-------------
    double x, y;

    @FXML
    void windowDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
        stage.setOpacity(0.7f);
    }

    @FXML
    void windowDraggedDone(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setOpacity(1.0f);
    }

    @FXML
    void windowPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
    //---------------For making the screen draggable-------------


    @FXML
    void login_button_clicked(ActionEvent event) {
        LoginRequest loginRequest = new LoginRequest(email_field.getText(), password_field.getText());

        NetworkManager.getInstance().Login(loginRequest, new NetworkResponseListener<ApiBaseResponse<UserWrapper>>() {
            @Override
            public void onResponseReceived(ApiBaseResponse<UserWrapper> userWrapperApiBaseResponse) {
                User userResponse = userWrapperApiBaseResponse.getData().getUser();
                Platform.runLater(
                            () -> {
                                try {
//                                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/dashboard_view.fxml"));
//                                    StackPane pane = FXMLLoader.load(getClass().getResource("../views/testVideo.fxml"));
                                    BorderPane pane = FXMLLoader.load(getClass().getResource("../views/dashboard_view.fxml"));
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final MediaPlayer video = new MediaPlayer(new Media(new File("src/com/lunchtime/assets/loginVideoCustom.mp4").toURI().toString()));
        video.setMute(true);
        video.setCycleCount(MediaPlayer.INDEFINITE);
        video.play();
        loginVideoPlayer.setMediaPlayer(video);
    }
}

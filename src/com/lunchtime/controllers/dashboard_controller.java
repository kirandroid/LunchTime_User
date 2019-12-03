package com.lunchtime.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.wrappers.MenuWrapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class dashboard_controller {

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private JFXListView<Label> menuListview;



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
    private JFXButton closeButton;

    @FXML
    void closeButtonClicked(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void profileClicked(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/profile_view.fxml"));
        dashboardPane.getChildren().setAll(pane);
    }

    public void initialize() {
        closeButton.setGraphic(new ImageView(new Image(new File("src/com/lunchtime/assets/image/close.png").toURI().toString(), 15, 15, false, true, true)));

        NetworkManager.getInstance().GetMenu(new NetworkResponseListener<ApiBaseResponse<MenuWrapper>>() {
            @Override
            public void onResponseReceived(ApiBaseResponse<MenuWrapper> menuWrapperApiBaseResponse) {


                Platform.runLater(() -> {
//                    final List menu = menuWrapperApiBaseResponse.getData().getMenu();
//                    for (int i = 0; i < menu.size(); i++) {
//
//                        Label label = new Label(menuWrapperApiBaseResponse.getData().getMenu().get(i).getFood_name());
//                        label.setGraphic(new ImageView(new Image(menuWrapperApiBaseResponse.getData().getMenu().get(i).getPicture(), 100, 100, false, true,true)));
//                        menuListview.getItems().add(label);
//
//                    }
                });
            }

            @Override
            public void onError() {
                System.out.println("Error on menu fetch");
            }
        });
    }
}

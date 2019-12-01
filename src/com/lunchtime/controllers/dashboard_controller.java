package com.lunchtime.controllers;

import com.jfoenix.controls.JFXListView;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.wrappers.MenuWrapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.cell.ColorGridCell;
import org.controlsfx.control.cell.ImageGridCell;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class dashboard_controller {

    @FXML
    private BorderPane dashboardPane;

    @FXML
    private JFXListView<Label> menuListview;


    @FXML
    void profileClicked(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/profile_view.fxml"));
        dashboardPane.getChildren().setAll(pane);
    }

    public void initialize() {

        NetworkManager.getInstance().GetMenu(new NetworkResponseListener<ApiBaseResponse<MenuWrapper>>() {
            @Override
            public void onResponseReceived(ApiBaseResponse<MenuWrapper> menuWrapperApiBaseResponse) {


                Platform.runLater(() -> {
                    final List menu = menuWrapperApiBaseResponse.getData().getMenu();
                    for (int i = 0; i < menu.size(); i++) {
                        Label label = new Label(menuWrapperApiBaseResponse.getData().getMenu().get(i).getFood_name());
                        menuListview.getItems().add(label);
//                        imageGrid.getItems().addAll(new Comp(menuWrapperApiBaseResponse.getData().getMenu().get(i).getFood_name()));
                    }
//                    final ObservableList<Comp> list = FXCollections.observableArrayList();
//
//                    GridView<Comp> imageGrid = new GridView<>(list);
//
//                    dashboardGridPane.getChildren().add(imageGrid);
//
//                    imageGrid.setCellFactory(arg0 -> new CompGridCell());

                });
            }

            @Override
            public void onError() {
                System.out.println("Error on menu fetch");
            }
        });
    }
}

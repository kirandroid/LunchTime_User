package com.lunchtime.controllers;

import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.wrappers.MenuWrapper;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
public class dashboard_controller {


    @FXML
    private ImageView testImage;

    @FXML
    private AnchorPane dashboardPane;

    public void initialize(){
        NetworkManager.getInstance().GetMenu(new NetworkResponseListener<ApiBaseResponse<MenuWrapper>>() {
            @Override
            public void onResponseReceived(ApiBaseResponse<MenuWrapper> menuWrapperApiBaseResponse) {
                                        testImage.setImage(new Image(menuWrapperApiBaseResponse.getData().getMenu().getPicture()));
            }

            @Override
            public void onError() {
                System.out.println("Error on menu fetch");
            }
        });
        }
}

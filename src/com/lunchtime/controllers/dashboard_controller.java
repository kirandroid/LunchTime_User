package com.lunchtime.controllers;

import com.jfoenix.controls.JFXSnackbar;
import com.lunchtime.apiservices.ApiBaseResponse;
import com.lunchtime.apiservices.Network;
import com.lunchtime.apiservices.wrappers.MenuWrapper;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.prefs.Preferences;

public class dashboard_controller {


    @FXML
    private ImageView testImage;

    @FXML
    private AnchorPane dashboardPane;

    public void initialize(){
            Call<ApiBaseResponse<MenuWrapper>> call = Network.apiService.getMenu();
            call.enqueue(new Callback<ApiBaseResponse<MenuWrapper>>() {
                @Override
                public void onResponse(Call<ApiBaseResponse<MenuWrapper>> call, Response<ApiBaseResponse<MenuWrapper>> response) {
                    ApiBaseResponse<MenuWrapper> menu = response.body();

                    if (menu.isSuccess()){
                        testImage.setImage(new Image(menu.getData().getMenu().getPicture()));
                    }else {
                        System.out.println("Failed");
                        System.out.println(menu);
                    }
                }

                @Override
                public void onFailure(Call<ApiBaseResponse<MenuWrapper>> call, Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

        }
}

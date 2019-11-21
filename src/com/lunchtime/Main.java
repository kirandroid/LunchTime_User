package com.lunchtime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double x, y;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/login_view.fxml"));
        primaryStage.setScene(new Scene(root, 1000, 600));
//        primaryStage.initStyle(StageStyle.UNDECORATED); //Borderless window
        primaryStage.show();

        //To make window draggable
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}

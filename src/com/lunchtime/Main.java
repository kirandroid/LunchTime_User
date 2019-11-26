package com.lunchtime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private double x, y;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/login_view.fxml"));
        Scene scene = new Scene(root);
        String css = Main.class.getResource("assets/css/main.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
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

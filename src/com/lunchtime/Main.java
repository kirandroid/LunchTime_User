package com.lunchtime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("views/login_view.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("views/dashboard_view.fxml"));
        Scene scene = new Scene(root);
        String css = Main.class.getResource("assets/css/main.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED); //Borderless window
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

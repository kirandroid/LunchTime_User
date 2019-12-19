/**
 * @author Kiran Pradhan
 * This controller class is for a static dashboard UI. This class handles all the routes for buttons in the side menu.
 * This controller also Renders the user details
 * */

package com.lunchtime.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class dashboard_controller implements Initializable {
    @FXML
    private StackPane dashboardPane;

    @FXML
    private AnchorPane homePaneButton;

    @FXML
    private AnchorPane menuPaneButton;

    @FXML
    private AnchorPane orderPaneButton;

    @FXML
    private AnchorPane expensePaneButton;

    @FXML
    private StackPane dashboardContentPane;

    @FXML
    private HBox usernameHbox;

    @FXML
    private HBox profilePictureHbox;

    @FXML
    private HBox balanceHbox;

    public static Label userNameLabel = new Label(com.lunchtime.controllers.login_controller.firsName + " "+ com.lunchtime.controllers.login_controller.lastName);
    public static Label userBalanceLabel = new Label(String.valueOf("CC: "+com.lunchtime.controllers.login_controller.balance));
    public static Circle profilePicture = new Circle(45, new ImagePattern(new Image(login_controller.picture)));

    //---------------For making the screen draggable-------------
    private double x, y;

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


    //Exits the application
    @FXML
    void closeButtonClicked(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void expensePaneButtonClicked(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/expense_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);
    }

    @FXML
    void homePaneButtonClicked(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/home_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);
    }


    @FXML
    void menuPaneButtonClicked(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/menu_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);
    }

    @FXML
    void orderPaneButtonClicked(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/order_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);
    }

    //Shows confirmation dialog and Logout the user and change view to login screen.
    @FXML
    void logOutPaneButtonClicked(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            JFXDialogLayout logoutContent = new JFXDialogLayout();
            logoutContent.setHeading(new Text("Logout from the system?"));
            logoutContent.setBody(new Text("Are you sure you want to logout?"));
            JFXDialog logoutDialog = new JFXDialog(dashboardPane, logoutContent, JFXDialog.DialogTransition.CENTER);
            JFXButton logoutConfirmButton = new JFXButton("Confirm");
            JFXButton logoutCancelButton = new JFXButton("Cancel");
            logoutContent.setActions(logoutCancelButton, logoutConfirmButton);

            logoutCancelButton.setOnAction(logout -> logoutDialog.close());

            logoutConfirmButton.setOnAction(logout -> {
                StackPane pane = null;
                try {
                    pane = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dashboardPane.getChildren().setAll(pane);
            });
            logoutDialog.show();
        });
    }

    @FXML
    void profileClicked(MouseEvent event) throws IOException{
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/profile_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            login_controller loginController = new login_controller();
//            profilePicture.setFill(new ImagePattern(new Image(loginController.picture)));
            userNameLabel.setFont(new Font(15));
            userBalanceLabel.setFont(new Font(15));
            userBalanceLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #0b941b");
            usernameHbox.getChildren().add(userNameLabel);
            balanceHbox.getChildren().add(userBalanceLabel);
            profilePictureHbox.getChildren().add(profilePicture);
        });
    }
}

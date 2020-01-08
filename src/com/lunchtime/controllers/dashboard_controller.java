/**
 * @author Kiran Pradhan
 * This controller class is for a static dashboard UI. This class handles all the routes for buttons in the side menu.
 * This controller also Renders the user details
 * */

package com.lunchtime.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class dashboard_controller implements Initializable {

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

    @FXML
    private StackPane dashboardPane;

    @FXML
    private StackPane dashboardContentPane;

    @FXML
    private HBox usernameHbox;

    @FXML
    private HBox profilePictureHbox;

    @FXML
    private HBox balanceHbox;


    public static Label userNameLabel = new Label(com.lunchtime.controllers.login_controller.firsName + " "+ com.lunchtime.controllers.login_controller.lastName);
    public static Label userBalanceLabel = new Label("CC: " + login_controller.balance);

    public static Circle profilePicture;


    //-------------------------------------------------

    String activeBtnBG = "-fx-background-color: #db0f4b;";
    String activeBtnIcon = "-fx-fill: #FFF";
    String activeBtntxt = "-fx-text-fill: #FFF";

    String inactiveBtnBG = "-fx-background-color: #FFF;";
    String inactiveBtntxt = "-fx-text-fill: #000";
    String inactiveBtnIcon = "-fx-fill: #000";

    @FXML
    private HBox homePaneButtonPane;

    @FXML
    private MaterialDesignIconView homePaneButtonIcon;

    @FXML
    private Label homePaneButtonText;

    @FXML
    private HBox menuPaneButtonPane;

    @FXML
    private MaterialDesignIconView menuPaneButtonIcon;

    @FXML
    private Label menuPaneButtonText;

    @FXML
    private HBox orderPaneButtonPane;

    @FXML
    private MaterialDesignIconView orderPaneButtonIcon;

    @FXML
    private Label orderPaneButtonText;

    @FXML
    private HBox expensePaneButtonPane;

    @FXML
    private MaterialDesignIconView expensePaneButtonIcon;

    @FXML
    private Label expensePaneButtonText;


    @FXML
    void homePaneButtonClicked(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/home_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);

        homePaneButtonPane.setStyle(activeBtnBG);
        homePaneButtonIcon.setStyle(activeBtnIcon);
        homePaneButtonText.setStyle(activeBtntxt);

        menuPaneButtonPane.setStyle(inactiveBtnBG);
        menuPaneButtonIcon.setStyle(inactiveBtnIcon);
        menuPaneButtonText.setStyle(inactiveBtntxt);

        orderPaneButtonPane.setStyle(inactiveBtnBG);
        orderPaneButtonIcon.setStyle(inactiveBtnIcon);
        orderPaneButtonText.setStyle(inactiveBtntxt);

        expensePaneButtonPane.setStyle(inactiveBtnBG);
        expensePaneButtonIcon.setStyle(inactiveBtnIcon);
        expensePaneButtonText.setStyle(inactiveBtntxt);
    }

    @FXML
    void menuPaneButtonClicked(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/menu_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);
        homePaneButtonPane.setStyle(inactiveBtnBG);
        homePaneButtonIcon.setStyle(inactiveBtnIcon);
        homePaneButtonText.setStyle(inactiveBtntxt);

        menuPaneButtonPane.setStyle(activeBtnBG);
        menuPaneButtonIcon.setStyle(activeBtnIcon);
        menuPaneButtonText.setStyle(activeBtntxt);

        orderPaneButtonPane.setStyle(inactiveBtnBG);
        orderPaneButtonIcon.setStyle(inactiveBtnIcon);
        orderPaneButtonText.setStyle(inactiveBtntxt);

        expensePaneButtonPane.setStyle(inactiveBtnBG);
        expensePaneButtonIcon.setStyle(inactiveBtnIcon);
        expensePaneButtonText.setStyle(inactiveBtntxt);
    }

    @FXML
    void orderPaneButtonClicked(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/order_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);

        homePaneButtonPane.setStyle(inactiveBtnBG);
        homePaneButtonIcon.setStyle(inactiveBtnIcon);
        homePaneButtonText.setStyle(inactiveBtntxt);

        menuPaneButtonPane.setStyle(inactiveBtnBG);
        menuPaneButtonIcon.setStyle(inactiveBtnIcon);
        menuPaneButtonText.setStyle(inactiveBtntxt);

        orderPaneButtonPane.setStyle(activeBtnBG);
        orderPaneButtonIcon.setStyle(activeBtnIcon);
        orderPaneButtonText.setStyle(activeBtntxt);

        expensePaneButtonPane.setStyle(inactiveBtnBG);
        expensePaneButtonIcon.setStyle(inactiveBtnIcon);
        expensePaneButtonText.setStyle(inactiveBtntxt);
    }

    @FXML
    void expensePaneButtonClicked(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/expense_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);
        homePaneButtonPane.setStyle(inactiveBtnBG);
        homePaneButtonIcon.setStyle(inactiveBtnIcon);
        homePaneButtonText.setStyle(inactiveBtntxt);

        menuPaneButtonPane.setStyle(inactiveBtnBG);
        menuPaneButtonIcon.setStyle(inactiveBtnIcon);
        menuPaneButtonText.setStyle(inactiveBtntxt);

        orderPaneButtonPane.setStyle(inactiveBtnBG);
        orderPaneButtonIcon.setStyle(inactiveBtnIcon);
        orderPaneButtonText.setStyle(inactiveBtntxt);

        expensePaneButtonPane.setStyle(activeBtnBG);
        expensePaneButtonIcon.setStyle(activeBtnIcon);
        expensePaneButtonText.setStyle(activeBtntxt);
    }

//---------------------------------------------------------


    //Exits the application
    @FXML
    void closeButtonClicked(ActionEvent event) {
        System.exit(0);
    }

    //Shows confirmation dialog and Logout the user and change view to login screen.
    @FXML
    void logOutPaneButtonClicked(ActionEvent event) throws IOException {
//        MaterialDesignIcon.CASH
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

        homePaneButtonPane.setStyle(activeBtnBG);
        homePaneButtonIcon.setStyle(activeBtnIcon);
        homePaneButtonText.setStyle(activeBtntxt);

        menuPaneButtonPane.setStyle(inactiveBtnBG);
        menuPaneButtonIcon.setStyle(inactiveBtnIcon);
        menuPaneButtonText.setStyle(inactiveBtntxt);

        orderPaneButtonPane.setStyle(inactiveBtnBG);
        orderPaneButtonIcon.setStyle(inactiveBtnIcon);
        orderPaneButtonText.setStyle(inactiveBtntxt);

        expensePaneButtonPane.setStyle(inactiveBtnBG);
        expensePaneButtonIcon.setStyle(inactiveBtnIcon);
        expensePaneButtonText.setStyle(inactiveBtntxt);


        Platform.runLater(() -> {
            userNameLabel.setFont(new Font(15));
            userBalanceLabel.setFont(new Font(15));
            userBalanceLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #0b941b");
            usernameHbox.getChildren().add(userNameLabel);
            balanceHbox.getChildren().add(userBalanceLabel);
            Image image = null;
            try{
                URL url = new URL(login_controller.picture);
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                image = new Image(inputStream);

            }catch (IOException e){
                image = new Image(new File("src/com/lunchtime/assets/image/defaultUser.png").toURI().toString());
                e.printStackTrace();
            }
            profilePicture = new Circle(45, new ImagePattern(image));

            profilePictureHbox.getChildren().add(profilePicture);
        });
    }
}

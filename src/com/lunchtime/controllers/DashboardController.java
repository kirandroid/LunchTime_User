/**
 * @author Kiran Pradhan
 * This controller class is for a static dashboard UI. This class handles all the routes for buttons in the side menu.
 * This controller also Renders the user details
 */

package com.lunchtime.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.svg.SVGGlyph;
import com.lunchtime.Main;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.User;
import com.lunchtime.network.apiObjects.models.UserObservable;
import com.lunchtime.network.apiObjects.wrappers.UserWrapper;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    String activeBtnBG = "-fx-background-color: #db0f4b;";
    String activeBtnIcon = "-fx-fill: #FFF";
    String activeBtntxt = "-fx-text-fill: #FFF";

    String inactiveBtnBG = "-fx-background-color: #FFF;";
    String inactiveBtntxt = "-fx-text-fill: #000";
    String inactiveBtnIcon = "-fx-fill: #000";


    public static Label userNameLabel;
    public static Label userBalanceLabel;

    public static Circle profilePicture;

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
    private JFXButton refreshButton;

    @FXML
    private HBox profilePictureHbox;

    @FXML
    private HBox balanceHbox;

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


    //Method to run when home button is clicked
    @FXML
    void homePaneButtonClicked(MouseEvent event) throws IOException {
        //Replace the other pane with home view
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/home_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);

        //--------------For button Styling---------------------//
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
        //--------------For button Styling---------------------//
    }

    @FXML
    void menuPaneButtonClicked(MouseEvent event) throws IOException {
        //Replace the other pane with menu view
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/menu_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);

        //--------------For button Styling---------------------//
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
        //--------------For button Styling---------------------//
    }

    @FXML
    void orderPaneButtonClicked(MouseEvent event) throws IOException {
        //Replace the other pane with user order view
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/order_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);

        //--------------For button Styling---------------------//
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
        //--------------For button Styling---------------------//
    }

    @FXML
    void expensePaneButtonClicked(MouseEvent event) throws IOException {
        //Replace the other pane with expense view
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/expense_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);

        //--------------For button Styling---------------------//
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
        //--------------For button Styling---------------------//
    }


    //Exits the application
    @FXML
    void closeButtonClicked(ActionEvent event) {
        System.exit(0);
    }


    //Refetch the user data
    @FXML
    void refreshButtonClicked(ActionEvent event) {
        NetworkManager.getInstance().UserDetail(LoginController.userId, new NetworkResponseListener<ApiBaseResponse<UserWrapper>>() {
            @Override
            public void onResponseReceived(ApiBaseResponse<UserWrapper> userWrapperApiBaseResponse) {
                Platform.runLater(() -> {
                    User userDetailResponse = userWrapperApiBaseResponse.getData().getUser();
                    List<User> users = new ArrayList<>();
                    users.add(new User(userDetailResponse.getId(), userDetailResponse.getFirst_name(), userDetailResponse.getLast_name(), userDetailResponse.getEmail(), userDetailResponse.getPhone_number(), userDetailResponse.getPicture(), userDetailResponse.getBalance()));
                    UserObservable userObservable = new UserObservable();

                    for (User user : users) {
                        userObservable.addObserver(user);
                    }
                    userObservable.UserObservable();
                });
            }

            @Override
            public void onError() {
                System.out.println("Error");
            }
        });
    }

    //Minimize the window
    @FXML
    void minimizeButtonClicked(ActionEvent event) {
        Main.primaryStage.setIconified(true);
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
                    //load login view
                    pane = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dashboardPane.getChildren().setAll(pane);
            });
            logoutDialog.show();
        });
    }

    //Load profile view when profile button is clicked
    @FXML
    void profileClicked(MouseEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/profile_view.fxml"));
        dashboardContentPane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //--------------For button Styling---------------------//
        SVGGlyph glyph = new SVGGlyph(-1,
                "Refresh Icon",
                "M17.65 6.35C16.2 4.9 14.21 4 12 4c-4.42 0-7.99 3.58-7.99 8s3.57 8 7.99 8c3.73 0 6.84-2.55 7.73-6h-2.08c-.82 2.33-3.04 4-5.65 4-3.31 0-6-2.69-6-6s2.69-6 6-6c1.66 0 3.14.69 4.22 1.78L13 11h7V4l-2.35 2.35z",
                Color.WHITE);
        glyph.setSize(10, 10);
        refreshButton.setGraphic(glyph);

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
        //--------------For button Styling---------------------//

        //Populate the username and balance label to the loggedin user
        userNameLabel = new Label(LoginController.firsName + " " + LoginController.lastName);
        userBalanceLabel = new Label("CC: " + LoginController.balance);
        userNameLabel.setFont(new Font(15));
        userBalanceLabel.setFont(new Font(15));
        userBalanceLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #0b941b");
        usernameHbox.getChildren().add(userNameLabel);
        balanceHbox.getChildren().add(userBalanceLabel);

        Platform.runLater(() -> {
            //Load the image through the user picture url
            Image image = null;
            try {
                URL url = new URL(LoginController.picture);
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                image = new Image(inputStream);

            } catch (IOException e) {
                //If due to some problem the picture couldn't be loaded, show a default picture from local
                image = new Image(new File("src/com/lunchtime/assets/image/defaultUser.png").toURI().toString());
                e.printStackTrace();
            }
            profilePicture = new Circle(45, new ImagePattern(image));
            profilePictureHbox.getChildren().add(profilePicture);
        });

    }
}

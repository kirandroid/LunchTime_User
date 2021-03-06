/**
 * @author Kiran Pradhan
 * This controller class is used for handling all the UI events and request a login API with the given user credentials.
 * All the verification and validation is handled here.
 * This class also navigates to Dashboard Screen and Register Screen.
 */

package com.lunchtime.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lunchtime.Main;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.User;
import com.lunchtime.network.apiObjects.requests.LoginRequest;
import com.lunchtime.network.apiObjects.wrappers.UserWrapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private boolean emailIsValid = false;
    private boolean emailIsEmpty = true;

    private boolean passwordIsValid = false;
    private boolean passwordIsEmpty = true;

    public static int userId;
    public static String firsName;
    public static String lastName;
    public static String phoneNumber;
    public static String email;
    public static int balance;
    public static String picture;
    AnchorPane loadingHUD;


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
    private StackPane login_pane;

    @FXML
    private JFXTextField email_field;

    @FXML
    private JFXPasswordField password_field;

    @FXML
    private MediaView loginVideoPlayer;

    //Window close function for the close button
    @FXML
    void closeButtonClicked(ActionEvent event) {
        System.exit(0);
    }

    //Minimize the window
    @FXML
    void minimizeButtonClicked(ActionEvent event) {
        Main.primaryStage.setIconified(true);
    }


    //Run on Login Button Clicked
    @FXML
    void login_button_clicked(ActionEvent event) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //Create a loading HUD when this function runs.
        loadingHUD = new AnchorPane();
        JFXSpinner jfxSpinner = new JFXSpinner();
        jfxSpinner.setLayoutX(475);
        jfxSpinner.setLayoutY(275);
        loadingHUD.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6)");
        loadingHUD.getChildren().add(jfxSpinner);
        login_pane.getChildren().add(loadingHUD);

        //Check the validation from the field validator method.
        if (emailIsValid && !emailIsEmpty && passwordIsValid && !passwordIsEmpty) {
            //Hash the user password with SHA1 encryption
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(password_field.getText().getBytes("UTF-8"), 0, password_field.getText().length());
            String encriptedPassword = DatatypeConverter.printHexBinary(messageDigest.digest());

            //Make a login request body with email and sha1 encrypted password
            LoginRequest loginRequest = new LoginRequest(email_field.getText(), encriptedPassword);

            //Post a login request and listen for response and failure
            NetworkManager.getInstance().Login(loginRequest, new NetworkResponseListener<ApiBaseResponse<UserWrapper>>() {
                //If login is successful response is fetched
                @Override
                public void onResponseReceived(ApiBaseResponse<UserWrapper> userWrapperApiBaseResponse) {
                    User user = userWrapperApiBaseResponse.getData().getUser();
                    Platform.runLater(
                            () -> {
                                //Remove loading HUD
                                login_pane.getChildren().remove(loadingHUD);

                                try {
                                    //for global variable
                                    userId = user.getId();
                                    firsName = user.getFirst_name();
                                    lastName = user.getLast_name();
                                    phoneNumber = user.getPhone_number();
                                    email = user.getEmail();
                                    balance = user.getBalance();
                                    picture = user.getPicture();

                                    //Change the scene to Dashboard view
                                    StackPane pane = FXMLLoader.load(getClass().getResource("../views/dashboard_view.fxml"));
                                    login_pane.getChildren().setAll(pane);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );

                }

                @Override
                public void onError() {
                    Platform.runLater(() -> {
                        //Remove loading HUD
                        login_pane.getChildren().remove(loadingHUD);

                        //If login is failed show an error dialog.
                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setHeading(new Text("Error"));
                        content.setBody(new Text("Email or Password is Invalid!"));
                        JFXButton button = new JFXButton("Okay");
                        JFXDialog dialog = new JFXDialog(login_pane, content, JFXDialog.DialogTransition.CENTER);

                        button.setOnAction(event -> dialog.close());
                        content.setActions(button);

                        dialog.show();
                    });

                }
            });
        }else{
            //Remove loading HUD
            login_pane.getChildren().remove(loadingHUD);
        }

    }

    /**
     * Changes the current scene to register view when register button is clicked
     */
    @FXML
    void register_button_clicked(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/register_view.fxml"));
        login_pane.getChildren().setAll(pane);
    }

    /**
     * Validators for login fields, checks if the fields are empty and validates email format
     */
    private void fieldValidators() {
        //Field Required validator for email
        RequiredFieldValidator emailRequiredFieldValidator = new RequiredFieldValidator();
        email_field.getValidators().add(emailRequiredFieldValidator);
        emailRequiredFieldValidator.setMessage("Please enter an email!");
        email_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (email_field.validate()) {

                    System.out.println("Email not empty");
                    emailIsEmpty = false;
                } else {
                    System.out.println("Email empty");
                    emailIsEmpty = true;
                }

            }
        });
        email_field.textProperty().addListener((observable, oldValue, newValue) -> email_field.validate());

        //Field Required validator for password
        RequiredFieldValidator passwordRequiredFieldValidator = new RequiredFieldValidator();
        password_field.getValidators().add(passwordRequiredFieldValidator);
        passwordRequiredFieldValidator.setMessage("Please enter a password!");
        password_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (password_field.validate()) {
                    System.out.println("Password not empty");
                    passwordIsEmpty = false;
                } else {
                    System.out.println("Password empty");
                    passwordIsEmpty = true;
                }
            }
        });
        password_field.textProperty().addListener((observable, oldValue, newValue) -> password_field.validate());


        //Email Validator
        RegexValidator emailValidator = new RegexValidator();
        emailValidator.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); //Email Regex format
        email_field.setValidators(emailValidator);
        emailValidator.setMessage("Email is invalid!");
        email_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (email_field.validate()) {
                    System.out.println("Email valid");
                    emailIsValid = true;
                } else {
                    System.out.println("Email not valid");
                    emailIsValid = false;
                }
            }
        });
        email_field.textProperty().addListener((observable, oldValue, newValue) -> email_field.validate());


        //Password Validator
        RegexValidator passwordValidator = new RegexValidator();
        passwordValidator.setRegexPattern("^.{8,}$"); //Regex Pattern for limiting character to be atleast 8 characters long.
        password_field.setValidators(passwordValidator);
        passwordValidator.setMessage("Password should be atleast 8 characters long!");
        password_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (password_field.validate()) {
                    System.out.println("Password valid");
                    passwordIsValid = true;
                } else {
                    System.out.println("Password not valid");
                    passwordIsValid = false;
                }
            }
        });
        password_field.textProperty().addListener((observable, oldValue, newValue) -> password_field.validate());

    }


    /**
     * Runs when the screen initializes
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            email_field.requestFocus(); //Give focus in the email field to show early warning of empty field
        });


        fieldValidators(); //Call the field validator method for running all the validators when the screen starts

        //Set the video to play on the login screen
        final MediaPlayer video = new MediaPlayer(new Media(new File("src/com/lunchtime/assets/video/startVideo.mp4").toURI().toString()));
        video.setMute(true);
        video.setCycleCount(MediaPlayer.INDEFINITE);
        video.play();
        loginVideoPlayer.setMediaPlayer(video);
    }
}

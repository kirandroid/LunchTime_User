package com.lunchtime.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.requests.RegisterRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class register_controller implements Initializable {

    private boolean firstNameIsEmpty = true;
    private boolean firstNameIsValid = false;
    private boolean lastNameIsEmpty = true;
    private boolean lastNameIsValid = false;
    private boolean emailIsEmpty = true;
    private boolean emailIsValid = false;
    private boolean phoneIsEmpty = true;
    private boolean phoneIsValid = false;
    private boolean passwordIsEmpty = true;
    private boolean passwordIsValid = false;
    private boolean confirmPasswordIsEmpty = true;
    private boolean confirmPasswordIsValid = false;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private MediaView registerVideoPlayer;

    @FXML
    private JFXTextField phone_field;

    @FXML
    private JFXPasswordField password_field;

    @FXML
    private JFXTextField email_field;

    @FXML
    private JFXTextField last_name_field;

    @FXML
    private JFXTextField first_name_field;

    @FXML
    private JFXPasswordField confirm_password_field;

    @FXML
    void login_button_clicked(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
        registerPane.getChildren().setAll(pane);
    }

    @FXML
    void register_button_clicked(ActionEvent event) {
        RegisterRequest registerRequest = new RegisterRequest(first_name_field.getText(), last_name_field.getText(), phone_field.getText(), email_field.getText(), password_field.getText(), "google");

        NetworkManager.getInstance().Register(registerRequest, new NetworkResponseListener<ApiBaseResponse>() {
            @Override
            public void onResponseReceived(ApiBaseResponse apiBaseResponse) {
                Platform.runLater(
                            () -> {
                                try {
                                    StackPane pane = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
                                    registerPane.getChildren().setAll(pane);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
            }

            @Override
            public void onError() {
                System.out.println("Register Error!");
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldValidators();
        final MediaPlayer video = new MediaPlayer(new Media(new File("src/com/lunchtime/assets/registerVideo.mp4").toURI().toString()));
        video.setMute(true);
        video.setCycleCount(MediaPlayer.INDEFINITE);
        video.play();
        registerVideoPlayer.setMediaPlayer(video);
    }


    private void fieldValidators(){

        //Field Required validator for firstName
        RequiredFieldValidator firstNameRequiredFieldValidator = new RequiredFieldValidator();
        first_name_field.getValidators().add(firstNameRequiredFieldValidator);
        firstNameRequiredFieldValidator.setMessage("Please enter firstName!");
        first_name_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (first_name_field.validate()) {
                    System.out.println("firstName not empty");
                    firstNameIsEmpty = false;
                } else {
                    System.out.println("firstName empty");
                    firstNameIsEmpty = true;
                }

            }
        });
        first_name_field.textProperty().addListener((observable, oldValue, newValue) -> first_name_field.validate());


        //Field Required validator for lastName
        RequiredFieldValidator lastNameRequiredFieldValidator = new RequiredFieldValidator();
        last_name_field.getValidators().add(lastNameRequiredFieldValidator);
        lastNameRequiredFieldValidator.setMessage("Please enter lastName!");
        last_name_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (last_name_field.validate()) {
                    System.out.println("lastName not empty");
                    lastNameIsEmpty = false;
                } else {
                    System.out.println("lastName empty");
                    lastNameIsEmpty = true;
                }

            }
        });
        last_name_field.textProperty().addListener((observable, oldValue, newValue) -> last_name_field.validate());

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

        //Field Required validator for phone
        RequiredFieldValidator phoneRequiredFieldValidator = new RequiredFieldValidator();
        phone_field.getValidators().add(phoneRequiredFieldValidator);
        phoneRequiredFieldValidator.setMessage("Please enter an email!");
        phone_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (phone_field.validate()) {

                    System.out.println("Phone not empty");
                    phoneIsEmpty = false;
                } else {
                    System.out.println("Phone empty");
                    phoneIsEmpty = true;
                }

            }
        });
        phone_field.textProperty().addListener((observable, oldValue, newValue) -> phone_field.validate());


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


        //Field Required validator for Confirm password
        RequiredFieldValidator confirmPasswordRequiredFieldValidator = new RequiredFieldValidator();
        confirm_password_field.getValidators().add(confirmPasswordRequiredFieldValidator);
        confirmPasswordRequiredFieldValidator.setMessage("Please enter a password!");
        confirm_password_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (confirm_password_field.validate()) {
                    System.out.println("Confirm Password not empty");
                    confirmPasswordIsEmpty = false;
                } else {
                    System.out.println("Confirm Password empty");
                    confirmPasswordIsEmpty = true;
                }
            }
        });
        confirm_password_field.textProperty().addListener((observable, oldValue, newValue) -> confirm_password_field.validate());
//----------------------------------------------------------------------------------------------------------------------------------//

//
//        //Email Validator
//        RegexValidator emailValidator = new RegexValidator();
//        emailValidator.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
//        email_field.setValidators(emailValidator);
//        emailValidator.setMessage("Email is invalid!");
//        email_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (email_field.validate()) {
//                    System.out.println("Email valid");
//                    emailIsValid = true;
//                } else {
//                    System.out.println("Email not valid");
//                    emailIsValid = false;
//                }
//            }
//        });
//        email_field.textProperty().addListener((observable, oldValue, newValue) -> email_field.validate());
//
//

//        //Field Required validator for password
//        NumberValidator phoneRequiredFieldValidator = new NumberValidator();
//        phone_field.getValidators().add(phoneRequiredFieldValidator);
//        phoneRequiredFieldValidator.setMessage("Please enter a mobile number!");
//        phone_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                phone_field.validate();
//            }
//        });
//        phone_field.textProperty().addListener((observable, oldValue, newValue) -> phone_field.validate());

//        //Password Validator
//        RegexValidator passwordValidator = new RegexValidator();
//        passwordValidator.setRegexPattern("^.{8,}$");
//        password_field.setValidators(passwordValidator);
//        passwordValidator.setMessage("Password should be atleast 8 characters long!");
//        password_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (password_field.validate()) {
//                    System.out.println("Password valid");
//                    passwordIsValid = true;
//                } else {
//                    System.out.println("Password not valid");
//                    passwordIsValid = false;
//                }
//            }
//        });
//        password_field.textProperty().addListener((observable, oldValue, newValue) -> password_field.validate());

    }

}

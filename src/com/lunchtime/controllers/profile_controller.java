package com.lunchtime.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.User;
import com.lunchtime.network.apiObjects.requests.UpdateProfileRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class profile_controller implements Initializable {

    private boolean firstNameIsEmpty = true;
    private boolean lastNameIsEmpty = true;
    private boolean emailIsEmpty = true;
    private boolean emailIsValid = false;
    private boolean phoneIsEmpty = true;
    private boolean phoneIsValid = false;
    private boolean passwordIsEmpty = true;
    private boolean passwordIsValid = false;


    final FileChooser fileChooser = new FileChooser();
    File file;

    @FXML
    private StackPane profilePane;

    @FXML
    private JFXTextField first_name_field;

    @FXML
    private JFXTextField last_name_field;

    @FXML
    private JFXTextField email_field;

    @FXML
    private JFXPasswordField password_field;

    @FXML
    private JFXTextField phone_field;

    @FXML
    private Circle profilePictureView;

    @FXML
    private Circle addPictureView;

    @FXML
    void selectPictureClicked(MouseEvent event) throws MalformedURLException {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpg"));
        file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String imagePath = file.toURI().toURL().toString();
            profilePictureView.setFill(new ImagePattern(new Image(imagePath)));

        } else {
            System.out.println("Error");
        }
    }

    @FXML
    void update_button_clicked(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldValidators();
        Platform.runLater(() -> {
            login_controller loginController = new login_controller();
            profilePictureView.setFill(new ImagePattern(new Image(loginController.picture)));
            first_name_field.setText(loginController.firsName);
            last_name_field.setText(loginController.lastName);
            email_field.setText(loginController.email);
            phone_field.setText(loginController.phoneNumber);
        });
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
        phoneRequiredFieldValidator.setMessage("Please enter an phone!");
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

//----------------------------------------------------------------------------------------------------------------------------------//

//        //Email Validator
        RegexValidator emailValidator = new RegexValidator();
        emailValidator.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
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

//        //Field Required validator for phone
        NumberValidator phoneFieldValidator = new NumberValidator();
        phone_field.getValidators().add(phoneFieldValidator);
        phoneFieldValidator.setMessage("Only numbers accepted!");
        phone_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (phone_field.validate()) {
                    System.out.println("Phone valid");
                    phoneIsValid = true;
                } else {
                    System.out.println("Phone not valid");
                    phoneIsValid = false;
                }
            }
        });
        phone_field.textProperty().addListener((observable, oldValue, newValue) -> phone_field.validate());

//        //Password Validator
        RegexValidator passwordValidator = new RegexValidator();
        passwordValidator.setRegexPattern("^.{8,}$");
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


//        //Phone length Validator
        RegexValidator phoneLengthValidator = new RegexValidator();
        phoneLengthValidator.setRegexPattern("^.{10}$");
        phone_field.setValidators(phoneLengthValidator);
        phoneLengthValidator.setMessage("Phone Number should be 10 characters long!");
        phone_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (phone_field.validate()) {
                    System.out.println("Phone valid");
                    phoneIsValid = true;
                } else {
                    System.out.println("Phone not valid");
                    phoneIsValid = false;
                }
            }
        });
        phone_field.textProperty().addListener((observable, oldValue, newValue) -> phone_field.validate());

    }
}

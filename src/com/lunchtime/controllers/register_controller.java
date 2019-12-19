/**
 * @author Kiran Pradhan
 * This controller class is used for handling all the UI events and request a Register API with the given user credentials.
 * All the verification and validation is handled here.
 * This class also navigates to Login Screen.
 * */

package com.lunchtime.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.UploadAPI;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.UploadResponse;
import com.lunchtime.network.apiObjects.requests.RegisterRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.UUID;

public class register_controller implements Initializable {

    private boolean firstNameIsEmpty = true;
    private boolean lastNameIsEmpty = true;
    private boolean emailIsEmpty = true;
    private boolean emailIsValid = false;
    private boolean phoneIsEmpty = true;
    private boolean phoneIsValid = false;
    private boolean passwordIsEmpty = true;
    private boolean passwordIsValid = false;

    @FXML
    private StackPane registerPane;

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
    private Circle profilePictureView;

    @FXML
    private Circle addPictureView;


    final FileChooser fileChooser = new FileChooser();
    File file;

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
    void closeButtonClicked(ActionEvent event) {
        System.exit(0);
    }


    @FXML
    void login_button_clicked(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
        registerPane.getChildren().setAll(pane);
    }

    @FXML
    void register_button_clicked(ActionEvent event) throws NoSuchAlgorithmException {

        if (!firstNameIsEmpty && !lastNameIsEmpty && !emailIsEmpty && !passwordIsEmpty && !phoneIsEmpty && emailIsValid && passwordIsValid && phoneIsValid){
            long timestamp = System.currentTimeMillis();
            String apiKey = "588753441842251";
            String eager = "w_400,h_400,c_pad";
            String publicId = String.valueOf(UUID.randomUUID());
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(("eager=w_400,h_400,c_pad&public_id=" + publicId + "&timestamp=" + timestamp + "oWEOZ2sxuB2cpixDPaa6XhLS23E").getBytes());
            String signature = DatatypeConverter.printHexBinary(messageDigest.digest());


            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            builder.addFormDataPart("timestamp", String.valueOf(timestamp))
                    .addFormDataPart("public_id", publicId)
                    .addFormDataPart("api_key", apiKey)
                    .addFormDataPart("eager", eager)
                    .addFormDataPart("signature", signature)
                    .addFormDataPart("file", file.getName(), RequestBody.create(MultipartBody.FORM, file));

            RequestBody requestBody = builder.build();


            Call<UploadResponse> call = UploadAPI.apiService.upload(requestBody);
            call.enqueue(new Callback<UploadResponse>() {
                @Override
                public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                    MessageDigest messageDigest = null;
                    try {
                        messageDigest = MessageDigest.getInstance("SHA-1");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    try {
                        messageDigest.update(password_field.getText().getBytes("UTF-8"), 0, password_field.getText().length());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String encriptedPassword = DatatypeConverter.printHexBinary(messageDigest.digest());

                    RegisterRequest registerRequest = new RegisterRequest(first_name_field.getText(), last_name_field.getText(), phone_field.getText(), email_field.getText(), encriptedPassword, response.body().getEager().get(0).getSecureUrl());

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
                            Platform.runLater(() -> {
                                JFXDialogLayout content = new JFXDialogLayout();
                                content.setHeading(new Text("Error"));
                                content.setBody(new Text("Email Already Used"));
                                JFXButton button = new JFXButton("Okay");
                                JFXDialog dialog = new JFXDialog(registerPane, content, JFXDialog.DialogTransition.CENTER);

                                button.setOnAction(event -> dialog.close());
                                content.setActions(button);

                                dialog.show();
                            });
                        }
                    });
                }

                @Override
                public void onFailure(Call<UploadResponse> call, Throwable throwable) {
                    System.out.println(throwable);
                    System.out.println("Error");
                }
            });

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldValidators();
        final MediaPlayer video = new MediaPlayer(new Media(new File("src/com/lunchtime/assets/video/registerVideo.mp4").toURI().toString()));
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

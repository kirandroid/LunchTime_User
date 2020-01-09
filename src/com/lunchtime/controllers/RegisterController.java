/**
 * @author Kiran Pradhan
 * This controller class is used for handling all the UI events and request a Register API with the given user credentials.
 * All the verification and validation is handled here.
 * This class also navigates to Login Screen.
 */

package com.lunchtime.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lunchtime.Main;
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
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
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

public class RegisterController implements Initializable {

    private boolean firstNameIsEmpty = true;
    private boolean lastNameIsEmpty = true;
    private boolean emailIsEmpty = true;
    private boolean emailIsValid = false;
    private boolean phoneIsEmpty = true;
    private boolean phoneIsValid = false;
    private boolean passwordIsEmpty = true;
    private boolean passwordIsValid = false;
    AnchorPane loadingHUD;

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
    private StackPane circleAddButtonStackPane;


    final FileChooser fileChooser = new FileChooser();
    File file;

    // Select picture window with picture filter
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

    //Close the window
    @FXML
    void closeButtonClicked(ActionEvent event) {
        System.exit(0);
    }

    //Minimize the window
    @FXML
    void minimizeButtonClicked(ActionEvent event) {
        Main.primaryStage.setIconified(true);
    }


    /**
     * Change the scene to login view on login button clicked.
     */
    @FXML
    void login_button_clicked(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
        registerPane.getChildren().setAll(pane);
    }

    /**
     * Run this when register button is clicked
     */
    @FXML
    void register_button_clicked(ActionEvent event) throws NoSuchAlgorithmException {

        //Create a loading HUD when this fuction runs.
        loadingHUD = new AnchorPane();
        JFXSpinner jfxSpinner = new JFXSpinner();
        jfxSpinner.setLayoutX(475);
        jfxSpinner.setLayoutY(275);
        loadingHUD.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6)");
        loadingHUD.getChildren().add(jfxSpinner);
        registerPane.getChildren().add(loadingHUD);

        //Check the validations
        if (!firstNameIsEmpty && !lastNameIsEmpty && !emailIsEmpty && !passwordIsEmpty && !phoneIsEmpty && emailIsValid && passwordIsValid && phoneIsValid) {
            //check if the profile picture is null
            if (file != null) {
                //if the file is not null upload the picture in an external API (Cloudinary)

                //------------------For creating the signature for using in api---------------------//
                long timestamp = System.currentTimeMillis();
                String apiKey = "588753441842251";
                String eager = "w_400,h_400,c_pad";
                String publicId = String.valueOf(UUID.randomUUID());
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                messageDigest.update(("eager=w_400,h_400,c_pad&public_id=" + publicId + "&timestamp=" + timestamp + "oWEOZ2sxuB2cpixDPaa6XhLS23E").getBytes());
                String signature = DatatypeConverter.printHexBinary(messageDigest.digest());
                //------------------For creating the signature for using in api---------------------//

                //Create a multipart builder for the request body
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("timestamp", String.valueOf(timestamp)) //Timestamp used when creating the signature
                        .addFormDataPart("public_id", publicId) //Unique name for the upload file so that it will not override the existing file in the server
                        .addFormDataPart("api_key", apiKey) //Unique API Key provided by the API provider.
                        .addFormDataPart("eager", eager) //Custom Dimension file the request in the response for smaller file sizes
                        .addFormDataPart("signature", signature) //Unique signature for upload
                        .addFormDataPart("file", file.getName(), RequestBody.create(MultipartBody.FORM, file)); //Picture file

                RequestBody requestBody = builder.build();

                //Run the Upload Api with the request body
                Call<UploadResponse> call = UploadAPI.apiService.upload(requestBody);

                call.enqueue(new Callback<UploadResponse>() {
                    @Override
                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                        //if the picture upload is successful run the register method with the picture parameter which is the response gotten from the upload API
                        register(response.body().getEager().get(0).getSecureUrl());
                    }

                    @Override
                    public void onFailure(Call<UploadResponse> call, Throwable throwable) {
                        //if the upload is failed remove the loading HUD
                        registerPane.getChildren().remove(loadingHUD);
                        System.out.println(throwable);
                        System.out.println("Error");
                    }
                });
            } else {
                //if the picture file is null, directly run register method with a default picture
                register("https://res.cloudinary.com/kirandroid/image/upload/v1578395816/default_ksx4rb.png");
            }
        } else {
            //if validation is incorrect remove the loading HUD.
            registerPane.getChildren().remove(loadingHUD);
        }
    }

    /**
     * Method for registering an account
     */
    public void register(String picture) {
        //Encrypt the entered password with an SHA1 hash
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


        //Create a register request with all the entered fields and an encrypted password rather than plan text password
        RegisterRequest registerRequest = new RegisterRequest(first_name_field.getText(), last_name_field.getText(), phone_field.getText(), email_field.getText(), encriptedPassword, picture);

        NetworkManager.getInstance().Register(registerRequest, new NetworkResponseListener<ApiBaseResponse>() {
            @Override
            public void onResponseReceived(ApiBaseResponse apiBaseResponse) {
                //If the registration is successful
                Platform.runLater(
                        () -> {
                            //Remove the loading HUD
                            registerPane.getChildren().remove(loadingHUD);

                            //Show a Success Dialog
                            JFXDialogLayout content = new JFXDialogLayout();
                            content.setHeading(new Text("Success"));
                            content.setBody(new Text("Account Registration Successful!"));
                            JFXButton button = new JFXButton("GoTo Login Page");
                            JFXDialog dialog = new JFXDialog(registerPane, content, JFXDialog.DialogTransition.CENTER);

                            button.setOnAction(event -> {
                                try {
                                    //Change the scene to login view on dialog button clicked
                                    StackPane pane = FXMLLoader.load(getClass().getResource("../views/login_view.fxml"));
                                    registerPane.getChildren().setAll(pane);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            content.setActions(button);
                            dialog.show();
                        }
                );
            }

            @Override
            public void onError() {
                Platform.runLater(() -> {
                    //If registration failed remove the loading HUD and show an error dialog
                    registerPane.getChildren().remove(loadingHUD);
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

    //Run this on the screen initialization
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // fill the picture field with a default picture
        profilePictureView.setFill(new ImagePattern(new Image(new File("src/com/lunchtime/assets/image/defaultUser.png").toURI().toString())));

        // create a Add picure button in the picture field
        JFXButton addButton = new JFXButton("");
        addButton.setButtonType(JFXButton.ButtonType.RAISED);
        addButton.setStyle("-fx-background-radius: 40;-fx-background-color: #db0f4b");
        addButton.setPrefSize(26, 26);

        // Add an SVG for a select button
        SVGGlyph glyph = new SVGGlyph(-1,
                "AddIcon",
                "M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z",
                Color.WHITE);
        glyph.setSize(10, 10);
        addButton.setGraphic(glyph);
        circleAddButtonStackPane.setAlignment(Pos.BOTTOM_RIGHT);
        circleAddButtonStackPane.getChildren().addAll(addButton);

        addButton.setOnAction(param -> {
            //Open a file chooser window with picture filter
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files",
                            "*.png", "*.jpg"));
            file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                //save the file in a variable
                String imagePath = null;
                try {
                    imagePath = file.toURI().toURL().toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                //Update the picture view with the file path
                profilePictureView.setFill(new ImagePattern(new Image(imagePath)));

            } else {
                System.out.println("Error");
            }
        });
        //------------------------------------------------------------------------

        fieldValidators();  //initialize the validators

        //add and play a video in the register pane
        final MediaPlayer video = new MediaPlayer(new Media(new File("src/com/lunchtime/assets/video/startVideo.mp4").toURI().toString()));
        video.setMute(true);
        video.setCycleCount(MediaPlayer.INDEFINITE);
        video.play();
        registerVideoPlayer.setMediaPlayer(video);
    }


    private void fieldValidators() {

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

//        //Number validator for phone
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

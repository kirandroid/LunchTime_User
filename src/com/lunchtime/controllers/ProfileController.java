/**
 * @author Kiran Pradhan
 * This controller class displays the user detail and image to the form.
 * A refresh button triggers event that will fetch user detail and updates the user observer.
 * An update request is called which updates the user table with the given user information.
 * A image upload api is called when user adds a profile picture.
 */

package com.lunchtime.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.UploadAPI;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.UploadResponse;
import com.lunchtime.network.apiObjects.models.User;
import com.lunchtime.network.apiObjects.models.UserObservable;
import com.lunchtime.network.apiObjects.requests.UpdateProfileRequest;
import com.lunchtime.network.apiObjects.wrappers.UserWrapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class ProfileController implements Initializable {

    private boolean firstNameIsEmpty = false;
    private boolean lastNameIsEmpty = false;
    private boolean emailIsEmpty = false;
    private boolean emailIsValid = true;
    private boolean phoneIsEmpty = false;
    private boolean phoneIsValid = true;
    AnchorPane loadingHUD;


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
    private JFXTextField phone_field;

    @FXML
    private Circle profilePictureView;

    @FXML
    private StackPane circleAddButtonStackPane;

    //Open File chooser with picture format filter
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

    //Update profile method
    @FXML
    void update_button_clicked(ActionEvent event) throws NoSuchAlgorithmException {
        //Create a loading HUD when this fuction runs.
        loadingHUD = new AnchorPane();
        JFXSpinner jfxSpinner = new JFXSpinner();
        jfxSpinner.setLayoutX(375);
        jfxSpinner.setLayoutY(260);
        loadingHUD.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6)");
        loadingHUD.getChildren().add(jfxSpinner);
        profilePane.getChildren().add(loadingHUD);

        //Check the validators
        if (!firstNameIsEmpty && !lastNameIsEmpty && !emailIsEmpty && !phoneIsEmpty && emailIsValid && phoneIsValid && file != null) {
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
            builder.addFormDataPart("timestamp", String.valueOf(timestamp))//Timestamp used when creating the signature
                    .addFormDataPart("public_id", publicId)//Unique name for the upload file so that it will not override the existing file in the server
                    .addFormDataPart("api_key", apiKey)//Unique API Key provided by the API provider.
                    .addFormDataPart("eager", eager) //Custom Dimension file the request in the response for smaller file sizes
                    .addFormDataPart("signature", signature) //Unique signature for upload
                    .addFormDataPart("file", file.getName(), RequestBody.create(MultipartBody.FORM, file)); //Picture file

            RequestBody requestBody = builder.build();

            //Run the Upload Api with the request body
            Call<UploadResponse> call = UploadAPI.apiService.upload(requestBody);
            call.enqueue(new Callback<UploadResponse>() {
                @Override
                public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                    //if the picture upload is successful run the update method with the picture parameter which is the response gotten from the upload API
                    UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(first_name_field.getText(), last_name_field.getText(), phone_field.getText(), email_field.getText(), LoginController.userId, response.body().getEager().get(0).getSecureUrl());

                    NetworkManager.getInstance().Update(updateProfileRequest, new NetworkResponseListener<ApiBaseResponse>() {
                        @Override
                        public void onResponseReceived(ApiBaseResponse apiBaseResponse) {
                            //if update is successful
                            Platform.runLater(
                                    () -> {
                                        //remove loading HUD
                                        profilePane.getChildren().remove(loadingHUD);
                                        //show success dialog
                                        JFXDialogLayout content = new JFXDialogLayout();
                                        content.setHeading(new Text("Success"));
                                        content.setBody(new Text("Profile Successfully Updated"));
                                        JFXButton button = new JFXButton("Okay");
                                        JFXDialog dialog = new JFXDialog(profilePane, content, JFXDialog.DialogTransition.CENTER);

                                        button.setOnAction(event -> dialog.close());
                                        content.setActions(button);
                                        dialog.show();

                                        //Feed the new data to the user observable
                                        List<User> users = new ArrayList<>();
                                        users.add(new User(LoginController.userId, first_name_field.getText(), last_name_field.getText(), email_field.getText(), phone_field.getText(), response.body().getEager().get(0).getSecureUrl(), LoginController.balance));
                                        UserObservable userObservable = new UserObservable();

                                        for (User user : users) {
                                            userObservable.addObserver(user);
                                        }
                                        userObservable.UserObservable();
                                    }
                            );
                        }

                        @Override
                        public void onError() {
                            Platform.runLater(() -> {
                                //remove loading HUD
                                profilePane.getChildren().remove(loadingHUD);
                                //show error dialog
                                JFXDialogLayout content = new JFXDialogLayout();
                                content.setHeading(new Text("Error"));
                                content.setBody(new Text("Email Already Used"));
                                JFXButton button = new JFXButton("Okay");
                                JFXDialog dialog = new JFXDialog(profilePane, content, JFXDialog.DialogTransition.CENTER);

                                button.setOnAction(event -> dialog.close());
                                content.setActions(button);

                                dialog.show();
                            });
                        }
                    });
                }

                @Override
                public void onFailure(Call<UploadResponse> call, Throwable throwable) {
                    //remove loading HUD
                    profilePane.getChildren().remove(loadingHUD);
                    System.out.println(throwable);
                    System.out.println("Error");
                }
            });

        } else {
            //if file is null update as it is with the existing data
            UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(first_name_field.getText(), last_name_field.getText(), phone_field.getText(), email_field.getText(), LoginController.userId, LoginController.picture);

            NetworkManager.getInstance().Update(updateProfileRequest, new NetworkResponseListener<ApiBaseResponse>() {
                @Override
                public void onResponseReceived(ApiBaseResponse apiBaseResponse) {
                    Platform.runLater(
                            () -> {
                                profilePane.getChildren().remove(loadingHUD);
                                JFXDialogLayout content = new JFXDialogLayout();
                                content.setHeading(new Text("Success"));
                                content.setBody(new Text("Profile Successfully Updated"));
                                JFXButton button = new JFXButton("Okay");
                                JFXDialog dialog = new JFXDialog(profilePane, content, JFXDialog.DialogTransition.CENTER);

                                button.setOnAction(event -> dialog.close());
                                content.setActions(button);

                                dialog.show();

                                List<User> users = new ArrayList<>();
                                users.add(new User(LoginController.userId, first_name_field.getText(), last_name_field.getText(), email_field.getText(), phone_field.getText(), LoginController.picture, LoginController.balance));
                                UserObservable userObservable = new UserObservable();

                                for (User user : users) {
                                    userObservable.addObserver(user);
                                }
                                userObservable.UserObservable();
                            }
                    );
                }

                @Override
                public void onError() {
                    Platform.runLater(() -> {
                        profilePane.getChildren().remove(loadingHUD);
                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setHeading(new Text("Error"));
                        content.setBody(new Text("Email Already Used"));
                        JFXButton button = new JFXButton("Okay");
                        JFXDialog dialog = new JFXDialog(profilePane, content, JFXDialog.DialogTransition.CENTER);

                        button.setOnAction(event -> dialog.close());
                        content.setActions(button);

                        dialog.show();
                    });
                }
            });
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

        fieldValidators();
        Platform.runLater(() -> {
            Image image = null;
            try {
                URL url = new URL(LoginController.picture);
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                image = new Image(inputStream);

            } catch (IOException e) {
                image = new Image(new File("src/com/lunchtime/assets/image/defaultUser.png").toURI().toString());
                e.printStackTrace();
            }
            profilePictureView.setFill(new ImagePattern(image));
            first_name_field.setText(LoginController.firsName);
            last_name_field.setText(LoginController.lastName);
            email_field.setText(LoginController.email);
            phone_field.setText(LoginController.phoneNumber);
        });
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

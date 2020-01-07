/**
 * @author Kiran Pradhan
 * This controller class displays the user detail and image to the form.
 * A refresh button triggers event that will fetch user detail and updates the user observer.
 * An update request is called which updates the user table with the given user information.
 * A image upload api is called when user adds a profile picture.
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
import com.lunchtime.network.apiObjects.models.User;
import com.lunchtime.network.apiObjects.models.UserObservable;
import com.lunchtime.network.apiObjects.requests.OrderRequest;
import com.lunchtime.network.apiObjects.requests.RegisterRequest;
import com.lunchtime.network.apiObjects.requests.UpdateProfileRequest;
import com.lunchtime.network.apiObjects.wrappers.UserWrapper;
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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class profile_controller implements Initializable {

    private boolean firstNameIsEmpty = false;
    private boolean lastNameIsEmpty = false;
    private boolean emailIsEmpty = false;
    private boolean emailIsValid = true;
    private boolean phoneIsEmpty = false;
    private boolean phoneIsValid = true;


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
    void onRefreshClicked(ActionEvent event) {
        NetworkManager.getInstance().UserDetail(login_controller.userId, new NetworkResponseListener<ApiBaseResponse<UserWrapper>>() {
            @Override
            public void onResponseReceived(ApiBaseResponse<UserWrapper> userWrapperApiBaseResponse) {
                Platform.runLater(() -> {
                    User userDetailResponse = userWrapperApiBaseResponse.getData().getUser();
                    List<User> users = new ArrayList<>();
                    users.add(new User(userDetailResponse.getId(), userDetailResponse.getFirst_name(), userDetailResponse.getLast_name(), userDetailResponse.getEmail(), userDetailResponse.getPhone_number(), userDetailResponse.getPicture(), userDetailResponse.getBalance()));
                    UserObservable userObservable = new UserObservable();

                    for (User user: users){
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
    void update_button_clicked(ActionEvent event) throws NoSuchAlgorithmException {

        if (!firstNameIsEmpty && !lastNameIsEmpty && !emailIsEmpty && !phoneIsEmpty && emailIsValid && phoneIsValid && file != null){
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
                    login_controller login_controller = new login_controller();

                    UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(first_name_field.getText(), last_name_field.getText(), phone_field.getText(), email_field.getText(), login_controller.userId, response.body().getEager().get(0).getSecureUrl());

                    NetworkManager.getInstance().Update(updateProfileRequest, new NetworkResponseListener<ApiBaseResponse>() {
                        @Override
                        public void onResponseReceived(ApiBaseResponse apiBaseResponse) {
                            Platform.runLater(
                                    () -> {
                                        JFXDialogLayout content = new JFXDialogLayout();
                                        content.setHeading(new Text("Success"));
                                        content.setBody(new Text("Profile Successfully Updated"));
                                        JFXButton button = new JFXButton("Okay");
                                        JFXDialog dialog = new JFXDialog(profilePane, content, JFXDialog.DialogTransition.CENTER);

                                        button.setOnAction(event -> dialog.close());
                                        content.setActions(button);

                                        dialog.show();


                                        List<User> users = new ArrayList<>();
                                        users.add(new User(login_controller.userId, first_name_field.getText(), last_name_field.getText(), email_field.getText(), phone_field.getText(), response.body().getEager().get(0).getSecureUrl(), login_controller.balance));
                                        UserObservable userObservable = new UserObservable();

                                        for (User user: users){
                                            userObservable.addObserver(user);
                                        }
                                        userObservable.UserObservable();
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
                    System.out.println(throwable);
                    System.out.println("Error");
                }
            });

        }else{
            login_controller login_controller = new login_controller();

            UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(first_name_field.getText(), last_name_field.getText(), phone_field.getText(), email_field.getText(), login_controller.userId, login_controller.picture);

            NetworkManager.getInstance().Update(updateProfileRequest, new NetworkResponseListener<ApiBaseResponse>() {
                @Override
                public void onResponseReceived(ApiBaseResponse apiBaseResponse) {
                    Platform.runLater(
                            () -> {
                                JFXDialogLayout content = new JFXDialogLayout();
                                content.setHeading(new Text("Success"));
                                content.setBody(new Text("Profile Successfully Updated"));
                                JFXButton button = new JFXButton("Okay");
                                JFXDialog dialog = new JFXDialog(profilePane, content, JFXDialog.DialogTransition.CENTER);

                                button.setOnAction(event -> dialog.close());
                                content.setActions(button);

                                dialog.show();

                                List<User> users = new ArrayList<>();
                                users.add(new User(login_controller.userId, first_name_field.getText(), last_name_field.getText(), email_field.getText(), phone_field.getText(), login_controller.picture, login_controller.balance));
                                UserObservable userObservable = new UserObservable();

                                for (User user: users){
                                    userObservable.addObserver(user);
                                }
                                userObservable.UserObservable();
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
        fieldValidators();
        Platform.runLater(() -> {
            login_controller loginController = new login_controller();


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
            profilePictureView.setFill(new ImagePattern(image));
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
//        RequiredFieldValidator passwordRequiredFieldValidator = new RequiredFieldValidator();
//        password_field.getValidators().add(passwordRequiredFieldValidator);
//        passwordRequiredFieldValidator.setMessage("Please enter a password!");
//        password_field.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (password_field.validate()) {
//                    System.out.println("Password not empty");
//                    passwordIsEmpty = false;
//                } else {
//                    System.out.println("Password empty");
//                    passwordIsEmpty = true;
//                }
//            }
//        });
//        password_field.textProperty().addListener((observable, oldValue, newValue) -> password_field.validate());

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

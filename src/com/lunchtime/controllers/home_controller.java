package com.lunchtime.controllers;

import com.lunchtime.network.UploadAPI;
import com.lunchtime.network.apiObjects.models.UploadResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.UUID;

public class home_controller implements Initializable {
    @FXML
    private ImageView selectedImageView;


    @FXML
    private StackPane homeRootPane;


    final FileChooser fileChooser = new FileChooser();
    File file;
    ProgressIndicator progressIndicator = new ProgressIndicator();

    @FXML
    void chooseImageButtonClicked(ActionEvent event) throws MalformedURLException {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpg"));
        file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String imagePath = file.toURI().toURL().toString();
            selectedImageView.setImage(new Image(imagePath));

        } else {
            System.out.println("Error");
        }
    }


    @FXML
    void uploadImageButtonClicked(ActionEvent event) throws NoSuchAlgorithmException {
        progressIndicator.setVisible(true);
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
                System.out.println(response.body().getEager());
                System.out.println("Success");
                progressIndicator.setVisible(false);
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable throwable) {
                System.out.println(throwable);
                System.out.println("Error");
                progressIndicator.setVisible(false);
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeRootPane.getChildren().add(progressIndicator);
        progressIndicator.setVisible(false);
        progressIndicator.isIndeterminate();
    }
}

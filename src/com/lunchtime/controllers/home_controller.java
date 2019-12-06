package com.lunchtime.controllers;

import com.lunchtime.network.NetworkManager;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.models.UploadResponse;
import com.lunchtime.network.apiObjects.requests.UploadRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.UUID;

public class home_controller {
    @FXML
    private ImageView selectedImageView;

    final FileChooser fileChooser = new FileChooser();
    File file;

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
    void uploadImageButtonClicked(ActionEvent event) throws NoSuchAlgorithmException, NoSuchProviderException {
        System.out.println("Clicked");
        long timestamp = System.currentTimeMillis();
        String publicId = "test";
        String apiKey = "588753441842251";
        String eager = "w_400,h_400,c_pad";
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
//        System.out.println("eager=w_400,h_400,c_pad&public_id=test&timestamp="+timestamp+"oWEOZ2sxuB2cpixDPaa6XhLS23E");
        messageDigest.update(("eager=w_400,h_400,c_pad&public_id=test&timestamp="+timestamp+"oWEOZ2sxuB2cpixDPaa6XhLS23E").getBytes());
        String signature = DatatypeConverter.printHexBinary(messageDigest.digest());

        UploadRequest uploadRequest = new UploadRequest(1575637447, "test", "588753441842251", "w_400,h_400,c_pad", "https://res.cloudinary.com/kirandroid/image/upload/w_400,h_400,c_pad/v1575632920/test.jpg", "ee2a4203add53cf8236def2ca40b227cd80aad2f");

        NetworkManager.getInstance().UploadImage(uploadRequest, new NetworkResponseListener<UploadResponse>() {
            @Override
            public void onResponseReceived(UploadResponse uploadResponse) {
                System.out.println("Successfully uploaded");
                System.out.println(uploadResponse);
            }

            @Override
            public void onError() {
                System.out.println("Error");
            }
        });
    }
}

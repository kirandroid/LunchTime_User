package com.lunchtime.network.apiObjects.models;

import com.google.gson.annotations.SerializedName;
import com.lunchtime.controllers.LoginController;
import com.lunchtime.controllers.DashboardController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Observable;
import java.util.Observer;

public class User implements Observer {
    @SerializedName("id")
    private Integer id;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("picture")
    private String picture;

    @SerializedName("balance")
    private int balance;

    public User() {
    }

    public User(Integer id, String first_name, String last_name, String email, String phone_number, String picture, int balance) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.picture = picture;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getPicture() {
        return picture;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public void update(Observable o, Object arg) {
        DashboardController.userNameLabel.setText(getFirst_name()+" "+getLast_name());
        DashboardController.userBalanceLabel.setText("CC: " + getBalance());
        DashboardController.profilePicture.setFill(new ImagePattern(new Image(getPicture())));

        LoginController.userId = getId();
        LoginController.firsName = getFirst_name();
        LoginController.lastName = getLast_name();
        LoginController.phoneNumber = getPhone_number();
        LoginController.email = getEmail();
        LoginController.picture = getPicture();
        LoginController.balance = getBalance();

        System.out.println("new value "+getFirst_name());
        System.out.println("new value "+getLast_name());
        System.out.println("new value "+getPicture());
        System.out.println("new value "+getEmail());
        System.out.println("new value "+getPhone_number());;
        System.out.println("new value "+getBalance());


    }
}
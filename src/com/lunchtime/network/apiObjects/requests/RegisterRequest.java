/**
 * @Author Kiran Pradhananga
 * Request model for user registration
 */

package com.lunchtime.network.apiObjects.requests;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("picture")
    private String picture;

    public RegisterRequest(String first_name, String last_name, String phone_number, String email, String password, String picture) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email = email;
        this.password = password;
        this.picture = picture;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
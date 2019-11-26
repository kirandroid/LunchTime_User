package com.lunchtime.network.apiObjects.models;

import com.google.gson.annotations.SerializedName;

public class Menu {
    @SerializedName("food_id")
    private  Integer food_id;
    @SerializedName("food_name")
    private String food_name;
    @SerializedName("food_price")
    private Integer food_price;
    @SerializedName("picture")
    private String picture;
    @SerializedName("available")
    private Integer available;

    public Menu(Integer food_id, String food_name, Integer food_price, String picture, Integer available) {
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_price = food_price;
        this.picture = picture;
        this.available = available;
    }

    public Integer getFood_id() {
        return food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public Integer getFood_price() {
        return food_price;
    }

    public String getPicture() {
        return picture;
    }

    public Integer isAvailable() {
        return available;
    }
}

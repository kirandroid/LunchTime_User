/**
 * @Author Kiran Pradhananga
 * User orders response model
 * */

package com.lunchtime.network.apiObjects.models;

import com.google.gson.annotations.SerializedName;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class MyOrder {
    @SerializedName("date")
    private  String date;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("total_price")
    private Integer total_price;
    @SerializedName("food_name")
    private String food_name;
    @SerializedName("order_id")
    private Integer order_id;
    @SerializedName("status")
    private String status;
    @SerializedName("picture")
    private String picture;

    public MyOrder(String date, Integer quantity, Integer total_price, String food_name, String picture, String status, Integer order_id) {
        this.date = date;
        this.quantity = quantity;
        this.total_price = total_price;
        this.food_name = food_name;
        this.picture = picture;
        this.status = status;
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public String getDate() {
        return date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getTotal_price() {
        return total_price;
    }

    public String getFood_name() {
        return food_name;
    }

    public String getPicture() {
        return picture;
    }
}

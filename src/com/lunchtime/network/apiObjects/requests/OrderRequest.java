package com.lunchtime.network.apiObjects.requests;

import com.google.gson.annotations.SerializedName;

public class OrderRequest {
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("food_id")
    private int food_id;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("total_price")
    private int total_price;

    public OrderRequest(int user_id, int food_id, int quantity, int total_price) {
        this.user_id = user_id;
        this.food_id = food_id;
        this.quantity = quantity;
        this.total_price = total_price;
    }
}

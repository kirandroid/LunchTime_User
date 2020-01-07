package com.lunchtime.network.apiObjects.requests;

import com.google.gson.annotations.SerializedName;

public class UserOrderRequest {
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("status")
    private String status;

    public UserOrderRequest(int user_id, String status) {
        this.user_id = user_id;
        this.status = status;
    }
}

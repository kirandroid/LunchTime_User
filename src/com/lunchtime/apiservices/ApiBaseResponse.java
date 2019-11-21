package com.lunchtime.apiservices;

import com.google.gson.annotations.SerializedName;

public class ApiBaseResponse<T> {
    @SerializedName("data")
    private T data;
    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private boolean success;

    public ApiBaseResponse(T data, String message, boolean status_code) {
        this.data = data;
        this.message = message;
        this.success = status_code;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}

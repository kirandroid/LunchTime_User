package com.lunchtime.apiservices;

import com.google.gson.annotations.SerializedName;

public class ApiBaseResponse<T> {
    @SerializedName("data")
    private T data;
    @SerializedName("message")
    private String message;
    @SerializedName("status_code")
    private Integer status_code;

    public ApiBaseResponse(T data, String message, Integer status_code) {
        this.data = data;
        this.message = message;
        this.status_code = status_code;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus_code() {
        return status_code;
    }
}

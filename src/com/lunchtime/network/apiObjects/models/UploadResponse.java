package com.lunchtime.network.apiObjects.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadResponse {
    @SerializedName("secure_url")
    private String secure_url;
    @SerializedName("eager")
    private List eager;


    public UploadResponse(String secure_url, List eager) {
        this.secure_url = secure_url;
        this.eager = eager;
    }

    public String getSecure_url() {
        return secure_url;
    }

    public List getEager() {
        return eager;
    }
}

package com.lunchtime.network.apiObjects.requests;

import com.google.gson.annotations.SerializedName;
import retrofit2.http.Part;

import java.io.File;

public class UploadRequest {
    @SerializedName("timestamp")
    private long timestamp;
    @SerializedName("public_id")
    private String public_id;
    @SerializedName("api_key")
    private String api_key;
    @SerializedName("eager")
    private String eager;
    @SerializedName("file")
    private String file;
    @SerializedName("signature")
    private String signature;

    public UploadRequest(long timestamp, String public_id, String api_key, String eager, String file, String signature) {
        this.timestamp = timestamp;
        this.public_id = public_id;
        this.api_key = api_key;
        this.eager = eager;
        this.file = file;
        this.signature = signature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPublic_id() {
        return public_id;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getEager() {
        return eager;
    }

    public String getFile() {
        return file;
    }

    public String getSignature() {
        return signature;
    }
}

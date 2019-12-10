package com.lunchtime.network;

import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.UploadResponse;
import com.lunchtime.network.apiObjects.requests.LoginRequest;
import com.lunchtime.network.apiObjects.requests.UploadRequest;
import com.lunchtime.network.apiObjects.wrappers.UserWrapper;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.io.File;

public interface UploadAPI {
    @POST("upload")
    Call<UploadResponse> upload(
            @Body RequestBody file

    );

}

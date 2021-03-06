/**
 * @author Kiran Pradhan
 * This interface has the retrofit method for Uploading image which has a callback respective to the response of the api, a request if it has and specify the endpoints.
 */
package com.lunchtime.network;

import com.lunchtime.network.apiObjects.models.UploadResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public interface UploadAPI {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.cloudinary.com/v1_1/kirandroid/image/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    UploadAPI apiService = retrofit.create(UploadAPI.class);

    @POST("upload")
    Call<UploadResponse> upload(
            @Body RequestBody file

    );

}

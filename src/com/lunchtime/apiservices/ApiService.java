package com.lunchtime.apiservices;

import com.lunchtime.apiservices.requests.LoginRequest;
import com.lunchtime.apiservices.requests.RegisterRequest;
import com.lunchtime.apiservices.wrappers.UserWrapper;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @POST("api/login")
    Call<ApiBaseResponse<UserWrapper>> login(@Body LoginRequest loginRequest);

    @POST("api/register")
    Call<ApiBaseResponse> register(@Body RegisterRequest registerRequest);
}

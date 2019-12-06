package com.lunchtime.network.apiServices;

import com.lunchtime.network.LunchAPI;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.UploadAPI;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.UploadResponse;
import com.lunchtime.network.apiObjects.requests.*;
import com.lunchtime.network.apiObjects.wrappers.MenuWrapper;
import com.lunchtime.network.apiObjects.wrappers.OrderWrapper;
import com.lunchtime.network.apiObjects.wrappers.UserWrapper;
import okhttp3.MultipartBody;
import retrofit2.http.Part;

import java.io.File;

public class ApiService {
    public void loginApi(NetworkResponseListener<ApiBaseResponse<UserWrapper>> listener, LunchAPI api, LoginRequest loginRequest){
        api.login(loginRequest).enqueue(new NetworkResponse<>(listener));
    }

    public void registerApi(NetworkResponseListener<ApiBaseResponse> listener, LunchAPI api, RegisterRequest registerRequest){
        api.register(registerRequest).enqueue(new NetworkResponse<>(listener));
    }


    public void fetchMenuApi(NetworkResponseListener<ApiBaseResponse<MenuWrapper>> listener, LunchAPI api){
        api.getMenu().enqueue(new NetworkResponse<>(listener));
    }

    public void updateApi(NetworkResponseListener<ApiBaseResponse> listener, LunchAPI api, UpdateProfileRequest updateProfileRequest){
        api.update(updateProfileRequest).enqueue(new NetworkResponse<>(listener));
    }

    public void orderApi(NetworkResponseListener<ApiBaseResponse> listener, LunchAPI api, OrderRequest orderRequest){
        api.order(orderRequest).enqueue(new NetworkResponse<>(listener));
    }

    public void myOrderApi(NetworkResponseListener<ApiBaseResponse<OrderWrapper>> listener, LunchAPI api, int id){
        api.myOrder(id).enqueue(new NetworkResponse<>(listener));
    }

    public void uploadApi(NetworkResponseListener<UploadResponse> listener, UploadAPI api, UploadRequest uploadRequest){
        api.upload(uploadRequest).enqueue(new NetworkResponse<>(listener));
    }
}

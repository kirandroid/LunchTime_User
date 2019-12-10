package com.lunchtime.network;

import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.models.UploadResponse;
import com.lunchtime.network.apiObjects.requests.*;
import com.lunchtime.network.apiObjects.wrappers.MenuWrapper;
import com.lunchtime.network.apiObjects.wrappers.OrderWrapper;
import com.lunchtime.network.apiObjects.wrappers.UserWrapper;
import com.lunchtime.network.apiServices.ApiService;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

import java.io.File;

public class NetworkManager {
    private static final NetworkManager instance = new NetworkManager();
    private final LunchAPI api;
    private final String BASE_URL = "http://localhost:3000/api/";
    private final ApiService apiService;

    private NetworkManager() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        api = retrofit.create(LunchAPI.class);

        apiService = new ApiService();
    }

    public static NetworkManager getInstance(){
        return instance;
    }

    //Api Call methods
    public void Login( LoginRequest loginRequest, NetworkResponseListener<ApiBaseResponse<UserWrapper>> listener){
        apiService.loginApi(listener, api, loginRequest);
    }

    public void Register(RegisterRequest registerRequest, NetworkResponseListener<ApiBaseResponse> listener){
        apiService.registerApi(listener, api, registerRequest);
    }

    public void GetMenu(NetworkResponseListener<ApiBaseResponse<MenuWrapper>> listener){
        apiService.fetchMenuApi(listener, api);
    }

    public void Update(UpdateProfileRequest updateProfileRequest, NetworkResponseListener<ApiBaseResponse> listener){
        apiService.updateApi(listener, api, updateProfileRequest);
    }

    public void Order(OrderRequest orderRequest, NetworkResponseListener<ApiBaseResponse> listener){
        apiService.orderApi(listener, api, orderRequest);
    }

    public void MyOrder(int id, NetworkResponseListener<ApiBaseResponse<OrderWrapper>> listener){
        apiService.myOrderApi(listener, api, id);
    }



}



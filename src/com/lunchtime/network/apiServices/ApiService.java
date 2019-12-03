package com.lunchtime.network.apiServices;

import com.lunchtime.network.LunchAPI;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.requests.LoginRequest;
import com.lunchtime.network.apiObjects.requests.OrderRequest;
import com.lunchtime.network.apiObjects.requests.RegisterRequest;
import com.lunchtime.network.apiObjects.requests.UpdateProfileRequest;
import com.lunchtime.network.apiObjects.wrappers.MenuWrapper;
import com.lunchtime.network.apiObjects.wrappers.UserWrapper;

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
}

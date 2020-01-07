package com.lunchtime.network.apiServices;

import com.lunchtime.network.LunchAPI;
import com.lunchtime.network.NetworkResponseListener;
import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.requests.*;
import com.lunchtime.network.apiObjects.wrappers.MenuWrapper;
import com.lunchtime.network.apiObjects.wrappers.OrderWrapper;
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

    public void myOrderApi(NetworkResponseListener<ApiBaseResponse<OrderWrapper>> listener, LunchAPI api, UserOrderRequest userOrderRequest){
        api.myOrder(userOrderRequest).enqueue(new NetworkResponse<>(listener));
    }


    public void expenseOrderApi(NetworkResponseListener<ApiBaseResponse<OrderWrapper>> listener, LunchAPI api, int id){
        api.expenseOrder(id).enqueue(new NetworkResponse<>(listener));
    }


    public void userDetailApi(NetworkResponseListener<ApiBaseResponse<UserWrapper>> listener, LunchAPI api, int id){
        api.userDetail(id).enqueue(new NetworkResponse<>(listener));
    }

    public void cancelOrderApi(NetworkResponseListener<ApiBaseResponse> listener, LunchAPI api, int id){
        api.cancelOrder(id).enqueue(new NetworkResponse<>(listener));
    }
}

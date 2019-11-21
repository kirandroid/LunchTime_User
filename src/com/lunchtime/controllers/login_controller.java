package com.lunchtime.controllers;

import com.lunchtime.apiservices.ApiBaseResponse;
import com.lunchtime.apiservices.Network;
import com.lunchtime.apiservices.requests.LoginRequest;
import com.lunchtime.apiservices.wrappers.UserWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_controller {


    public void initialize(){
        LoginRequest loginRequest = new LoginRequest("pradhanangakiran@gmail.com", "kiran123");
        Call<ApiBaseResponse<UserWrapper>> call = Network.apiService.login(loginRequest);
        call.enqueue(new Callback<ApiBaseResponse<UserWrapper>>() {
            @Override
            public void onResponse(Call<ApiBaseResponse<UserWrapper>> call, Response<ApiBaseResponse<UserWrapper>> response) {

                ApiBaseResponse<UserWrapper> user = response.body();
                System.out.println(user.getMessage());
                System.out.println(user.getStatus_code());
                System.out.println(user.getData().getUser().getFirst_name());
            }

            @Override
            public void onFailure(Call<ApiBaseResponse<UserWrapper>> call, Throwable throwable) {
                System.out.println(throwable);
            }
        });

    }
}

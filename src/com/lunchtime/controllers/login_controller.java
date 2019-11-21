package com.lunchtime.controllers;

import com.lunchtime.apiservices.ApiBaseResponse;
import com.lunchtime.apiservices.ApiService;
import com.lunchtime.apiservices.models.UserWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_controller {

    public void initialize(){
        Call<ApiBaseResponse<UserWrapper>> call = ApiService.apiService.getUser();
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

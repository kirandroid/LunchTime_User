/**
 * @author Kiran Pradhan
 * This interface has all the retrofit method which has a callback respective to the response of the api, a request if it has and specify the endpoints.
 * */

package com.lunchtime.network;

import com.lunchtime.network.apiObjects.ApiBaseResponse;
import com.lunchtime.network.apiObjects.requests.*;
import com.lunchtime.network.apiObjects.wrappers.MenuWrapper;
import com.lunchtime.network.apiObjects.wrappers.OrderWrapper;
import com.lunchtime.network.apiObjects.wrappers.UserWrapper;
import retrofit2.Call;
import retrofit2.http.*;

public interface LunchAPI {

    @POST("login")
    Call<ApiBaseResponse<UserWrapper>> login(@Body LoginRequest loginRequest);

    @POST("register")
    Call<ApiBaseResponse> register(@Body RegisterRequest registerRequest);

    @GET("menu")
    Call<ApiBaseResponse<MenuWrapper>> getMenu();

    @POST("update")
    Call<ApiBaseResponse> update(@Body UpdateProfileRequest updateProfileRequest);

    @POST("order")
    Call<ApiBaseResponse> order(@Body OrderRequest orderRequest);

    @POST("userorder")
    Call<ApiBaseResponse<OrderWrapper>> myOrder(@Body UserOrderRequest userOrderRequest);

    @GET("expenseorder/{id}")
    Call<ApiBaseResponse<OrderWrapper>> expenseOrder(@Path("id") int order);

    @GET("user/{id}")
    Call<ApiBaseResponse<UserWrapper>> userDetail(@Path("id") int id);

    @GET("cancelorder/{id}")
    Call<ApiBaseResponse> cancelOrder(@Path("id") int id);
}

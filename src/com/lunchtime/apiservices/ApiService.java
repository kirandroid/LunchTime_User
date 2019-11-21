package com.lunchtime.apiservices;
import com.lunchtime.apiservices.models.UserWrapper;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    @GET("user")
    Call<ApiBaseResponse<UserWrapper>> getUser();
}

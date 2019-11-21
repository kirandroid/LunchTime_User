package com.lunchtime.apiservices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface Network {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);
}

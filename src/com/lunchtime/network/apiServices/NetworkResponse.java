package com.lunchtime.network.apiServices;

import com.lunchtime.network.NetworkResponseListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.ref.WeakReference;

public class NetworkResponse<ResponseType> implements Callback<ResponseType> {
    private WeakReference<NetworkResponseListener<ResponseType>> listener;

    NetworkResponse(NetworkResponseListener<ResponseType> listener){
        this.listener = new WeakReference<>(listener);
    }
    @Override
    public void onResponse(Call<ResponseType> call, Response<ResponseType> response) {
        if (listener.get() != null && listener != null) {
            if (response.code() == 400){
                listener.get().onError();
            }else{
                listener.get().onResponseReceived(response.body());
            }
            System.out.println(response);

        }
    }

    @Override
    public void onFailure(Call<ResponseType> call, Throwable throwable) {
        if (listener.get() != null && listener != null) {
            listener.get().onError();
        }
        System.out.println("Error : From NetworkResponse");
        throwable.printStackTrace();
        System.out.println(call);
        System.out.println(throwable);
    }
}

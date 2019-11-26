package com.lunchtime.network;

public interface NetworkResponseListener<Response> {
    void onResponseReceived(Response response);
    void onError();
}

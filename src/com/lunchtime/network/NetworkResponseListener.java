/**
 * @author Kiran Pradhan
 * This interface has two method which listens to the response from the API and returns the method with response or error.
 * */
package com.lunchtime.network;

public interface NetworkResponseListener<Response> {
    void onResponseReceived(Response response);
    void onError();
}

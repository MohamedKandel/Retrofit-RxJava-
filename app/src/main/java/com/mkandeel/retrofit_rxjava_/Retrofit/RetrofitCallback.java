package com.mkandeel.retrofit_rxjava_.Retrofit;

public interface RetrofitCallback<T> {
    void onSuccess(T post);
    void onFailure(String error);
}

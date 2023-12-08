package com.mkandeel.retrofit_rxjava_.Repository;

import android.app.Application;

import com.mkandeel.retrofit_rxjava_.Pojo.PostsModel;
import com.mkandeel.retrofit_rxjava_.Retrofit.APIService;
import com.mkandeel.retrofit_rxjava_.Retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostRepo {
    private Application application;
    private APIService apiService;
    public PostRepo(Application application) {
        this.application = application;
        this.apiService = RetrofitClient.getInstance()
                .create(APIService.class);
    }

    // get posts with observable
    public Observable<List<PostsModel>> getAllPosts() {
        return apiService.getAllPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    /* get posts with callback
    public void getAllPosts(RetrofitCallback<List<PostsModel>> callback) {
        apiService.getAllPosts().enqueue(new Callback<List<PostsModel>>() {
            @Override
            public void onResponse(Call<List<PostsModel>> call, Response<List<PostsModel>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(ERRORCODE+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PostsModel>> call, Throwable t) {
                callback.onFailure(ERRORMESSAGE+t.getMessage());
            }
        });

    }*/
}

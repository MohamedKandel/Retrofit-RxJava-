package com.mkandeel.retrofit_rxjava_.Repository;

import android.app.Application;

import com.mkandeel.retrofit_rxjava_.Pojo.CommentsModel;
import com.mkandeel.retrofit_rxjava_.Retrofit.APIService;
import com.mkandeel.retrofit_rxjava_.Retrofit.RetrofitClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CommentRepo {
    private Application application;
    private APIService apiService;
    public CommentRepo(Application application) {
        this.application = application;
        this.apiService = RetrofitClient.getInstance()
                .create(APIService.class);
    }

    public Observable<List<CommentsModel>> getCommentsForPost(int postId) {
        return apiService.getCommentForPost(postId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .debounce(2, TimeUnit.SECONDS)
                .distinct();
    }
}

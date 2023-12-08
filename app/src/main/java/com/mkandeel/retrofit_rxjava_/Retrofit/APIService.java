package com.mkandeel.retrofit_rxjava_.Retrofit;

import com.mkandeel.retrofit_rxjava_.Pojo.CommentsModel;
import com.mkandeel.retrofit_rxjava_.Pojo.PostsModel;
import com.mkandeel.retrofit_rxjava_.Pojo.UsersModel;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {
    @GET("users/{id}")
    Observable<UsersModel> getUserData(@Path("id") int id);
    @GET("posts")
    Observable<List<PostsModel>> getAllPosts();
    @GET("posts/{id}/comments")
    Observable<List<CommentsModel>> getCommentForPost(@Path("id") int id);
}

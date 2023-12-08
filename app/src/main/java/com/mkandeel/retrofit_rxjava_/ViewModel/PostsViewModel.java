package com.mkandeel.retrofit_rxjava_.ViewModel;

import static com.mkandeel.retrofit_rxjava_.Helper.Constants.APITAG;
import static com.mkandeel.retrofit_rxjava_.Helper.Constants.ERRORMESSAGE;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mkandeel.retrofit_rxjava_.Pojo.PostsModel;
import com.mkandeel.retrofit_rxjava_.Repository.PostRepo;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class PostsViewModel extends AndroidViewModel {
    private PostRepo postRepo;
    //private APIService apiService;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<PostsModel>> allPosts = new MutableLiveData<>();
    private MutableLiveData<Object> deletePost = new MutableLiveData<>();

    public PostsViewModel(@NonNull Application application) {
        super(application);
        this.postRepo = new PostRepo(application);
        /*this.apiService = RetrofitClient.getInstance()
                .create(APIService.class);*/
    }

    /* get all posts woth callback
    public void getAllPosts() {
        postRepo.getAllPosts(new RetrofitCallback<List<PostsModel>>() {
            @Override
            public void onSuccess(List<PostsModel> post) {
                allPosts.postValue(post);
            }

            @Override
            public void onFailure(String error) {
                Log.e(APITAG, ERRORMESSAGE+error );
            }
        });

    }*/
    // get all posts with observer
    public void getAllPosts() {
        /*Observer<List<PostsModel>> observer = new Observer<List<PostsModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<PostsModel> postsModels) {
                allPosts.postValue(postsModels);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(ERRORMESSAGE, "onError Posts: " + e.getMessage(), e);
            }

            @Override
            public void onComplete() {

            }
        };*/
        compositeDisposable.add(postRepo.getAllPosts()
                .subscribe(postsModels -> allPosts.postValue(postsModels)
                , throwable -> Log.e(APITAG, ERRORMESSAGE, throwable)));
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public LiveData<List<PostsModel>> getAllPostsData() {
        return allPosts;
    }
    public LiveData<Object> getDeletedPost() {
        return deletePost;
    }
}

package com.mkandeel.retrofit_rxjava_.ViewModel;

import static com.mkandeel.retrofit_rxjava_.Helper.Constants.APITAG;
import static com.mkandeel.retrofit_rxjava_.Helper.Constants.ERRORMESSAGE;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mkandeel.retrofit_rxjava_.Pojo.CommentsModel;
import com.mkandeel.retrofit_rxjava_.Repository.CommentRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class CommentsViewModel extends AndroidViewModel {
    private CommentRepo commentRepo;
    private MutableLiveData<List<CommentsModel>> comments = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public CommentsViewModel(@NonNull Application application) {
        super(application);
        this.commentRepo = new CommentRepo(application);
    }

    public void getComments(int postId) {
        compositeDisposable.add(commentRepo.getCommentsForPost(postId)
                .subscribe(commentsModels -> comments.postValue(commentsModels),
                        throwable -> Log.e(APITAG, ERRORMESSAGE,throwable )));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public LiveData<List<CommentsModel>> getComments() {
        return comments;
    }
}

package com.mkandeel.retrofit_rxjava_.ViewModel;

import static com.mkandeel.retrofit_rxjava_.Helper.Constants.APITAG;
import static com.mkandeel.retrofit_rxjava_.Helper.Constants.ERRORMESSAGE;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mkandeel.retrofit_rxjava_.Pojo.UsersModel;
import com.mkandeel.retrofit_rxjava_.Repository.UserRepo;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class UserViewModel extends AndroidViewModel {
    private UserRepo userRepo;
    //private APIService apiService;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<UsersModel> usersData = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.userRepo = new UserRepo(application);
        /*this.apiService = RetrofitClient.getInstance()
                .create(APIService.class);*/
    }

    public void getDataUserByID(int id) {
        /*userRepo.getUserById(id, new RetrofitCallback<UsersModel>() {
            @Override
            public void onSuccess(UsersModel post) {
                usersData.postValue(post);
            }

            @Override
            public void onFailure(String error) {
                Log.e(APITAG, ERRORMESSAGE+error);
            }
        });*/
        /*Observer<UsersModel> observer = new Observer<UsersModel>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(UsersModel usersModel) {
                usersData.postValue(usersModel);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(ERRORMESSAGE, "onError Users: " + e.getMessage(), e);
            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);*/
        compositeDisposable.add(userRepo.getUsernameById(id)
                .subscribe(usersModel -> usersData.postValue(usersModel),
                throwable -> Log.e(APITAG, ERRORMESSAGE,throwable )));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public LiveData<UsersModel> getUsersData() {
        return usersData;
    }
}

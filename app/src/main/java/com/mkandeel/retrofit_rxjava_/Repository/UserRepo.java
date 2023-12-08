package com.mkandeel.retrofit_rxjava_.Repository;

import android.app.Application;

import com.mkandeel.retrofit_rxjava_.Pojo.UsersModel;
import com.mkandeel.retrofit_rxjava_.Retrofit.APIService;
import com.mkandeel.retrofit_rxjava_.Retrofit.RetrofitCallback;
import com.mkandeel.retrofit_rxjava_.Retrofit.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserRepo {
    private Application application;
    private APIService apiService;
    public UserRepo(Application application) {
        this.application = application;
        this.apiService = RetrofitClient.getInstance()
                .create(APIService.class);
    }

    // get user by id with observable
    public Observable<UsersModel> getUsernameById(int id) {
        return apiService.getUserData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    /* get user by id with callback
    public void getUserById(int id, RetrofitCallback<UsersModel> callBack){
        apiService.getUserData(id)
                .enqueue(new Callback<UsersModel>() {
                    @Override
                    public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                        if (response.isSuccessful()) {
                            callBack.onSuccess(response.body());
                        } else {
                            callBack.onFailure(ERRORCODE+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<UsersModel> call, Throwable t) {
                        callBack.onFailure(ERRORMESSAGE+t.getMessage());
                    }
                });
    }*/
}

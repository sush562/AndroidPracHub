package com.myprac.advanced.android.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myprac.advanced.android.database.RetroDatabase;
import com.myprac.advanced.android.interfaces.GetRetroPhoto;
import com.myprac.advanced.android.model.RetroPhoto;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroRepository {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static RetroRepository mInstance;
    private Retrofit retrofit;
    private RetroDatabase retroDatabase;
    private final CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    public RetroRepository() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retroDatabase = RetroDatabase.getInstance();
    }

    public static RetroRepository getInstance() {
        if (mInstance == null) {
            mInstance = new RetroRepository();
        }
        return mInstance;
    }

    public void getPhotoList(final MutableLiveData<List<RetroPhoto>> list) {
        Disposable observer = Observable.create(new ObservableOnSubscribe<List<RetroPhoto>>() {
            @Override
            public void subscribe(ObservableEmitter<List<RetroPhoto>> emitter) throws Exception {
                emitter.onNext(getFromDatabase());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<RetroPhoto>>() {
                    @Override
                    public void onNext(List<RetroPhoto> retroPhotos) {
                        if (retroPhotos.isEmpty()) {
                            Log.e("API", "Called");
                            final GetRetroPhoto gerritAPI = retrofit.create(GetRetroPhoto.class);
                            gerritAPI.getAllPhotos().enqueue(new Callback<List<RetroPhoto>>() {
                                @Override
                                public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                                    Log.e("List Size", "" + response.body().size());
                                    //list.setValue(response.body());
                                    insertInDatabase(response.body());
                                    list.setValue(response.body());
                                }

                                @Override
                                public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                                    Log.e("Step", "5");
                                }
                            });
                        } else {
                            Log.e("Step", "Fetched from db");
                            list.setValue(retroPhotos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mCompositeSubscription.add(observer);
    }

    @SuppressLint("CheckResult")
    private void insertInDatabase(final List<RetroPhoto> list) {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.e("DB", "Insert");
                retroDatabase.retroPhotoDao().insertAll(list);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }


    private List<RetroPhoto> getFromDatabase() {
        return retroDatabase.retroPhotoDao().getAllRetroPhotoList();
    }

    public void onDestroy() {
        mCompositeSubscription.dispose();
    }
}

package com.myprac.advanced.android.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myprac.advanced.android.database.RetroDatabase;
import com.myprac.advanced.android.interfaces.GetRetroPhoto;
import com.myprac.advanced.android.model.RetroPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RetroRepository {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static RetroRepository mInstance;
    private Retrofit retrofit;
    private RetroDatabase retroDatabase;
    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

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
        mCompositeSubscription.add(Observable.create(new Observable.OnSubscribe<List<RetroPhoto>>() {
            @Override
            public void call(Subscriber<? super List<RetroPhoto>> subscriber) {
                subscriber.onNext(getFromDatabase());
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<RetroPhoto>>() {
                    @Override
                    public void onCompleted() {
                        //
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<RetroPhoto> count) {
                        if (count.isEmpty()) {
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
                            list.setValue(count);
                        }
                    }
                }));
    }

    private void insertInDatabase(final List<RetroPhoto> list) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.e("DB", "Insert");
                retroDatabase.retroPhotoDao().insertAll(list);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    private List<RetroPhoto> getFromDatabase() {
        return retroDatabase.retroPhotoDao().getAllRetroPhotoList();
    }

    public void onDestroy() {

    }
}

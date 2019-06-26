package com.myprac.advanced.android.interfaces;

import com.myprac.advanced.android.model.RetroPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRetroPhoto {

    @GET("/photos")
    Call<List<RetroPhoto>> getAllPhotos();


}

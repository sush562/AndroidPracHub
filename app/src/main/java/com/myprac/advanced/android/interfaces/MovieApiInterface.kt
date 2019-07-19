package com.myprac.advanced.android.interfaces

import com.myprac.advanced.android.model.MovieConfig
import com.myprac.advanced.android.model.MovieList
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiInterface {

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("api_key") apiKey: String, @Query("page") page: Int): Call<MovieList>

    @GET("movie/now_playing")
    fun getNowPlayingRx(@Query("api_key") apiKey: String, @Query("page") page: Int): Observable<MovieList>

    @GET("movie/top_rated")
    fun getTopRatedRx(@Query("api_key") apiKey: String, @Query("page") page: Int): Observable<MovieList>

    @GET("configuration")
    fun getMovieConfig(@Query("api_key") apiKey: String): Observable<MovieConfig>

    companion object Factory{
        val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): MovieApiInterface{
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(MovieApiInterface::class.java)
        }
    }
}
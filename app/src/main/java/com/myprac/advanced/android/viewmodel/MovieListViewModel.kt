package com.myprac.advanced.android.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.myprac.advanced.android.R
import com.myprac.advanced.android.interfaces.MovieApiInterface
import com.myprac.advanced.android.model.MovieList
import com.myprac.advanced.android.model.MovieResult
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListViewModel(val app: Application) : AndroidViewModel(app) {

    private val movieList: MutableLiveData<List<MovieResult>> = MutableLiveData()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun clearMovieList() {
        movieList.value = ArrayList()
    }

    fun getMovieList(page: Int) {
        val movieApi = MovieApiInterface.create()
        val call: Call<MovieList> = movieApi.getNowPlaying(app.getString(R.string.api_key), page)

        /*Log.e("url", call.request().url().toString())
        call.enqueue(object: Callback<MovieList> {
            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                
            }

            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                Log.e("step", "2 - " + response.isSuccessful);
                Log.e("step", "3 - " + response.body()?.results?.size);
            }

        })*/

        compositeDisposable.add(Observable.create(ObservableOnSubscribe<MovieList> { emitter ->
            try {
                Log.e("step", "2");
                emitter.onNext(call.execute().body()!!)
                emitter.onComplete()
            } catch (error: Exception) {
                emitter.onError(error)
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<MovieList>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: MovieList) {
                        Log.e("step", "3 - " + t.page);

                    }

                    override fun onError(e: Throwable) {
                        Log.e("step", "4" + e.message);
                    }

                }))
    }

    fun cancelMovieApiCall() {
        compositeDisposable.dispose()
    }
}
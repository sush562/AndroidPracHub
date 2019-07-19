package com.myprac.advanced.android.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.myprac.advanced.android.R
import com.myprac.advanced.android.Utils
import com.myprac.advanced.android.interfaces.MovieApiInterface
import com.myprac.advanced.android.model.MovieConfig
import com.myprac.advanced.android.model.MovieList
import com.myprac.advanced.android.model.MovieResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MovieListViewModel(val app: Application) : AndroidViewModel(app) {

    private val mMovieList: MutableLiveData<List<MovieResult>> = MutableLiveData()
    private var mPageCount: Int = 1
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private var posterBaseUrl: String = ""

    fun clearMovieList() {
        mMovieList.value = ArrayList()
        mPageCount = 1
    }

    fun getMovieList(): MutableLiveData<List<MovieResult>> {
        if (Utils().isNetworkAvailable()) {
            val movieApi = MovieApiInterface.create()
            if (TextUtils.isEmpty(posterBaseUrl)) {
                getConfig(movieApi)
            } else {
                val observer: Observable<MovieList> =
                        movieApi.getNowPlayingRx(app.getString(R.string.api_key), mPageCount)
                mCompositeDisposable.add(observer.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { movieList ->
                            for(i in 1..movieList.results.size){
                                movieList.results[i - 1].posterBaseUrl = posterBaseUrl
                            }
                            movieList
                        }
                        .subscribeWith(object : DisposableObserver<MovieList>() {
                            override fun onComplete() {

                            }

                            override fun onNext(movieList: MovieList) {
                                Log.e("step", "Rx3 - " + movieList.results.size)
                                mMovieList.value = movieList.results
                                mPageCount++
                            }

                            override fun onError(e: Throwable) {
                                Log.e(MovieListViewModel::class.java.simpleName, e.message, e)
                            }

                        }))
            }

        } else {
            Log.e(MovieListViewModel::class.java.simpleName, "Internet Not connected");
        }

        //val call: Call<MovieList> = movieApi.getNowPlaying(app.getString(R.string.api_key), page)

        /*Log.e("url", call.request().url().toString())
    call.enqueue(object: Callback<MovieList> {
        override fun onFailure(call: Call<MovieList>, t: Throwable) {

        }

        override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
            Log.e("step", "2 - " + response.isSuccessful);
            Log.e("step", "3 - " + response.body()?.results?.size);
        }

    })*/

        /*mCompositeDisposable.add(Observable.create(ObservableOnSubscribe<MovieList> { emitter ->
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

            }))*/

        return mMovieList
    }

    fun getTopRatedList(): MutableLiveData<List<MovieResult>> {
        if (Utils().isNetworkAvailable()) {
            val movieApi = MovieApiInterface.create()
            if (TextUtils.isEmpty(posterBaseUrl)) {
                getConfig(movieApi)
            } else {
                val observer: Observable<MovieList> =
                        movieApi.getTopRatedRx(app.getString(R.string.api_key), mPageCount)
                mCompositeDisposable.add(observer.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { movieList ->
                            for(i in 1..movieList.results.size){
                                movieList.results[i - 1].posterBaseUrl = posterBaseUrl
                            }
                            movieList
                        }
                        .subscribeWith(object : DisposableObserver<MovieList>() {
                            override fun onComplete() {

                            }

                            override fun onNext(movieList: MovieList) {
                                Log.e("step", "Rx3 - " + movieList.results.size)
                                mMovieList.value = movieList.results
                                mPageCount++
                            }

                            override fun onError(e: Throwable) {
                                Log.e(MovieListViewModel::class.java.simpleName, e.message, e)
                            }
                        }))
            }
        } else {
            Log.e(MovieListViewModel::class.java.simpleName, "Internet Not connected");
        }
        return mMovieList
    }

    fun getUpcomingList(): MutableLiveData<List<MovieResult>> {
        if (Utils().isNetworkAvailable()) {
            val movieApi = MovieApiInterface.create()
            if (TextUtils.isEmpty(posterBaseUrl)) {
                getConfig(movieApi)
            } else {
                val observer: Observable<MovieList> =
                        movieApi.getUpcomingRx(app.getString(R.string.api_key), mPageCount)
                mCompositeDisposable.add(observer.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { movieList ->
                            for(i in 1..movieList.results.size){
                                movieList.results[i - 1].posterBaseUrl = posterBaseUrl
                            }
                            movieList
                        }
                        .subscribeWith(object : DisposableObserver<MovieList>() {
                            override fun onComplete() {

                            }

                            override fun onNext(movieList: MovieList) {
                                Log.e("step", "Rx3 - " + movieList.results.size)
                                mMovieList.value = movieList.results
                                mPageCount++
                            }

                            override fun onError(e: Throwable) {
                                Log.e(MovieListViewModel::class.java.simpleName, e.message, e)
                            }
                        }))
            }
        } else {
            Log.e(MovieListViewModel::class.java.simpleName, "Internet Not connected");
        }
        return mMovieList
    }

    private fun getConfig(movieApi: MovieApiInterface){
        val configObs: Observable<MovieConfig> = movieApi.getMovieConfig(app.getString(R.string.api_key))
        mCompositeDisposable.add(configObs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<MovieConfig>() {
                    override fun onComplete() {

                    }

                    override fun onNext(movieConfig: MovieConfig) {
                        posterBaseUrl = movieConfig.imageConfig.secureBaseUrl
                        Log.e("Poster base url", posterBaseUrl)
                        getMovieList()
                    }

                    override fun onError(e: Throwable) {
                        Log.e(MovieListViewModel::class.java.simpleName, e.message, e)
                    }

                }))
    }

    fun cancelMovieApiCall() {
        mCompositeDisposable.dispose()

    }
}
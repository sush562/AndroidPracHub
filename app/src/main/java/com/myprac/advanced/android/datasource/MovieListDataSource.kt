package com.myprac.advanced.android.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.myprac.advanced.android.R
import com.myprac.advanced.android.RetroApp
import com.myprac.advanced.android.enum.MovieType
import com.myprac.advanced.android.interfaces.MovieApiInterface
import com.myprac.advanced.android.model.MovieList
import com.myprac.advanced.android.model.MovieResult
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class MovieListDataSource(private val movieApi: MovieApiInterface,
                          private val compositeDisposable: CompositeDisposable,
                          private val movieType: MovieType,
                          private val posterBaseUrl: String) :
        PageKeyedDataSource<Int, MovieResult>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieResult>) {
        Log.e(MovieListDataSource::class.java.simpleName, "First Load - " + posterBaseUrl)
        compositeDisposable.add(getMovieObserver(1)
                .map { movieList ->
                    for (i in 1..movieList.results.size) {
                        movieList.results[i - 1].posterBaseUrl = posterBaseUrl
                    }
                    movieList
                }
                .subscribeWith(object : DisposableObserver<MovieList>() {
                    override fun onNext(t: MovieList) {
                        val pageDiff = t.totalPages - t.page
                        if (pageDiff > 0) {
                            callback.onResult(t.results, null, t.page + 1)
                        } else {
                            callback.onResult(t.results, null, null)
                        }
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        Log.e(MovieListDataSource::class.java.simpleName, e.message, e)
                    }

                }))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
        Log.e(MovieListDataSource::class.java.simpleName, "" + params.key)
        compositeDisposable.add(getMovieObserver(params.key)
                .map { movieList ->
                    for (i in 1..movieList.results.size) {
                        movieList.results[i - 1].posterBaseUrl = posterBaseUrl
                    }
                    movieList
                }
                .subscribeWith(object : DisposableObserver<MovieList>() {
                    override fun onNext(t: MovieList) {
                        val pageDiff = t.totalPages - t.page
                        if (pageDiff > 0) {
                            callback.onResult(t.results, t.page + 1)
                        } else {
                            callback.onResult(t.results, null)
                        }
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        Log.e(MovieListDataSource::class.java.simpleName, e.message, e)
                    }

                }))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {

    }

    private fun getMovieObserver(mPageCount: Int): Observable<MovieList> {
        return when (movieType) {
            MovieType.NOW_PLAYING -> movieApi.getNowPlayingRx(RetroApp.instance.getString(R.string.api_key), mPageCount)
            MovieType.POPULAR -> movieApi.getPopularRx(RetroApp.instance.getString(R.string.api_key), mPageCount)
            MovieType.TOP_RATED -> movieApi.getTopRatedRx(RetroApp.instance.getString(R.string.api_key), mPageCount)
            MovieType.UPCOMING -> movieApi.getUpcomingRx(RetroApp.instance.getString(R.string.api_key), mPageCount)
        }
    }
}
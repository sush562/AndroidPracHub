package com.myprac.advanced.android.datasource.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.myprac.advanced.android.datasource.MovieListDataSource
import com.myprac.advanced.android.enum.MovieType
import com.myprac.advanced.android.interfaces.MovieApiInterface
import com.myprac.advanced.android.model.MovieResult
import io.reactivex.disposables.CompositeDisposable

class MovieListDataSourceFactory(private val movieApi: MovieApiInterface,
                                 private val compositeDisposable: CompositeDisposable,
                                 private val movieType: MovieType,
                                 private val posterBaseUrl: String): DataSource.Factory<Int, MovieResult>() {

    val movieDataSource = MutableLiveData<MovieListDataSource>()
    override fun create(): DataSource<Int, MovieResult> {
        val movieListDataSource = MovieListDataSource(movieApi, compositeDisposable, movieType, posterBaseUrl)
        movieDataSource.postValue(movieListDataSource)
        return movieListDataSource
    }
}
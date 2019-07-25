package com.myprac.advanced.android.datasource.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.myprac.advanced.android.enumclass.movie.MovieType
import com.myprac.advanced.android.interfaces.movie.MovieApiInterface
import com.myprac.advanced.android.model.MovieResult
import io.reactivex.disposables.CompositeDisposable

class MovieListDataSourceFactory(private val movieApi: MovieApiInterface,
                                 private val compositeDisposable: CompositeDisposable,
                                 private val movieType: MovieType): DataSource.Factory<Int, MovieResult>() {

    val movieDataSource = MutableLiveData<MovieListDataSource>()
    override fun create(): DataSource<Int, MovieResult> {
        val movieListDataSource = MovieListDataSource(movieApi, compositeDisposable, movieType)
        movieDataSource.postValue(movieListDataSource)
        return movieListDataSource
    }
}
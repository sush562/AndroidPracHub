package com.myprac.advanced.android.viewmodel.movie

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.myprac.advanced.android.datasource.movie.MovieListDataSourceFactory
import com.myprac.advanced.android.model.MovieResult

class MovieListPagingViewModel(val app1: Application) : MovieListViewModel(app1) {

    private val pageSize = 20
    private lateinit var movieListDataSourceFactory: MovieListDataSourceFactory
    private lateinit var mPagedMovieList: LiveData<PagedList<MovieResult>>


    fun getPagedMovieList(): LiveData<PagedList<MovieResult>>{
        return mPagedMovieList
    }

    fun initializeDataSource(){
        movieListDataSourceFactory = MovieListDataSourceFactory(movieApi, mCompositeDisposable, movieType)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize * 2)
                .build()
        mPagedMovieList = LivePagedListBuilder<Int, MovieResult>(movieListDataSourceFactory, config).build()
    }
}
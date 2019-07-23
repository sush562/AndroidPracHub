package com.myprac.advanced.android.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.myprac.advanced.android.datasource.factory.MovieListDataSourceFactory
import com.myprac.advanced.android.model.MovieResult

class MovieListPagingViewModel(val app1: Application) : MovieListViewModel(app1) {

    private val pageSize = 20
    private lateinit var movieListDataSourceFactory: MovieListDataSourceFactory
    private lateinit var mPagedMovieList: LiveData<PagedList<MovieResult>>


    fun getPagedMovieList(): LiveData<PagedList<MovieResult>>{
        return mPagedMovieList
    }

    fun getIsConfigFetching(): MutableLiveData<Boolean>{
        return isConfigFetching
    }

    fun getConfig(){
        getConfig(movieApi, false)
    }

    fun initializeDataSource(){
        Log.e("Got",  "test - " + posterBaseUrl)
        movieListDataSourceFactory = MovieListDataSourceFactory(movieApi, mCompositeDisposable, movieType, posterBaseUrl)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize * 2)
                .build()
        mPagedMovieList = LivePagedListBuilder<Int, MovieResult>(movieListDataSourceFactory, config).build()
    }

    fun clear(){
        movieListDataSourceFactory.movieDataSource.value?.invalidate()
    }
}
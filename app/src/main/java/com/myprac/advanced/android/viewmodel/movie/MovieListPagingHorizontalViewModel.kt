package com.myprac.advanced.android.viewmodel.movie

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.myprac.advanced.android.datasource.movie.MovieListDataSourceFactory
import com.myprac.advanced.android.enumclass.movie.MovieType
import com.myprac.advanced.android.model.MovieResult

class MovieListPagingHorizontalViewModel(val app1: Application) : MovieListViewModel(app1) {

    private val pageSize = 20
    private lateinit var mNowPlayingMovieList: LiveData<PagedList<MovieResult>>
    private lateinit var mPopularMovieList: LiveData<PagedList<MovieResult>>
    private lateinit var mTopRatedMovieList: LiveData<PagedList<MovieResult>>
    private lateinit var mUpcomingMovieList: LiveData<PagedList<MovieResult>>

    fun getNowPlayingMovieList(): LiveData<PagedList<MovieResult>>{
        return mNowPlayingMovieList
    }

    fun getPopularMovieList(): LiveData<PagedList<MovieResult>>{
        return mPopularMovieList
    }

    fun getTopRatedMovieList(): LiveData<PagedList<MovieResult>>{
        return mTopRatedMovieList
    }

    fun getUpcomingMovieList(): LiveData<PagedList<MovieResult>>{
        return mUpcomingMovieList
    }

    fun getIsConfigFetching(): MutableLiveData<Boolean>{
        return isConfigFetching
    }

    fun getConfig(){
        getConfig(movieApi, false)
    }

    fun initializeDataSource(){
        Log.e("Got",  "test - " + posterBaseUrl)
        val factory1: MovieListDataSourceFactory = MovieListDataSourceFactory(movieApi, mCompositeDisposable, MovieType.NOW_PLAYING, posterBaseUrl)
        val factory2: MovieListDataSourceFactory = MovieListDataSourceFactory(movieApi, mCompositeDisposable, MovieType.POPULAR, posterBaseUrl)
        val factory3: MovieListDataSourceFactory = MovieListDataSourceFactory(movieApi, mCompositeDisposable, MovieType.TOP_RATED, posterBaseUrl)
        val factory4: MovieListDataSourceFactory = MovieListDataSourceFactory(movieApi, mCompositeDisposable, MovieType.UPCOMING, posterBaseUrl)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize * 2)
                .build()
        mNowPlayingMovieList = LivePagedListBuilder<Int, MovieResult>(factory1, config).build()
        mPopularMovieList = LivePagedListBuilder<Int, MovieResult>(factory2, config).build()
        mTopRatedMovieList = LivePagedListBuilder<Int, MovieResult>(factory3, config).build()
        mUpcomingMovieList = LivePagedListBuilder<Int, MovieResult>(factory4, config).build()
    }
}
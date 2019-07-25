package com.myprac.advanced.android.viewmodel.movie

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.myprac.advanced.android.R
import com.myprac.advanced.android.Utils
import com.myprac.advanced.android.interfaces.movie.MovieApiInterface
import com.myprac.advanced.android.model.MovieDetail
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel(val app: Application) : AndroidViewModel(app) {

    private val movieDetail: MutableLiveData<MovieDetail> = MutableLiveData()
    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    val movieApi = MovieApiInterface.create()

    init {
        movieDetail.value = MovieDetail()
    }

    fun getMovieDetail(): MutableLiveData<MovieDetail>{
        return movieDetail
    }
    fun fetchMovieDetails(movieId: Long) {
        if(Utils().isNetworkAvailable()){
            val observer: Observable<MovieDetail> = movieApi.getMovieDetail(movieId, app.getString(R.string.api_key))
            mCompositeDisposable.add(observer.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<MovieDetail>(){
                        override fun onComplete() {

                        }

                        override fun onNext(t: MovieDetail) {
                            Log.e("Movie Id", ""+t.id)
                            movieDetail.value = t
                        }

                        override fun onError(e: Throwable) {

                        }

                    }))
        }
    }
}
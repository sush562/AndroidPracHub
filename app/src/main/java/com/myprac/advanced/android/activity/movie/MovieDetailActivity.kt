package com.myprac.advanced.android.activity.movie

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.myprac.advanced.android.R
import com.myprac.advanced.android.RetroApp
import com.myprac.advanced.android.model.MovieResult
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var mMovieResult: MovieResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        mMovieResult = intent.getSerializableExtra("movie_data") as MovieResult
        toolbar.title = mMovieResult.title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val path: String = mMovieResult.posterBaseUrl + "w780" + mMovieResult.backdropPath

        val backdropIv: ImageView = findViewById(R.id.movie_detail_header_iv)
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(RetroApp.instance)
                .load(path)
                .apply(requestOptions)
                .placeholder(android.R.drawable.stat_sys_download_done)
                .error(android.R.drawable.stat_notify_error)
                .into(backdropIv)

        movie_detail_desc_tv.text = mMovieResult.overview
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

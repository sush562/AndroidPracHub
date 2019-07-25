package com.myprac.advanced.android.activity.movie

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.myprac.advanced.android.R
import com.myprac.advanced.android.RetroApp
import com.myprac.advanced.android.databinding.ActivityMovieDetailBinding
import com.myprac.advanced.android.model.MovieDetail
import com.myprac.advanced.android.viewmodel.movie.MovieDetailViewModel
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_movie_detail)
        val binding: ActivityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        val viewModel: MovieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        binding.movieDetailViewModel = viewModel
        binding.lifecycleOwner = this

        val movieId: Long = intent.getLongExtra("movie_id", 0)
        val backDropPath: String = intent.getStringExtra("backdrop_path")

        val path: String = RetroApp.imageBasePath + "w780" + backDropPath

        val backdropIv: ImageView = findViewById(R.id.movie_detail_header_iv)
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(RetroApp.instance)
                .load(path)
                .apply(requestOptions)
                .placeholder(android.R.drawable.stat_sys_download_done)
                .error(android.R.drawable.stat_notify_error)
                .into(backdropIv)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        viewModel.getMovieDetail().observe(this, Observer<MovieDetail> { movieDetail ->
            run {
                if(movieDetail.id > 0){
                    toolbar.title = movieDetail.title
                    setSupportActionBar(toolbar)
                }
            }
        })
        viewModel.fetchMovieDetails(movieId)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

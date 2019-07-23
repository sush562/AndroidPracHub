package com.myprac.advanced.android.adapter.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.myprac.advanced.android.R
import com.myprac.advanced.android.RetroApp
import com.myprac.advanced.android.model.MovieResult
import kotlinx.android.synthetic.main.movie_list_icon_layout.view.*

class MoviePagedListAdapter(private val movieClickListener: (MovieResult) -> Unit) :
        PagedListAdapter<MovieResult, MoviePagedListAdapter.MovieListPagedViewHolder>(MovieListAdapter.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListPagedViewHolder {
        return MovieListPagedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_list_icon_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MovieListPagedViewHolder, position: Int) {
        val item: MovieResult = getItem(position) ?: return
        holder.bind(item, movieClickListener)
    }


    class MovieListPagedViewHolder(val mainView: View) : RecyclerView.ViewHolder(mainView) {
        fun bind(movieResult: MovieResult, clickListener: (MovieResult) -> Unit) {
            val posterIv: ImageView = mainView.findViewById(R.id.movie_list_poster_iv)
            val path: String = movieResult.posterBaseUrl + "w500" + movieResult.posterPath
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(RetroApp.instance)
                    .load(path)
                    .apply(requestOptions)
                    .placeholder(android.R.drawable.stat_sys_download_done)
                    .error(android.R.drawable.stat_notify_error)
                    .into(posterIv)
            mainView.movie_list_title_tv.text = movieResult.title
            mainView.setOnClickListener { clickListener(movieResult) }
        }
    }
}
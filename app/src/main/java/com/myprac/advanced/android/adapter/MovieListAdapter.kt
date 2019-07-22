package com.myprac.advanced.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.myprac.advanced.android.R
import com.myprac.advanced.android.RetroApp
import com.myprac.advanced.android.model.MovieResult
import kotlinx.android.synthetic.main.movie_list_icon_layout.view.*

class MovieListAdapter(private val movieClickListener: (MovieResult) -> Unit) :
        ListAdapter<MovieResult, MovieListAdapter.MovieListViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val viewHolder = MovieListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_list_icon_layout, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(getItem(position), movieClickListener)
    }

    class MovieListViewHolder(val mainView: View) : RecyclerView.ViewHolder(mainView) {
        fun bind(movieResult: MovieResult, clickListener: (MovieResult) -> Unit) {
            val path: String = movieResult.posterBaseUrl + "w500" + movieResult.posterPath
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            val posterIv: ImageView = mainView.findViewById(R.id.movie_list_poster_iv);
            Glide.with(RetroApp.instance).load(path).apply(requestOptions).into(posterIv)
            mainView.movie_list_title_tv.text = movieResult.title
            mainView.setOnClickListener{clickListener(movieResult)}
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieResult>() {
            override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
                return oldItem == newItem
            }

        }
    }
}
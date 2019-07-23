package com.myprac.advanced.android.adapter.movie

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.myprac.advanced.android.R
import com.myprac.advanced.android.RetroApp
import com.myprac.advanced.android.interfaces.HomePageClickCallback
import com.myprac.advanced.android.model.MovieResult

class MovieListRecyclerViewAdapter(val items: List<MovieResult>, val context: Context, val click: HomePageClickCallback) :
        RecyclerView.Adapter<MovieListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_list_icon_layout, parent, false))
        viewHolder.addOnClick()
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text = items.get(position).title
        val path: String = items.get(position).posterBaseUrl + "w500" +items.get(position).posterPath
        Log.e("Image path", path)
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(RetroApp.instance).load(path).apply(requestOptions).into(holder.posterImageView)
        /*holder.titleTextView.setOnClickListener{
            this.click.onClick(position)
        }*/
    }


    class ViewHolder(var view: View): RecyclerView.ViewHolder(view){
        var titleTextView: TextView = view.findViewById(R.id.movie_list_title_tv)
        var posterImageView: ImageView = view.findViewById(R.id.movie_list_poster_iv)

        fun addOnClick(){
            view.setOnClickListener {
                Log.e("Test","1")
            }
        }
    }

}
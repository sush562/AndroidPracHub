package com.myprac.advanced.android.activity.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.myprac.advanced.android.R
import com.myprac.advanced.android.adapter.movie.MoviePagedListAdapter
import com.myprac.advanced.android.model.MovieResult
import com.myprac.advanced.android.viewmodel.movie.MovieListPagingHorizontalViewModel

class MovieHorizontalListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_horizontal_list)

        val nowPlayingList: RecyclerView = findViewById(R.id.movie_horizontal_now_playing_lv)
        val popularList: RecyclerView = findViewById(R.id.movie_horizontal_popular_lv)
        val topRatedList: RecyclerView = findViewById(R.id.movie_horizontal_top_rated_lv)
        val upcomingList: RecyclerView = findViewById(R.id.movie_horizontal_upcoming_lv)

        val movieListViewModel = ViewModelProviders.of(this).get(MovieListPagingHorizontalViewModel::class.java)

        movieListViewModel.getIsConfigFetching().observe(this, Observer<Boolean> { t ->
            run {
                if (t) {
                    Log.e(MovieHomeActivity::class.java.simpleName, "Fetching Config")
                } else {
                    val adapter1 = MoviePagedListAdapter { movieResult -> onMovieClicked(movieResult) }
                    adapter1.setInflateLayout(R.layout.movie_list_horizontal_icon_layout)
                    nowPlayingList.adapter = adapter1
                    val adapter2 = MoviePagedListAdapter { movieResult -> onMovieClicked(movieResult) }
                    adapter2.setInflateLayout(R.layout.movie_list_horizontal_icon_layout)
                    popularList.adapter = adapter2
                    val adapter3 = MoviePagedListAdapter { movieResult -> onMovieClicked(movieResult) }
                    adapter3.setInflateLayout(R.layout.movie_list_horizontal_icon_layout)
                    topRatedList.adapter = adapter3
                    val adapter4 = MoviePagedListAdapter { movieResult -> onMovieClicked(movieResult) }
                    adapter4.setInflateLayout(R.layout.movie_list_horizontal_icon_layout)
                    upcomingList.adapter = adapter4

                    movieListViewModel.initializeDataSource()
                    movieListViewModel.getNowPlayingMovieList().observe(this, Observer<PagedList<MovieResult>> { t ->
                        run {
                            adapter1.submitList(t)
                        }
                    })
                    movieListViewModel.getPopularMovieList().observe(this, Observer<PagedList<MovieResult>> { t ->
                        run {
                            adapter2.submitList(t)
                        }
                    })
                    movieListViewModel.getTopRatedMovieList().observe(this, Observer<PagedList<MovieResult>> { t ->
                        run {
                            adapter3.submitList(t)
                        }
                    })
                    movieListViewModel.getUpcomingMovieList().observe(this, Observer<PagedList<MovieResult>> { t ->
                        run {
                            adapter4.submitList(t)
                        }
                    })
                }
            }
        })

        movieListViewModel.getConfig()
    }

    private fun onMovieClicked(movieResult: MovieResult) {
        Log.e("Movie Click", movieResult.title)
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie_data", movieResult)
        startActivity(intent)
    }


}

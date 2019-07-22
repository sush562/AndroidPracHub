package com.myprac.advanced.android.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.myprac.advanced.android.R
import com.myprac.advanced.android.adapter.MovieListAdapter
import com.myprac.advanced.android.enum.MovieType
import com.myprac.advanced.android.model.MovieResult
import com.myprac.advanced.android.viewmodel.MovieListViewModel

class MovieHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var navView: NavigationView
    private lateinit var fetchProgressBar: ProgressBar
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_home)
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.menu_now_playing)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setCheckedItem(R.id.nav_now_playing)
        navView.setNavigationItemSelectedListener(this)

        fetchProgressBar = findViewById(R.id.movie_list_progress_pb)

        val movieList: RecyclerView = findViewById(R.id.movie_list_rv)
        movieList.layoutManager = GridLayoutManager(this, 2)
        val adapter: MovieListAdapter = MovieListAdapter { movieResult -> onMovieClicked(movieResult) }
        movieList.adapter = adapter

        movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
        movieListViewModel.isFetching().observe(this, Observer<Boolean> { t ->
            run {
                if (t) {
                    fetchProgressBar.visibility = View.VISIBLE
                } else {
                    fetchProgressBar.visibility = View.GONE
                }
            }
        })
        movieListViewModel.getMovieList().observe(this, Observer<List<MovieResult>> { t ->
            run {
                adapter.submitList(t)
            }
        })
        movieListViewModel.getMovieList()
    }


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            movieListViewModel.cancelMovieApiCall()
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.movie_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        if (navView.checkedItem != item) {
            //movieListViewModel.cancelMovieApiCall()
            toolbar.title = item.title
            when (item.itemId) {
                R.id.nav_latest_movie -> {

                }
                R.id.nav_now_playing -> {
                    movieListViewModel.setMovieType(MovieType.NOW_PLAYING)
                }
                R.id.nav_popular -> {
                    movieListViewModel.setMovieType(MovieType.POPULAR)
                }
                R.id.nav_top_rated -> {
                    movieListViewModel.setMovieType(MovieType.TOP_RATED)
                }
                R.id.nav_upcoming -> {
                    movieListViewModel.setMovieType(MovieType.UPCOMING)
                }
            }
            movieListViewModel.getMovieList()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun onMovieClicked(movieResult: MovieResult) {
        Log.e("Movie Click", movieResult.title)
    }
}

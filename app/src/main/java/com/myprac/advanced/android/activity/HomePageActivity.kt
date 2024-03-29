package com.myprac.advanced.android.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myprac.advanced.android.R
import com.myprac.advanced.android.activity.movie.MovieHomeActivity
import com.myprac.advanced.android.activity.movie.MovieHorizontalListActivity
import com.myprac.advanced.android.adapter.HomePageAdapter
import com.myprac.advanced.android.interfaces.HomePageClickCallback

class HomePageActivity : AppCompatActivity(), HomePageClickCallback {

    val list: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val recycle = findViewById<RecyclerView>(R.id.home_page_rv)
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = HomePageAdapter(getList(), this, this)
    }

    fun getList(): List<String> {
        list.clear()
        list.add("Retrofit")
        list.add("Movie")
        list.add("Movie Horizontal")
        return list
    }

    override fun onClick(position: Int) {
        val intent: Intent
        if (position == 0) {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else if (position == 1) {
            /*intent = Intent(this, DaggerTutorialActivity::class.java)
            startActivity(intent)*/
            intent = Intent(this, MovieHomeActivity::class.java)
            startActivity(intent)

        } else if (position == 2) {
            intent = Intent(this, MovieHorizontalListActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onDestroy() {
        Thread {
            run {
                Glide.get(this).clearDiskCache()
            }
        }.start()
        super.onDestroy()
    }
}

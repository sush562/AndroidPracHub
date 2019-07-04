package com.myprac.advanced.android.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myprac.advanced.android.R
import com.myprac.advanced.android.adapter.HomePageAdapter
import com.myprac.advanced.android.interfaces.HomePageClickCallback

class HomePageActivity : AppCompatActivity(), HomePageClickCallback {

    val list: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        var recycle = findViewById<RecyclerView>(R.id.home_page_rv)
        var adapter: HomePageAdapter = HomePageAdapter(getList(), this, this)
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = adapter
    }

    fun getList(): List<String>{
        list.clear()
        list.add("Retrofit")
        return list
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onClick(position: Int) {
        val intent: Intent
        if (position == 0){
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
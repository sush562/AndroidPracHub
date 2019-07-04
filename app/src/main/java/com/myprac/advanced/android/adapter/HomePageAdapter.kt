package com.myprac.advanced.android.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myprac.advanced.android.interfaces.HomePageClickCallback

class HomePageAdapter(val items: List<String>, val context: Context, val click: HomePageClickCallback) :
        RecyclerView.Adapter<HomePageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false))
        viewHolder.addOnClick()
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textItem.text = items.get(position)
        holder.textItem.setOnClickListener{
            this.click.onClick(position)
        }
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var textItem: TextView = view as TextView

        fun addOnClick(){
            textItem.setOnClickListener {
                Log.e("Test","1")
                Log.d("Test","2")
            }
        }
    }

}
package com.myprac.advanced.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myprac.advanced.android.R;
import com.myprac.advanced.android.model.RetroPhoto;

import java.util.List;

public class RetroAdapter extends RecyclerView.Adapter<RetroAdapter.CustomViewHolder>{

    private final List<RetroPhoto> list;
    public RetroAdapter(final List<RetroPhoto> list){
        this.list = list;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_list_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.titleTv.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView titleTv;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.retro_list_title);
        }
    }
}

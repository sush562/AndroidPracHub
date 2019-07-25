package com.myprac.advanced.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myprac.advanced.android.R;
import com.myprac.advanced.android.adapter.RetroAdapter;
import com.myprac.advanced.android.model.RetroPhoto;
import com.myprac.advanced.android.viewmodel.ProjectListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView printTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printTv = findViewById(R.id.retro_list);
        printTv.setLayoutManager(new LinearLayoutManager(this));


        final ProjectListViewModel model = ViewModelProviders.of(this).get(ProjectListViewModel.class);
        model.getList().observe(this, new Observer<List<RetroPhoto>>() {
            @Override
            public void onChanged(List<RetroPhoto> list) {
                Log.e("Observe", "Called");
                if(list != null){
                    final RetroAdapter adapter = new RetroAdapter(list);
                    printTv.setAdapter(adapter);
                }
            }
        });
        findViewById(R.id.retro_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.clearPhotoList();
                model.fetchPhotoList();
            }
        });

        findViewById(R.id.retro_clear_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.clearPhotoList();
            }
        });

        findViewById(R.id.retro_second_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

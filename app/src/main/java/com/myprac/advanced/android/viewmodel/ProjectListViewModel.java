package com.myprac.advanced.android.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.myprac.advanced.android.model.RetroPhoto;
import com.myprac.advanced.android.repository.RetroRepository;

import java.util.ArrayList;
import java.util.List;

public class ProjectListViewModel extends AndroidViewModel {

    private MutableLiveData<List<RetroPhoto>> list = new MutableLiveData<>();
    public ProjectListViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchPhotoList(){
        RetroRepository.getInstance().getPhotoList(list);
    }

    public void clearPhotoList(){
        list.setValue(new ArrayList<RetroPhoto>());
    }

    public LiveData<List<RetroPhoto>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}

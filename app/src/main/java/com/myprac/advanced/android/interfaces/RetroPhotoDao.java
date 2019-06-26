package com.myprac.advanced.android.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.myprac.advanced.android.model.RetroPhoto;

import java.util.List;

@Dao
public interface RetroPhotoDao {

    @Insert
    void insertAll(List<RetroPhoto> retroPhoto);

    @Query("select * from retrophototable")
    List<RetroPhoto> getAllRetroPhotoList();

    @Query("select count(album_id) from retrophototable")
    int getCount();

    @Query("delete from retrophototable")
    void deleteAll();
}

package com.myprac.advanced.android.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.myprac.advanced.android.RetroApp;
import com.myprac.advanced.android.interfaces.RetroPhotoDao;
import com.myprac.advanced.android.model.RetroPhoto;

@Database(entities = {RetroPhoto.class}, version = 1)
public abstract class RetroDatabase extends RoomDatabase {

    public abstract RetroPhotoDao retroPhotoDao();

    public static final String DB_NAME = "prac_db";

    private static RetroDatabase mInstance;

    public static RetroDatabase getInstance() {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(RetroApp.Companion.getInstance(), RetroDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    //.allowMainThreadQueries()
                    .build();
        }
        return mInstance;
    }
}

package com.myprac.advanced.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myprac.advanced.android.model.MovieConfig

@Database(entities = arrayOf(MovieConfig::class), version = 1)
abstract class MovieDatabase: RoomDatabase() {


}
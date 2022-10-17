package com.tamaki.workerapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tamaki.workerapp.data.dataClasses.Profile

@Database(
    entities = [Profile::class],
    version = 3
)
abstract class RoomDatabase: RoomDatabase() {
    abstract fun getArticleDao(): DataAccessObject
}
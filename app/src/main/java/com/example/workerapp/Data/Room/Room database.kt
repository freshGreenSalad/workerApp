package com.example.workerapp.Data.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Workers::class],
    version = 2
)
abstract class roomDatabase(): RoomDatabase() {

    abstract fun getArticleDao(): dataAccessObject

}
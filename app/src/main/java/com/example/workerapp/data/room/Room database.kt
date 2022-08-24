package com.example.workerapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.workerapp.data.models.Worker

@Database(
    entities = [Worker::class],
    version = 2
)
abstract class RoomDatabase: RoomDatabase() {
    abstract fun getArticleDao(): DataAccessObject
}
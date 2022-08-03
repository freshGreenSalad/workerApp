package com.example.workerapp.Data.Room

import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface dataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(workers: Workers)

    @Query("SELECT * FROM Workers")
    fun getWorkers(): Flow<List<Workers>>
}
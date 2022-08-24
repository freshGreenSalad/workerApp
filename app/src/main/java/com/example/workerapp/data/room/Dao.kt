package com.example.workerapp.data.room

import androidx.room.*
import androidx.room.Dao
import com.example.workerapp.data.models.Worker
import kotlinx.coroutines.flow.Flow

@Dao
interface DataAccessObject {

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(workers: Worker)*/

    @Query("SELECT * FROM Worker")
    fun getWorkers(): Flow<List<Worker>>
}
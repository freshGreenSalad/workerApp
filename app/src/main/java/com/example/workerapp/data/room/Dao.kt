package com.example.workerapp.data.room

import androidx.room.*
import androidx.room.Dao
import com.example.workerapp.data.models.Profile
import com.example.workerapp.data.models.Worker
import kotlinx.coroutines.flow.Flow

@Dao
interface DataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: Profile)

    @Query("SELECT * FROM Profile")
    suspend fun getProfile():Profile
}
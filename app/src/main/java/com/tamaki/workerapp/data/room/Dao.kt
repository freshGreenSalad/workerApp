package com.tamaki.workerapp.data.room

import androidx.room.*
import androidx.room.Dao
import com.tamaki.workerapp.data.dataClasses.Profile

@Dao
interface DataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: Profile)

    @Query("SELECT * FROM Profile")
    suspend fun getProfile():Profile
}
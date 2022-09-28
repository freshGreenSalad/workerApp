package com.example.workerapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Profile(
    @PrimaryKey
    val email: String,
    val password: String,
    val firstName :String,
    val lastName :String,
    val company :String,
)

val testProfile = Profile(
    "testEmail",
    "testpassword",
    "testFistname",
    "testlastname",
    "testcompany"
)
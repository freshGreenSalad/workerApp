package com.example.workerapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Profile(
    val tableName:String,
    val key:String,
    val keyVal:String,
    val firstName :String,
    val firstNameVal:String,
    val lastName :String,
    val lastNameVal :String,
    @PrimaryKey
    val email :String,
    val emailVal :String,
    val company :String,
    val companyVal :String
)

val testProfile = Profile(
    "profileInfo",
    "profileId",
    "keyVal",
    "workerFirstName",
    "fromApp",
    "workerLastName",
    "fromApp",
    "email",
    "fromApp",
    "company",
    "fromApp"
)
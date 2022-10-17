package com.tamaki.workerapp.data.dataClasses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Entity
@Parcelize
@Serializable
data class Worker(
    @PrimaryKey(autoGenerate = true)
    val key:Int,
    val name:String,
    val age: Int?,
    val hourlyRate: Int?,
    val imageURL: String
) : Parcelable

val blankWorker = Worker(0,"",null, null,"")
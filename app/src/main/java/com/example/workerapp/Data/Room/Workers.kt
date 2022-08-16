package com.example.workerapp.Data.Room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workerapp.R
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity()
@Parcelize
@Serializable
data class Workers(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val name:String,
    val age:Int,
    val image:Int,
    val price:Int,
): Parcelable

val worker1 = Workers(0,"worker1",25, R.drawable.eight,35)
val worker2 = Workers(0,"worker2",25, R.drawable.five,35)
val worker3 = Workers(0,"worker3",25, R.drawable.nine,35)
val worker4 = Workers(0,"worker4",25, R.drawable.four,35)
val worker5 = Workers(0,"worker5",25, R.drawable.one,35)

val workerList = listOf<Workers>(worker1, worker2, worker3, worker4, worker5)

@Parcelize
@Serializable
data class WorkerTest(
    val key:Int,
    val name:String,
    val age: Int?,
    val hourlyRate: Int?,
    val imageURL: String
) : Parcelable

val workerTestTest = WorkerTest(0,"",null, null,"")
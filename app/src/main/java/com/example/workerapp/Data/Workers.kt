package com.example.workerapp.Data

import com.example.workerapp.R

data class Workers(
    val name:String,
    val age:Int,
    val image:Int,
    val price:Int
)

val worker1 = Workers("worker1",25, R.drawable.eight,35)
val worker2 = Workers("worker2",25, R.drawable.five,35)
val worker3 = Workers("worker3",25, R.drawable.nine,35)
val worker4 = Workers("worker4",25, R.drawable.four,35)
val worker5 = Workers("worker5",25, R.drawable.one,35)

val worker = listOf<Workers>(worker1, worker2, worker3, worker4, worker5)
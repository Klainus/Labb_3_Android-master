package com.example.labb_3_android.cities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    val date: String,
    val temperature: String,
    val description: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}

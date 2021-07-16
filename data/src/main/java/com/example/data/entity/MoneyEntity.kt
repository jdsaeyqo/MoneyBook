package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoneyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val checked : String,
    val date : String,
    val money : String,
    val separation : String,
    val use : String,
    val description: String,

) {
}
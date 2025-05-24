package com.example.expirytrackerapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val itemName: String,
    val company: String,
    val expiryDate: Long,
    val mrp: Double
) 
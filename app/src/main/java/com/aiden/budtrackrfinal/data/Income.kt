package com.aiden.budtrackrfinal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income")
data class Income(
    @PrimaryKey(autoGenerate = true)
    val incomeId: Int = 0,
    val userId: Int,
    val month: String,
    val amount: Double
)
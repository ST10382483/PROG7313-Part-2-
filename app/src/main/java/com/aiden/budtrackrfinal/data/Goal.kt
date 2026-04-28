package com.aiden.budtrackrfinal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true) val goalId: Int = 0,
    val userId: Int,
    val goalName: String,
    val goalType: String,
    val targetAmount: Double,
    val currentAmount: Double
)
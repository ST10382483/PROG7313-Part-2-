package com.aiden.budtrackrfinal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_goals")
data class MonthlyGoal(
    @PrimaryKey(autoGenerate = true)
    val goalId: Int = 0,
    val userId: Int,
    val minAmount: Double,
    val maxAmount: Double
)
package com.aiden.budtrackrfinal.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int = 0,
    val userId: Int,
    val date: String,
    val startTime: String = "",
    val endTime: String = "",
    val description: String,
    val amount: Double,
    val categoryName: String,
    val addedOn: String,
    val imagePath: String?
) : Serializable
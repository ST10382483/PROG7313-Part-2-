package com.darsh.roomdb_st10442633.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "budget_goals",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index(value = ["userId", "year", "month"], unique = true)]
)
data class BudgetGoal(
    @PrimaryKey(autoGenerate = true)
    val goalId: Long = 0,
    val userId: Long,
    val year: Int,
    val month: Int, // 1-12
    val maxExpenditure: Double, // Maximum budget limit
    val categoryId: Long? = null, // Category specific... if null then applies to the overall budget;
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
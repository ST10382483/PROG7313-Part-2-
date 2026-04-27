package com.darsh.roomdb_st10442633.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date
import java.util.Locale.Category

@Entity(
    tableName = "expense_entries",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"]

        )
    ],
    indices = [Index("userId"), Index("categoryId"), Index("date")]
)
data class Expenses(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Long = 0,
    val userId: Long,
    val categoryId: Long,
    val date: Date,
    val startTime: Date,
    val endTime: Date,
    val description: String,
    val amount: Double,
    val photoUri: String? = null,
    val photoPath: String? = null,
    val notes: String? = null,
    val location: String? = null,
    val isSynced: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
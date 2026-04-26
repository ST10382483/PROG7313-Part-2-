package com.darsh.roomdb_st10442633

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class `Category.kt`(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long = 0,
    val userId: Long,
    val name: String,
    val description: String? = null,
    val icon: String? = null, //Stores the image location
    val isDefault: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
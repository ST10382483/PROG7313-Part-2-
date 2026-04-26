package com.darsh.roomdb_st10442633.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0,
    val username: String,
    //val password: String, (Not used yet)
    val email: String? = null,
    val fullName: String? = null,
    val createdAt: Date = Date(),
    val lastLogin: Date? = null
)

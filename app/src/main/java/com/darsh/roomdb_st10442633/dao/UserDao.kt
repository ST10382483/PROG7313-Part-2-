package com.darsh.roomdb_st10442633.dao

import androidx.room.*
import com.darsh.roomdb_st10442633.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    //@Query("SELECT * FROM users WHERE username = :username AND password = :password")
    //suspend fun login(username: String, password: String): User?

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Query("UPDATE users SET lastLogin = :timestamp WHERE userId = :userId")
    suspend fun updateLastLogin(userId: Long, timestamp: Long)

    @Query("DELETE FROM users WHERE userId = :userId")
    suspend fun deleteUserById(userId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE username = :username)")
    suspend fun isUsernameExists(username: String): Boolean
}
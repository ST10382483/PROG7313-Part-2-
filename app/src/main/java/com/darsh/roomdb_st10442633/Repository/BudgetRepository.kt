package com.darsh.roomdb_st10442633.repository

import android.content.Context
import com.darsh.roomdb_st10442633.AppDatabase
import com.darsh.roomdb_st10442633.dao.CategorySpending
import com.darsh.roomdb_st10442633.models.*
import kotlinx.coroutines.flow.Flow
import java.util.*

class BudgetRepository(private val context: Context) {

    private val database = AppDatabase.getDatabase(context)
    private val userDao = database.userDao()
    private val categoryDao = database.categoryDao()
    private val expenseDao = database.expenseEntryDao()
    private val budgetGoalDao = database.budgetGoalDao()

    //USER OPERATIONS

    suspend fun registerUser(
        username: String,
        password: String,
        email: String? = null,
        fullName: String? = null
    ): Long {
        if (userDao.isUsernameExists(username)) {
            return -1
        }
        val user = User(
            username = username,
            password = password,
            email = email,
            fullName = fullName
        )
        return userDao.insertUser(user)
    }

    suspend fun login(username: String, password: String): User? {
        val user = userDao.login(username, password)
        user?.let {
            userDao.updateLastLogin(it.userId, System.currentTimeMillis())
        }
        return user
    }

    suspend fun getUserById(userId: Long): User? = userDao.getUserById(userId)

    suspend fun updateUser(user: User) = userDao.updateUser(user)

    suspend fun deleteUser(userId: Long) = userDao.deleteUserById(userId)

    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
}
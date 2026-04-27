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
    

    //CATEGORY OPERATIONS

    suspend fun addCategory(
        userId: Long,
        name: String,
        description: String? = null,
        icon: String? = null,
        color: String? = null,
        isDefault: Boolean = false
    ): Long {
        val category = Category(
            userId = userId,
            name = name,
            description = description,
            icon = icon,
            color = color,
            isDefault = isDefault
        )
        return categoryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)

    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)

    suspend fun getCategoryById(categoryId: Long): Category? = categoryDao.getCategoryById(categoryId)

    fun getCategoriesByUser(userId: Long): Flow<List<Category>> = categoryDao.getCategoriesByUser(userId)

    fun searchCategories(userId: Long, search: String): Flow<List<Category>> = categoryDao.searchCategories(userId, search)
}
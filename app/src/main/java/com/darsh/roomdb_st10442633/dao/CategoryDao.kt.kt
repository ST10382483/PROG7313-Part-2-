package com.darsh.roomdb_st10442633.dao

import androidx.room.*
import com.darsh.roomdb_st10442633.models.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface `CategoryDao.kt` {

    @Insert
    suspend fun insertCategory(category: Category): Long

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    suspend fun getCategoryById(categoryId: Long): Category?

    @Query("SELECT * FROM categories WHERE userId = :userId ORDER BY name ASC")
    fun getCategoriesByUser(userId: Long): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE userId = :userId AND isDefault = 1")
    fun getDefaultCategories(userId: Long): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE userId = :userId AND name LIKE '%' || :search || '%'")
    fun searchCategories(userId: Long, search: String): Flow<List<Category>>

    @Query("DELETE FROM categories WHERE userId = :userId AND isDefault = 0")
    suspend fun deleteCustomCategories(userId: Long)

    @Query("SELECT COUNT(*) FROM categories WHERE userId = :userId")
    suspend fun getCategoryCount(userId: Long): Int
}
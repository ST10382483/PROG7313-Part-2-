package com.darsh.roomdb_st10442633.dao

import androidx.room.*
import com.darsh.roomdb_st10442633.models.Expenses
import kotlinx.coroutines.flow.Flow
import java.util.Date

data class CategorySpending(
    val categoryName: String,
    val totalSpent: Double,
    val expenseCount: Int
)

@Dao
interface ExpensesDao {

    @Insert
    suspend fun insertExpense(expense: Expenses): Long

    @Update
    suspend fun updateExpense(expense: Expenses)

    @Delete
    suspend fun deleteExpense(expense: Expenses)

    @Query("SELECT * FROM expense_entries WHERE expenseId = :expenseId")
    suspend fun getExpenseById(expenseId: Long): Expenses?

    @Query("SELECT * FROM expense_entries WHERE userId = :userId ORDER BY date DESC, startTime DESC")
    fun getAllExpenses(userId: Long): Flow<List<Expenses>>

    @Query("SELECT * FROM expense_entries WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC, startTime DESC")
    fun getExpensesByDateRange(userId: Long, startDate: Date, endDate: Date): Flow<List<Expenses>>

    @Query("SELECT * FROM expense_entries WHERE userId = :userId AND categoryId = :categoryId AND date BETWEEN :startDate AND :endDate")
    fun getExpensesByCategoryAndDateRange(
        userId: Long,
        categoryId: Long,
        startDate: Date,
        endDate: Date
    ): Flow<List<Expenses>>

    @Query("SELECT SUM(amount) FROM expense_entries WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalSpentInDateRange(userId: Long, startDate: Date, endDate: Date): Double?

    @Query("""
        SELECT c.name as categoryName, 
               SUM(e.amount) as totalSpent, 
               COUNT(e.expenseId) as expenseCount
        FROM expense_entries e
        JOIN categories c ON e.categoryId = c.categoryId
        WHERE e.userId = :userId AND e.date BETWEEN :startDate AND :endDate
        GROUP BY c.categoryId
        ORDER BY totalSpent DESC
    """)
    fun getTotalSpentByCategory(userId: Long, startDate: Date, endDate: Date): Flow<List<CategorySpending>>

    @Query("SELECT * FROM expense_entries WHERE userId = :userId ORDER BY date DESC LIMIT :limit OFFSET :offset")
    suspend fun getExpensesPaginated(userId: Long, limit: Int, offset: Int): List<Expenses>

    @Query("SELECT COUNT(*) FROM expense_entries WHERE userId = :userId")
    suspend fun getTotalExpenseCount(userId: Long): Int
}
package com.darsh.roomdb_st10442633.dao

import androidx.room.*
import com.darsh.roomdb_st10442633.models.BudgetGoal
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetGoalDao {

    @Insert
    suspend fun insertBudgetGoal(goal: BudgetGoal): Long

    @Update
    suspend fun updateBudgetGoal(goal: BudgetGoal)

    @Delete
    suspend fun deleteBudgetGoal(goal: BudgetGoal)

    @Query("SELECT * FROM budget_goals WHERE goalId = :goalId")
    suspend fun getBudgetGoalById(goalId: Long): BudgetGoal?

    @Query("SELECT * FROM budget_goals WHERE userId = :userId AND year = :year AND month = :month AND categoryId IS NULL")
    suspend fun getOverallBudgetGoalForMonth(userId: Long, year: Int, month: Int): BudgetGoal?

    @Query("SELECT * FROM budget_goals WHERE userId = :userId AND year = :year AND month = :month AND categoryId = :categoryId")
    suspend fun getCategoryBudgetGoalForMonth(userId: Long, year: Int, month: Int, categoryId: Long): BudgetGoal?

    @Query("SELECT * FROM budget_goals WHERE userId = :userId ORDER BY year DESC, month DESC")
    fun getAllBudgetGoals(userId: Long): Flow<List<BudgetGoal>>

    @Query("SELECT * FROM budget_goals WHERE userId = :userId AND year = :year ORDER BY month ASC")
    fun getBudgetGoalsForYear(userId: Long, year: Int): Flow<List<BudgetGoal>>

    @Query("DELETE FROM budget_goals WHERE userId = :userId AND year = :year AND month = :month")
    suspend fun deleteBudgetGoalForMonth(userId: Long, year: Int, month: Int)


}
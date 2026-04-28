package com.aiden.budtrackrfinal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MonthlyGoalDao {

    @Insert
    suspend fun insertGoal(goal: MonthlyGoal)

    @Query("SELECT * FROM monthly_goals WHERE userId = :userId ORDER BY goalId DESC LIMIT 1")
    suspend fun getLatestGoalForUser(userId: Int): MonthlyGoal?
}
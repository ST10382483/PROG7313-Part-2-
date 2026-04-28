package com.aiden.budtrackrfinal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GoalDao {

    @Insert
    suspend fun insertGoal(goal: Goal)

    @Query("SELECT * FROM goals WHERE userId = :userId")
    suspend fun getGoalsForUser(userId: Int): List<Goal>

    @Query("DELETE FROM goals WHERE goalId = :goalId")
    suspend fun deleteGoal(goalId: Int)
}
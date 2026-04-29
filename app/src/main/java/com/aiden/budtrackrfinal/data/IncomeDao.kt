package com.aiden.budtrackrfinal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IncomeDao {

    @Insert
    suspend fun insertIncome(income: Income)

    @Query("SELECT * FROM income WHERE userId = :userId ORDER BY incomeId DESC")
    suspend fun getIncomeForUser(userId: Int): List<Income>
}
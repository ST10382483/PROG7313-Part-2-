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
}
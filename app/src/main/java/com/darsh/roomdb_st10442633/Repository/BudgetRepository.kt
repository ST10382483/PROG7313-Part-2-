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

    suspend fun getCategoryById(categoryId: Long): Category? =
        categoryDao.getCategoryById(categoryId)

    fun getCategoriesByUser(userId: Long): Flow<List<Category>> =
        categoryDao.getCategoriesByUser(userId)

    fun searchCategories(userId: Long, search: String): Flow<List<Category>> =
        categoryDao.searchCategories(userId, search)

    //EXPENSE OPERATIONS

    suspend fun addExpense(
        userId: Long,
        categoryId: Long,
        date: Date,
        startTime: Date,
        endTime: Date,
        description: String,
        amount: Double,
        photoUri: String? = null,
        photoPath: String? = null,
        notes: String? = null,
        location: String? = null
    ): Long {
        val expense = Expenses(
            userId = userId,
            categoryId = categoryId,
            date = date,
            startTime = startTime,
            endTime = endTime,
            description = description,
            amount = amount,
            photoUri = photoUri,
            photoPath = photoPath,
            notes = notes,
            location = location
        )
        return expenseDao.insertExpense(expense)
    }

    suspend fun updateExpense(expense: Expenses) = expenseDao.updateExpense(expense)

    suspend fun deleteExpense(expense: Expenses) = expenseDao.deleteExpense(expense)

    suspend fun getExpenseById(expenseId: Long):Expenses? = expenseDao.getExpenseById(expenseId)

    fun getAllExpenses(userId: Long): Flow<List<Expenses>> = expenseDao.getAllExpenses(userId)

    fun getExpensesByDateRange(
        userId: Long,
        startDate: Date,
        endDate: Date
    ): Flow<List<Expenses>> {
        return expenseDao.getExpensesByDateRange(userId, startDate, endDate)
    }

    fun getExpensesByCategoryAndDateRange(
        userId: Long,
        categoryId: Long,
        startDate: Date,
        endDate: Date
    ): Flow<List<Expenses>> {
        return expenseDao.getExpensesByCategoryAndDateRange(userId, categoryId, startDate, endDate)
    }

    suspend fun getTotalSpentInDateRange(userId: Long, startDate: Date, endDate: Date): Double {
        return expenseDao.getTotalSpentInDateRange(userId, startDate, endDate) ?: 0.0
    }

    fun getTotalSpentByCategory(
        userId: Long,
        startDate: Date,
        endDate: Date
    ): Flow<List<CategorySpending>> {
        return expenseDao.getTotalSpentByCategory(userId, startDate, endDate)
    }

    suspend fun getExpensesPaginated(userId: Long, page: Int, pageSize: Int): List<Expenses> {
        val offset = page * pageSize
        return expenseDao.getExpensesPaginated(userId, pageSize, offset)
    }

    suspend fun getTotalExpenseCount(userId: Long): Int = expenseDao.getTotalExpenseCount(userId)

    //BUDGET GOAL OPERATIONS

    suspend fun setMonthlyBudgetGoal(
        userId: Long,
        year: Int,
        month: Int,
        minGoal: Double,
        maxGoal: Double,
        categoryId: Long? = null
    ) {
        val existingGoal = if (categoryId != null) {
            budgetGoalDao.getCategoryBudgetGoalForMonth(userId, year, month, categoryId)
        } else {
            budgetGoalDao.getOverallBudgetGoalForMonth(userId, year, month)
        }

        if (existingGoal != null) {
            val updatedGoal = existingGoal.copy(
                maxExpenditure = maxGoal,
                updatedAt = System.currentTimeMillis()
            )
            budgetGoalDao.updateBudgetGoal(updatedGoal)
        } else {
            val goal = BudgetGoal(
                userId = userId,
                year = year,
                month = month,
                maxExpenditure = maxGoal,
                categoryId = categoryId
            )
            budgetGoalDao.insertBudgetGoal(goal)
        }
    }

    suspend fun getMonthlyBudgetGoal(userId: Long, year: Int, month: Int): BudgetGoal? {
        return budgetGoalDao.getOverallBudgetGoalForMonth(userId, year, month)
    }

    suspend fun getCategoryBudgetGoal(
        userId: Long,
        year: Int,
        month: Int,
        categoryId: Long
    ): BudgetGoal? {
        return budgetGoalDao.getCategoryBudgetGoalForMonth(userId, year, month, categoryId)
    }

    fun getAllBudgetGoals(userId: Long): Flow<List<BudgetGoal>> = budgetGoalDao.getAllBudgetGoals(userId)

    fun getBudgetGoalsForYear(userId: Long, year: Int): Flow<List<BudgetGoal>> = budgetGoalDao.getBudgetGoalsForYear(userId, year)

    suspend fun deleteBudgetGoalForMonth(userId: Long, year: Int, month: Int) {
        budgetGoalDao.deleteBudgetGoalForMonth(userId, year, month)

        //BUDGET STATUS CHECK

        suspend fun checkBudgetStatus(userId: Long, year: Int, month: Int): BudgetStatus {
            val goal = getMonthlyBudgetGoal(userId, year, month)

            if (goal == null) {
                return BudgetStatus.NoGoalSet
            }

            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, 1, 0, 0, 0)
            val startDate = calendar.time
            calendar.set(year, month, 0, 23, 59, 59)
            val endDate = calendar.time

            val totalSpent = getTotalSpentInDateRange(userId, startDate, endDate)

            return when {

                totalSpent > goal.maxExpenditure -> BudgetStatus.AboveMax(
                    maxGoal = goal.maxExpenditure,
                    currentSpent = totalSpent,
                    overBy = totalSpent - goal.maxExpenditure
                )
                else -> BudgetStatus.WithinRange(
                    maxGoal = goal.maxExpenditure,
                    currentSpent = totalSpent,
                    remainingBudget = goal.maxExpenditure - totalSpent
                )
            }
        }
    }

    // Sealed class for budget status
    sealed class BudgetStatus {
        object NoGoalSet : BudgetStatus()
        data class BelowMin(
            val minGoal: Double,
            val currentSpent: Double,
            val remainingToReachMin: Double
        ) : BudgetStatus()

        data class WithinRange(
            val maxGoal: Double,
            val currentSpent: Double,
            val remainingBudget: Double
        ) : BudgetStatus()

        data class AboveMax(
            val maxGoal: Double,
            val currentSpent: Double,
            val overBy: Double
        ) : BudgetStatus()
    }
}